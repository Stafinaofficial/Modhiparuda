package fighters;

/**
 * A fast and agile fighter specialized in ranged attacks.
 */
public class Archer extends Fighter {
    /**
     * Creates an archer fighter.
     *
     * @param name the archer's name
     */
    public Archer(String name) {
        super(name, 110, 90, 14, 10, 14, 1, 0.04);
    }

    /**
     * Executes the Multi Shot skill.
     *
     * @param enemy the target fighter
     */
    @Override
    public void useSkill(Fighter enemy) {
        if (!isAlive() || enemy == null || !enemy.isAlive()) {
            return;
        }

        if (mana < 18) {
            System.out.println(name + " does not have enough mana for Multi Shot.");
            return;
        }

        mana -= 18;
        int damage = Math.max(4, attack + 7 + (speed / 2));
        enemy.takeDamage(damage);
        System.out.println(name + " fires Multi Shot at " + enemy.getName() + " for " + damage + " damage.");
    }
}
