package skills;

import fighters.Fighter;

/**
 * Abstract base class for fighter abilities.
 */
public abstract class Skill {
    private final String skillName;
    private final int manaCost;
    private final int damage;

    /**
     * Creates a skill with its display properties and base damage value.
     *
     * @param skillName the skill's display name
     * @param manaCost the mana required to use the skill
     * @param damage the skill's base damage contribution
     */
    protected Skill(String skillName, int manaCost, int damage) {
        this.skillName = skillName;
        this.manaCost = manaCost;
        this.damage = damage;
    }

    public String getSkillName() {
        return skillName;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getDamage() {
        return damage;
    }

    /**
     * Uses this skill against a target fighter.
     *
     * @param attacker the fighter using the skill
     * @param target the fighter being targeted
     */
    public abstract void use(Fighter attacker, Fighter target);

    /**
     * Checks the common target and fighter state required by every skill.
     */
    protected boolean canUse(Fighter attacker, Fighter target) {
        return attacker != null
                && target != null
                && attacker.isAlive()
                && target.isAlive();
    }

    /**
     * Charges this skill's mana cost when enough mana is available.
     */
    protected boolean consumeMana(Fighter attacker) {
        if (attacker.getMana() < manaCost) {
            return false;
        }

        attacker.setMana(attacker.getMana() - manaCost);
        return true;
    }
}
