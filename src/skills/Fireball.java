package skills;

import fighters.Fighter;

/**
 * Mage skill that deals spell damage based on attack and maximum mana.
 */
public class Fireball extends Skill {
    public Fireball() {
        super("Fireball", 24, 10);
    }

    @Override
    public void use(Fighter attacker, Fighter target) {
        if (!canUse(attacker, target)) {
            return;
        }

        if (!consumeMana(attacker)) {
            System.out.println(attacker.getName() + " does not have enough mana for " + getSkillName() + ".");
            return;
        }

        int finalDamage = Math.max(6, attacker.getAttack() + getDamage() + (attacker.getMaxMana() / 20));
        target.takeDamage(finalDamage);
        System.out.println(attacker.getName() + " casts Fireball on " + target.getName()
                + " for " + finalDamage + " damage.");
    }
}
