package fighters;

import skills.ShadowStrike;

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
        setSkill(new ShadowStrike());
    }
}
