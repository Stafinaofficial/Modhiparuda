package fighters;

import skills.MultiShot;

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
        setSkill(new MultiShot());
    }
}
