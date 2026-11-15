package edu.psu.se411.lab12_threads.model;

import java.util.concurrent.locks.ReentrantLock;
import edu.psu.se411.lab12_threads.exceptions.InsufficientBalanceException;

public class CryptoAccount {
    private final String id;
    private double balance;
    private final ReentrantLock lock = new ReentrantLock();

    public CryptoAccount(String id, Double balance) {
        if (id == null || balance == null || balance < 0) {
            throw new IllegalArgumentException("Invalid account data");
        }
        this.id = id;
        this.balance = balance;
    }

    public void addToBalance(Double amount) {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        balance += amount;
    }

    public void retrieveFromBalance(Double amount) throws InsufficientBalanceException {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(id);
        }
        balance -= amount;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public String getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }
}
