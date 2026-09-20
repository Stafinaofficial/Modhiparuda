package items;

/**
 * An item that increases offensive power.
 */
public class Weapon extends Item {
    private int damage;

    public Weapon(String itemName, int value, int damage) {
        super(itemName, value);
        setDamage(damage);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = Math.max(0, damage);
    }

    @Override
    public void displayInfo() {
        System.out.println(getItemName());
        System.out.println("Damage: " + damage);
        System.out.println("Value: " + getValue());
    }
}
