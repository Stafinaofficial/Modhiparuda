package fighters;

/**
 * A durable front-line fighter specialized in defense and crowd control.
 */
public class Knight extends Fighter {
    /**
     * Creates a knight fighter.
     *
     * @param name the knight's name
     */
    public Knight(String name) {
        super(name, 140, 70, 16, 18, 8, 1, 0.05);
    }

    /**
     * Executes the Shield Slam skill.
     *
     * @param enemy the target fighter
     */
    @Override
    public void useSkill(Fighter enemy) {
        if (!isAlive() || enemy == null || !enemy.isAlive()) {
            return;
        }

        if (mana < 20) {
            System.out.println(name + " does not have enough mana for Shield Slam.");
            return;
        }

        mana -= 20;
        int damage = Math.max(5, attack + 8 - (enemy.getDefense() / 2));
        enemy.takeDamage(damage);
        defense += 2;
        System.out.println(name + " uses Shield Slam on " + enemy.getName() + " for " + damage + " damage.");
    }
}
