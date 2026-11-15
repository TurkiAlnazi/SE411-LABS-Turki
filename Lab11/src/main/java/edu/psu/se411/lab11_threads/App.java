package edu.psu.se411.lab11_threads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab11_threads.model.CryptoAccount;
import edu.psu.se411.lab11_threads.model.Transaction;

public class App {
    private static final int NB_ACCOUNTS = 10;
    private static final int NB_TRANSACTIONS = 10000;
    private static final int THREAD_BATCH_SIZE = 200;
    private static final double INITIAL_BALANCE = 1000.0;

    public static void main(String[] args) throws InterruptedException, IOException {
        Files.createDirectories(Path.of("logs"));
        Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("Application is starting...");
        try {
            runSimulation(false);
            runSimulation(true);
        } finally {
            logger.info("Application is closing...");
        }
    }

    private static void runSimulation(boolean synchronizedMode) throws InterruptedException {
        CryptoAccount[] accounts = new CryptoAccount[NB_ACCOUNTS];
        for (int i = 0; i < NB_ACCOUNTS; i++) {
            accounts[i] = new CryptoAccount("acc_" + i, INITIAL_BALANCE);
        }

        Random random = new Random(411);
        System.out.println(synchronizedMode ? "With synchronized method:" : "Without synchronization:");
        System.out.printf("Initial total: %.2f SAR%n", sumTotalMoneyInAccounts(accounts));

        for (int start = 0; start < NB_TRANSACTIONS; start += THREAD_BATCH_SIZE) {
            int count = Math.min(THREAD_BATCH_SIZE, NB_TRANSACTIONS - start);
            Thread[] threads = new Thread[count];
            for (int j = 0; j < count; j++) {
                int senderIndex = random.nextInt(NB_ACCOUNTS);
                int receiverIndex = random.nextInt(NB_ACCOUNTS - 1);
                if (receiverIndex >= senderIndex) receiverIndex++;
                double amount = 1.0 + random.nextDouble() * 10.0;
                Transaction transaction = new Transaction("tx_" + (start + j),
                        accounts[senderIndex], accounts[receiverIndex], amount, synchronizedMode);
                threads[j] = new Thread(transaction);
                threads[j].start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
        }

        double initialTotal = NB_ACCOUNTS * INITIAL_BALANCE;
        double finalTotal = sumTotalMoneyInAccounts(accounts);
        System.out.printf("Transactions: %d%n", NB_TRANSACTIONS);
        System.out.printf("Final total: %.2f SAR%n", finalTotal);
        System.out.printf("Difference: %.2f SAR%n", finalTotal - initialTotal);
        System.out.println(synchronizedMode
                ? "The synchronized method keeps the total balance consistent."
                : "Concurrent transfers may produce an incorrect total balance.");
        System.out.println();
    }

    private static double sumTotalMoneyInAccounts(CryptoAccount[] accounts) {
        double total = 0.0;
        for (CryptoAccount account : accounts) {
            total += account.getBalance();
        }
        return total;
    }
}
