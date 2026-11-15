package edu.psu.se411.lab09_inventory.strategy;

import java.util.Objects;
import edu.psu.se411.lab09_inventory.model.Item;

public class SearchByName implements SearchStrategy<Item> {
    private final String name;

    public SearchByName(String name) {
        this.name = Objects.requireNonNull(name, "Search name cannot be null");
    }

    @Override
    public boolean matches(Item item) {
        return item.getName().equalsIgnoreCase(name);
    }
}
