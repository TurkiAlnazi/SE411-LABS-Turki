package edu.psu.se411.lab12_threads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CachedPoolApp {
    public static void main(String[] args) throws InterruptedException, IOException {
        Files.createDirectories(Path.of("logs"));
        Logger logger = LoggerFactory.getLogger(CachedPoolApp.class);
        logger.info("Cached pool application is starting...");
        try {
            int transactions = (args.length > 0 && args[0].equalsIgnoreCase("quick"))
                    ? 20000 : 1000000;
            for (int accountCount : new int[] {1000, 10}) {
                Simulation.Result result = Simulation.run(accountCount, transactions, true);
                Simulation.displayResult("Cached", accountCount, transactions, result);
            }
        } finally {
            logger.info("Cached pool application is closing...");
        }
    }
}
