package battle;

import fighters.Fighter;

import java.util.Objects;

/**
 * Coordinates the actions and state checks for a battle between two fighters.
 *
 * Battle decides which action happens next, while Fighter remains responsible
 * for attack, skill, damage, and health calculations.
 */
public class Battle {
    private final Fighter fighter1;
    private final Fighter fighter2;

    /**
     * Creates a battle between two fighters.
     *
     * @param fighter1 the first fighter
     * @param fighter2 the second fighter
     */
    public Battle(Fighter fighter1, Fighter fighter2) {
        this.fighter1 = Objects.requireNonNull(fighter1, "fighter1 must not be null");
        this.fighter2 = Objects.requireNonNull(fighter2, "fighter2 must not be null");
    }

    /**
     * Runs the automated test battle sequence.
     */
    public void startBattle() {
        System.out.println("===== ARENA FIGHTERS =====");
        System.out.println("\nInitial fighter stats:");
        fighter1.displayStats();
        fighter2.displayStats();

        System.out.println("\n" + fighter1.getName() + " attacks " + fighter2.getName() + ".");
        fighter1.attack(fighter2);
        System.out.println(fighter2.getName() + " updated health: " + fighter2.getHealth());
        if (!fighter2.isAlive()) {
            announceWinner(fighter1, fighter2);
            return;
        }

        System.out.println("\n" + fighter2.getName() + " uses a special skill on " + fighter1.getName() + ".");
        fighter2.useSkill(fighter1);
        System.out.println(fighter1.getName() + " updated health: " + fighter1.getHealth());
        if (!fighter1.isAlive()) {
            announceWinner(fighter2, fighter1);
            return;
        }

        System.out.println("\n" + fighter1.getName() + " performs a heavy attack on " + fighter2.getName() + ".");
        fighter1.heavyAttack(fighter2);
        System.out.println(fighter2.getName() + " updated health: " + fighter2.getHealth());
        announceBattleStatus();

        System.out.println("\n===== TEST COMPLETED =====");
    }

    private void announceBattleStatus() {
        if (!fighter1.isAlive()) {
            announceWinner(fighter2, fighter1);
        } else if (!fighter2.isAlive()) {
            announceWinner(fighter1, fighter2);
        } else {
            System.out.println("Battle continues...");
        }
    }

    private void announceWinner(Fighter winner, Fighter defeated) {
        System.out.println(defeated.getName() + " has been defeated!");
        System.out.println(winner.getName() + " wins the battle!");
    }
}
