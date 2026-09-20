package inventory;

import items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Stores item objects owned by a player.
 */
public class Inventory {
    private final ArrayList<Item> items;

    /**
     * Creates an empty inventory.
     */
    public Inventory() {
        items = new ArrayList<>();
    }

    /**
     * Adds an item to the inventory.
     *
     * @param item the item to add
     */
    public void addItem(Item item) {
        items.add(Objects.requireNonNull(item, "item must not be null"));
    }

    /**
     * Removes the first matching item.
     *
     * @param item the item to remove
     * @return true if an item was removed
     */
    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    /**
     * Checks whether the inventory contains an item.
     *
     * @param item the item to find
     * @return true if the item exists
     */
    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    /**
     * Displays the details of all current items.
     */
    public void displayInventory() {
        System.out.println("=== Inventory ===");
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        for (Item item : items) {
            item.displayInfo();
            System.out.println();
        }
    }

    public int getItemCount() {
        return items.size();
    }

    /**
     * Returns a copy so callers cannot modify the internal collection.
     *
     * @return a snapshot of the current item names
     */
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
