package edu.psu.se411.lab12_threads.model;

import java.util.concurrent.atomic.AtomicInteger;
import edu.psu.se411.lab12_threads.exceptions.InsufficientBalanceException;

public class Transaction implements Runnable {
    private final String id;
    private final CryptoAccount sender;
    private final CryptoAccount receiver;
    private final Double amount;
    private final AtomicInteger skipped;

    public Transaction(String id, CryptoAccount sender, CryptoAccount receiver,
                       Double amount, AtomicInteger skipped) {
        if (id == null || sender == null || receiver == null || sender == receiver
                || amount == null || amount < 0 || skipped == null) {
            throw new IllegalArgumentException("Invalid transaction");
        }
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.skipped = skipped;
    }

    public Transaction(String id, CryptoAccount sender, CryptoAccount receiver, Double amount) {
        this(id, sender, receiver, amount, new AtomicInteger());
    }

    public void execute() throws InsufficientBalanceException {
        CryptoAccount first = sender.getId().compareTo(receiver.getId()) < 0 ? sender : receiver;
        CryptoAccount second = first == sender ? receiver : sender;

        first.getLock().lock();
        try {
            second.getLock().lock();
            try {
                sender.retrieveFromBalance(amount);
                receiver.addToBalance(amount);
            } finally {
                second.getLock().unlock();
            }
        } finally {
            first.getLock().unlock();
        }
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (InsufficientBalanceException e) {
            skipped.incrementAndGet();
        }
    }

    public String getId() { return id; }
    public CryptoAccount getSender() { return sender; }
    public CryptoAccount getReceiver() { return receiver; }
    public Double getAmount() { return amount; }
}
