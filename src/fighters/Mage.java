package fighters;

import skills.Fireball;

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
        setSkill(new Fireball());
    }
}
