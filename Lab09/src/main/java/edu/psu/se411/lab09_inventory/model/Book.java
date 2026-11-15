package edu.psu.se411.lab09_inventory.model;

import java.util.Objects;

public class Book extends Item {
    private final String author;

    public Book(int id, String name, String author) {
        super(id, name);
        this.author = Objects.requireNonNull(author, "Author cannot be null");
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{id=" + getId() + ", name='" + getName()
                + "', author='" + author + "'}";
    }
}
