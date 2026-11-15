package edu.psu.se411.lab11_threads.model;

import edu.psu.se411.lab11_threads.exceptions.InsufficientBalanceException;

public class CryptoAccount {
    private final String id;
    private double balance;

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
        double current = balance;
        Thread.yield();
        balance = current + amount;
    }

    public void retrieveFromBalance(Double amount) throws InsufficientBalanceException {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        double current = balance;
        if (amount > current) {
            throw new InsufficientBalanceException(id);
        }
        Thread.yield();
        balance = current - amount;
    }

    public String getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }
}
