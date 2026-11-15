package edu.psu.se411.lab12_threads;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import edu.psu.se411.lab12_threads.model.CryptoAccount;
import edu.psu.se411.lab12_threads.model.Transaction;

public class Simulation {
    private static final double INITIAL_BALANCE = 1000.0;
    private static final int MAX_IN_FLIGHT = 32;

    public static class Result {
        public final long elapsedMs;
        public final double initialTotal;
        public final double finalTotal;
        public final int skipped;

        public Result(long elapsedMs, double initialTotal, double finalTotal, int skipped) {
            this.elapsedMs = elapsedMs;
            this.initialTotal = initialTotal;
            this.finalTotal = finalTotal;
            this.skipped = skipped;
        }
    }

    public static Result run(int accountCount, int transactionCount, boolean cached)
            throws InterruptedException {
        CryptoAccount[] accounts = new CryptoAccount[accountCount];
        for (int i = 0; i < accountCount; i++) {
            accounts[i] = new CryptoAccount("acc_" + i, INITIAL_BALANCE);
        }

        ExecutorService executor = cached
                ? Executors.newCachedThreadPool()
                : Executors.newFixedThreadPool(8);
        Semaphore inFlight = new Semaphore(MAX_IN_FLIGHT);
        CountDownLatch finished = new CountDownLatch(transactionCount);
        AtomicInteger skipped = new AtomicInteger();
        Random random = new Random(411);
        long startTime = System.nanoTime();

        try {
            for (int i = 0; i < transactionCount; i++) {
                inFlight.acquire();
                int senderIndex = random.nextInt(accountCount);
                int receiverIndex = random.nextInt(accountCount - 1);
                if (receiverIndex >= senderIndex) receiverIndex++;
                double amount = 1 + random.nextDouble() * 99;
                Transaction transaction = new Transaction("tx_" + i,
                        accounts[senderIndex], accounts[receiverIndex], amount, skipped);
                executor.execute(() -> {
                    try {
                        transaction.run();
                    } finally {
                        inFlight.release();
                        finished.countDown();
                    }
                });
            }
            finished.await();
        } finally {
            executor.shutdown();
        }

        long elapsedMs = (System.nanoTime() - startTime) / 1_000_000;
        double initialTotal = accountCount * INITIAL_BALANCE;
        double finalTotal = 0;
        for (CryptoAccount account : accounts) {
            finalTotal += account.getBalance();
        }
        return new Result(elapsedMs, initialTotal, finalTotal, skipped.get());
    }

    public static void displayResult(String pool, int accounts, int transactions, Result result) {
        System.out.printf("%-6s | %4d accounts | %8d transactions | %6d ms | "
                + "initial %.2f | final %.2f | skipped %d%n",
                pool, accounts, transactions, result.elapsedMs,
                result.initialTotal, result.finalTotal, result.skipped);
        if (Math.abs(result.finalTotal - result.initialTotal) > 0.01) {
            throw new IllegalStateException("Money total changed during concurrent transfers");
        }
    }
}
