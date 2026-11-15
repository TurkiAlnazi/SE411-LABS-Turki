package edu.psu.se411.lab09_inventory.strategy;

import java.util.Objects;
import edu.psu.se411.lab09_inventory.model.Book;
import edu.psu.se411.lab09_inventory.model.Item;

public class SearchByAuthor implements SearchStrategy<Item> {
    private final String author;

    public SearchByAuthor(String author) {
        this.author = Objects.requireNonNull(author, "Author cannot be null");
    }

    @Override
    public boolean matches(Item item) {
        return item instanceof Book book && book.getAuthor().equalsIgnoreCase(author);
    }
}
