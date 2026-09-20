package fighters;

import skills.ShieldSlam;

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
        setSkill(new ShieldSlam());
    }
}
