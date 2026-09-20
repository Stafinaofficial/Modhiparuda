package items;

import java.util.Objects;

/**
 * A consumable item with a named effect and effect amount.
 */
public class Potion extends Item {
    private String effect;
    private int effectAmount;
    private String effectUnit;

    public Potion(String itemName, int value, String effect, int effectAmount, String effectUnit) {
        super(itemName, value);
        this.effect = Objects.requireNonNull(effect, "effect must not be null");
        this.effectUnit = Objects.requireNonNull(effectUnit, "effectUnit must not be null");
        setEffectAmount(effectAmount);
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = Objects.requireNonNull(effect, "effect must not be null");
    }

    public int getEffectAmount() {
        return effectAmount;
    }

    public void setEffectAmount(int effectAmount) {
        this.effectAmount = Math.max(0, effectAmount);
    }

    public String getEffectUnit() {
        return effectUnit;
    }

    public void setEffectUnit(String effectUnit) {
        this.effectUnit = Objects.requireNonNull(effectUnit, "effectUnit must not be null");
    }

    @Override
    public void displayInfo() {
        System.out.println(getItemName());
        System.out.println("Effect: " + effect + " " + effectAmount + " " + effectUnit);
        System.out.println("Value: " + getValue());
    }
}
