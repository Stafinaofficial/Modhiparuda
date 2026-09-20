package items;

/**
 * An item that increases defensive power.
 */
public class Armor extends Item {
    private int defense;

    public Armor(String itemName, int value, int defense) {
        super(itemName, value);
        setDefense(defense);
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = Math.max(0, defense);
    }

    @Override
    public void displayInfo() {
        System.out.println(getItemName());
        System.out.println("Defense: " + defense);
        System.out.println("Value: " + getValue());
    }
}
