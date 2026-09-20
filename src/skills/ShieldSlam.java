package skills;

import fighters.Fighter;

/**
 * Knight skill that deals physical damage and increases defense.
 */
public class ShieldSlam extends Skill {
    public ShieldSlam() {
        super("Shield Slam", 20, 8);
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

        int finalDamage = Math.max(5, attacker.getAttack() + getDamage() - (target.getDefense() / 2));
        target.takeDamage(finalDamage);
        attacker.setDefense(attacker.getDefense() + 2);
        System.out.println(attacker.getName() + " uses Shield Slam on " + target.getName()
                + " for " + finalDamage + " damage.");
    }
}
