package edu.psu.se411.lab09_inventory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab09_inventory.model.Book;
import edu.psu.se411.lab09_inventory.model.ElectronicDevice;
import edu.psu.se411.lab09_inventory.model.Item;
import edu.psu.se411.lab09_inventory.strategy.SearchByAuthor;
import edu.psu.se411.lab09_inventory.strategy.SearchByCategory;
import edu.psu.se411.lab09_inventory.strategy.SearchById;
import edu.psu.se411.lab09_inventory.strategy.SearchByName;

public class App {
    static {
        try {
            Files.createDirectories(Path.of("logs"));
        } catch (IOException e) {
            throw new UncheckedIOException("Could not create the log directory", e);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Application is starting...");

        try {
            Inventory<Item> inventory = new Inventory<>();

            Book firstBook = new Book(101, "Concurrency in Java", "Brian Goetz");
            Book secondBook = new Book(102, "Effective Java", "Joshua Bloch");
            ElectronicDevice laptop = new ElectronicDevice(201, "Dell Laptop", "Computer");
            ElectronicDevice phone = new ElectronicDevice(202, "Samsung Phone", "Mobile");

            inventory.addItem(firstBook);
            inventory.addItem(secondBook);
            inventory.addItem(laptop);
            inventory.addItem(phone);

            System.out.println("All inventory items:");
            displayInventory(inventory);

            showResults("Search by name (book)",
                    inventory.findItems(new SearchByName("Concurrency in Java")));
            showResults("Search by name (electronic device)",
                    inventory.findItems(new SearchByName("Dell Laptop")));
            showResults("Search by ID", inventory.findItems(new SearchById(202)));
            showResults("Search by author", inventory.findItems(new SearchByAuthor("Brian Goetz")));
            showResults("Search by category", inventory.findItems(new SearchByCategory("Computer")));

            showResults("Search using a lambda expression",
                    inventory.findItems(item -> item.getName().contains("Java")));

            inventory.removeItem(phone);
            System.out.println("Inventory after removing Samsung Phone:");
            displayInventory(inventory);

            Inventory<Book> bookInventory = new Inventory<>();
            bookInventory.addItem(firstBook);
            bookInventory.addItem(secondBook);
            System.out.println("Books only:");
            displayInventory(bookInventory);
        } catch (RuntimeException e) {
            logger.error("Inventory operation failed", e);
            throw e;
        } finally {
            logger.info("Application is closing...");
        }
    }

    public static void displayInventory(Inventory<?> inventory) {
        for (Object item : inventory.getAllItems()) {
            System.out.println(item);
        }
        System.out.println();
    }

    private static void showResults(String label, List<? extends Item> items) {
        System.out.println(label + ":");
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.println();
    }
}
