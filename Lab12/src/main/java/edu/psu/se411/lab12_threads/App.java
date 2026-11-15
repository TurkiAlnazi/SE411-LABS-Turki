package edu.psu.se411.lab12_threads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    public static void main(String[] args) throws InterruptedException, IOException {
        Files.createDirectories(Path.of("logs"));
        Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("Application is starting...");
        try {
            int transactions = (args.length > 0 && args[0].equalsIgnoreCase("quick"))
                    ? 20000 : 1000000;
            System.out.println("Comparing fixed and cached thread pools:");
            for (int accountCount : new int[] {1000, 10}) {
                Simulation.Result fixed = Simulation.run(accountCount, transactions, false);
                Simulation.Result cached = Simulation.run(accountCount, transactions, true);
                Simulation.displayResult("Fixed", accountCount, transactions, fixed);
                Simulation.displayResult("Cached", accountCount, transactions, cached);
                System.out.printf("Difference (cached - fixed): %d ms%n%n",
                        cached.elapsedMs - fixed.elapsedMs);
            }
            System.out.println("Observation: Performance depends on the account contention "
                    + "and the number of active threads.");
            System.out.println("Without consistent lock ordering, two opposite transfers "
                    + "could deadlock. Account ID ordering prevents circular waiting.");
        } finally {
            logger.info("Application is closing...");
        }
    }
}
