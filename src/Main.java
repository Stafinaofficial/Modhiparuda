import fighters.*;

/**
 * Entry point for the Arena Fighters test scenario.
 */
public class Main {
    public static void main(String[] args) {
        Knight arthur = new Knight("Arthur");
        Mage merlin = new Mage("Merlin");

        System.out.println("===== ARENA FIGHTERS =====");

        System.out.println("\nInitial fighter stats:");
        arthur.displayStats();
        merlin.displayStats();

        System.out.println("\nRound 1: Arthur attacks Merlin.");
        arthur.attack(merlin);
        System.out.println("Merlin updated health: " + merlin.getHealth());
        checkBattleStatus(arthur, merlin);

        System.out.println("\nMerlin usesSkill on Arthur.");
        merlin.useSkill(arthur);
        System.out.println("Arthur updated health: " + arthur.getHealth());
        checkBattleStatus(arthur, merlin);

        System.out.println("\nArthur performs heavyAttack on Merlin.");
        arthur.heavyAttack(merlin);
        System.out.println("Merlin updated health: " + merlin.getHealth());
        checkBattleStatus(arthur, merlin);

        System.out.println("\n===== TEST COMPLETED =====");
    }

    /**
     * Checks whether either fighter has been defeated and prints the appropriate message.
     *
     * @param fighter1 the first fighter
     * @param fighter2 the second fighter
     */
    private static void checkBattleStatus(Fighter fighter1, Fighter fighter2) {
        if (!fighter1.isAlive()) {
            System.out.println(fighter1.getName() + " has been defeated!");
        } else if (!fighter2.isAlive()) {
            System.out.println(fighter2.getName() + " has been defeated!");
        } else {
            System.out.println("Battle continues...");
        }
    }
}