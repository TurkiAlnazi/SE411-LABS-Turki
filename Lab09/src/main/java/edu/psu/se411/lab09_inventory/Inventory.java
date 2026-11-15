package edu.psu.se411.lab09_inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.psu.se411.lab09_inventory.strategy.SearchStrategy;

public class Inventory<T> {
    private final List<T> items = new ArrayList<>();

    public void addItem(T item) {
        items.add(Objects.requireNonNull(item, "Item cannot be null"));
    }

    public boolean removeItem(T item) {
        return items.remove(item);
    }

    public List<T> getAllItems() {
        return List.copyOf(items);
    }

    public List<T> findItems(SearchStrategy<? super T> strategy) {
        Objects.requireNonNull(strategy, "Search strategy cannot be null");
        List<T> results = new ArrayList<>();
        for (T item : items) {
            if (strategy.matches(item)) {
                results.add(item);
            }
        }
        return results;
    }
}
