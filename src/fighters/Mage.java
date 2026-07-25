package fighters;

/**
 * A magical fighter with exceptional mana and ranged spell damage.
 */
public class Mage extends Fighter {
    /**
     * Creates a mage fighter.
     *
     * @param name the mage's name
     */
    public Mage(String name) {
        super(name, 100, 140, 15, 8, 9, 1, 0.02);
    }

    /**
     * Executes the Fireball skill.
     *
     * @param enemy the target fighter
     */
    @Override
    public void useSkill(Fighter enemy) {
        if (!isAlive() || enemy == null || !enemy.isAlive()) {
            return;
        }

        if (mana < 24) {
            System.out.println(name + " does not have enough mana for Fireball.");
            return;
        }

        mana -= 24;
        int damage = Math.max(6, attack + 10 + (maxMana / 20));
        enemy.takeDamage(damage);
        System.out.println(name + " casts Fireball on " + enemy.getName() + " for " + damage + " damage.");
    }
}
