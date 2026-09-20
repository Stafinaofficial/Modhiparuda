package skills;

import fighters.Fighter;

/**
 * Archer skill that deals damage based on the attacker's speed.
 */
public class MultiShot extends Skill {
    public MultiShot() {
        super("Multi Shot", 18, 7);
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

        int finalDamage = Math.max(4, attacker.getAttack() + getDamage() + (attacker.getSpeed() / 2));
        target.takeDamage(finalDamage);
        System.out.println(attacker.getName() + " fires Multi Shot at " + target.getName()
                + " for " + finalDamage + " damage.");
    }
}
