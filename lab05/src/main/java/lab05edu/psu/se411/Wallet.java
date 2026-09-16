package lab05edu.psu.se411;

import edu.psu.se411.exceptions.InsufficientFundsException;

public class Wallet {

    private double balance;

    public Wallet(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) throws InsufficientFundsException {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Withdrawal amount must be greater than zero."
            );
        }

        if (amount > balance) {
            throw new InsufficientFundsException(
                "Insufficient funds. Available balance: " + balance
            );
        }

        balance = balance - amount;

        System.out.println("Withdrawal successful.");
        System.out.println("Amount transferred to bank: " + amount);
        System.out.println("Remaining wallet balance: " + balance);
    }
}

