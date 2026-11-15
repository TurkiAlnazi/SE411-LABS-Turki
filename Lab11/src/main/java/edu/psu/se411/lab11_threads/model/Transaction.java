package edu.psu.se411.lab11_threads.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab11_threads.exceptions.InsufficientBalanceException;

public class Transaction implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Transaction.class);

    private final String id;
    private final CryptoAccount sender;
    private final CryptoAccount receiver;
    private final Double amount;
    private final boolean synchronizedMode;

    public Transaction(String id, CryptoAccount sender, CryptoAccount receiver,
                       Double amount, boolean synchronizedMode) {
        if (id == null || sender == null || receiver == null || sender == receiver
                || amount == null || amount < 0) {
            throw new IllegalArgumentException("Invalid transaction");
        }
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.synchronizedMode = synchronizedMode;
    }

    public Transaction(CryptoAccount sender, CryptoAccount receiver, Double amount) {
        this("transaction", sender, receiver, amount, false);
    }

    public void execute() throws InsufficientBalanceException {
        sender.retrieveFromBalance(amount);
        receiver.addToBalance(amount);
    }

    public static synchronized void executeSynchronized(Transaction transaction)
            throws InsufficientBalanceException {
        transaction.execute();
    }

    @Override
    public void run() {
        try {
            if (synchronizedMode) {
                executeSynchronized(this);
            } else {
                execute();
            }
        } catch (InsufficientBalanceException e) {
            logger.info("Transaction skipped: " + id + " - " + e.getMessage());
        }
    }

    public String getId() { return id; }
    public CryptoAccount getSender() { return sender; }
    public CryptoAccount getReceiver() { return receiver; }
    public Double getAmount() { return amount; }
}
