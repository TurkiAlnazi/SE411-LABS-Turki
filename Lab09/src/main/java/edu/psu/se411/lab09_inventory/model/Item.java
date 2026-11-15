package edu.psu.se411.lab09_inventory.model;

import java.util.Objects;

public abstract class Item {
    private final int id;
    private final String name;

    protected Item(int id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
