package lab05edu.psu.se411;

import edu.psu.se411.exceptions.InvalidAgeException;
import edu.psu.se411.exceptions.InsufficientFundsException;

public class App {

    // Exercise 1
    public static void validateAge(int age) throws InvalidAgeException {

        if (age < 18) {
            throw new InvalidAgeException(
                "Age must be 18 or older."
            );
        }

        System.out.println("Age valid.");
    }

    public static void main(String[] args) {

        // =========================
        // Exercise 1
        // =========================

        System.out.println("=== Exercise 1 ===");

        try {
            validateAge(20);
        } catch (InvalidAgeException e) {
            System.out.println(
                "Invalid age: " + e.getMessage()
            );
        }
        
        try {
            validateAge(15);
        } catch (InvalidAgeException e) {
            System.out.println(
                "Invalid age: " + e.getMessage()
            );
        }

        // =========================
        // Exercise 2
        // =========================

        System.out.println();
        System.out.println("=== Exercise 2 ===");

        Wallet wallet = new Wallet(500.00);

        System.out.println(
            "Initial wallet balance: "
            + wallet.getBalance()
        );

        // Successful withdrawal
        try {

            wallet.withdraw(200.00);

        } catch (InsufficientFundsException e) {

            System.out.println(
                "Withdrawal failed: "
                + e.getMessage()
            );
        }

        // Withdrawal with insufficient funds
        try {

            wallet.withdraw(400.00);

        } catch (InsufficientFundsException e) {

            System.out.println(
                "Withdrawal failed: "
                + e.getMessage()
            );
        }
    }
}
