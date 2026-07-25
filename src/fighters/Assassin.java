package fighters;

/**
 * A lethal fighter built around speed, precision, and critical strikes.
 */
public class Assassin extends Fighter {
    /**
     * Creates an assassin fighter.
     *
     * @param name the assassin's name
     */
    public Assassin(String name) {
        super(name, 105, 80, 17, 8, 13, 1, 0.70);
    }

    /**
     * Executes the Shadow Strike skill.
     *
     * @param enemy the target fighter
     */
    @Override
    public void useSkill(Fighter enemy) {
        if (!isAlive() || enemy == null || !enemy.isAlive()) {
            return;
        }

        if (mana < 16) {
            System.out.println(name + " does not have enough mana for Shadow Strike.");
            return;
        }

        mana -= 16;
        int damage = Math.max(7, attack + 9 + (speed / 3));
        if (Math.random() < criticalChance) {
            damage *= 2;
            System.out.println(name + " lands a devastating critical blow!");
        }

        enemy.takeDamage(damage);
        System.out.println(name + " uses Shadow Strike on " + enemy.getName() + " for " + damage + " damage.");
    }
}
