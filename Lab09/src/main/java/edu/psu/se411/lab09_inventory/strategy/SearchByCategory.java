package edu.psu.se411.lab09_inventory.strategy;

import java.util.Objects;
import edu.psu.se411.lab09_inventory.model.ElectronicDevice;
import edu.psu.se411.lab09_inventory.model.Item;

public class SearchByCategory implements SearchStrategy<Item> {
    private final String category;

    public SearchByCategory(String category) {
        this.category = Objects.requireNonNull(category, "Category cannot be null");
    }

    @Override
    public boolean matches(Item item) {
        return item instanceof ElectronicDevice device
                && device.getCategory().equalsIgnoreCase(category);
    }
}
