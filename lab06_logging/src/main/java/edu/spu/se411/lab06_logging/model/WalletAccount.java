package edu.spu.se411.lab06_logging.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.spu.se411.lab06_logging.exceptions.InsufficientFundsException;

public class WalletAccount {

	private static final Logger logger = LoggerFactory.getLogger(WalletAccount.class);

	private double balance;

	public WalletAccount(double balance) {
		setBalance(balance);
		logger.debug("Wallet account created with balance: {}", balance);
	}

	public void withdraw(double amount) throws InsufficientFundsException {

		logger.debug("Attempting to withdraw {} (current balance: {})", amount, balance);

		if (amount < 0) {
			logger.error("Thrown exception: cannot withdraw negative amount: {}", amount);
			throw new IllegalArgumentException("Cannot withdraw negative number: " + amount);

		} else if (amount > balance) {
			InsufficientFundsException ex =
					new InsufficientFundsException("Insufficient funds. Your balance is " + balance);
			logger.warn("InsufficientFundsException object created: {}", ex.getMessage());
			logger.error("Thrown exception: {}", ex.getMessage());
			throw ex;

		} else {
			balance -= amount;
			logger.debug("Withdrawal successful. Remaining balance: {}", balance);
			System.out.println("Withdrawal successful. Remaining balance: " + balance);
		}
	}

	public void deposit(double amount) throws IllegalArgumentException {

		logger.debug("Attempting to deposit {} (current balance: {})", amount, balance);

		if (amount < 0) {
			logger.error("Thrown exception: cannot deposit negative amount: {}", amount);
			throw new IllegalArgumentException("Cannot deposit negative number: " + amount);
		} else {
			balance += amount;
			logger.debug("Deposit successful. New balance: {}", balance);
			System.out.println("Deposit successful. New balance: " + balance);
		}
	}

	public void setBalance(double balance) {
		if (balance < 0) {
			logger.error("Thrown exception: balance cannot be negative: {}", balance);
			throw new IllegalArgumentException("Balance cannot be negative: " + balance);
		}

		this.balance = balance;
		
	}

}