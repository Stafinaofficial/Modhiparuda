package skills;

import fighters.Fighter;

/**
 * Assassin skill with high damage and a chance of a devastating critical hit.
 */
public class ShadowStrike extends Skill {
    public ShadowStrike() {
        super("Shadow Strike", 16, 9);
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

        int finalDamage = Math.max(7, attacker.getAttack() + getDamage() + (attacker.getSpeed() / 3));
        if (Math.random() < attacker.getCriticalChance()) {
            finalDamage *= 2;
            System.out.println(attacker.getName() + " lands a devastating critical blow!");
        }

        target.takeDamage(finalDamage);
        System.out.println(attacker.getName() + " uses Shadow Strike on " + target.getName()
                + " for " + finalDamage + " damage.");
    }
}
