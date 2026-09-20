package items;

import java.util.Objects;

/**
 * Abstract base class for all items that can be stored in an inventory.
 */
public abstract class Item {
    private String itemName;
    private int value;

    /**
     * Creates an item with a name and shop value.
     *
     * @param itemName the item's name
     * @param value the item's value
     */
    protected Item(String itemName, int value) {
        this.itemName = Objects.requireNonNull(itemName, "itemName must not be null");
        setValue(value);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = Objects.requireNonNull(itemName, "itemName must not be null");
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = Math.max(0, value);
    }

    /**
     * Displays the details specific to this item type.
     */
    public abstract void displayInfo();
}
