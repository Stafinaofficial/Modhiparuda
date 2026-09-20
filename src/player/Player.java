package player;

import fighters.Fighter;
import inventory.Inventory;

import java.util.Objects;

/**
 * Represents the person playing the game and their progress.
 * A Player has a Fighter, but is not a Fighter.
 */
public class Player {
    private static final int EXPERIENCE_PER_LEVEL = 100;

    private String playerName;
    private int level;
    private int experience;
    private int coins;
    private Fighter fighter;
    private final Inventory inventory;

    /**
     * Creates a new player at level one with no experience or coins.
     *
     * @param playerName the player's name
     * @param fighter the fighter controlled by the player
     */
    public Player(String playerName, Fighter fighter) {
        this.playerName = Objects.requireNonNull(playerName, "playerName must not be null");
        this.fighter = Objects.requireNonNull(fighter, "fighter must not be null");
        this.level = 1;
        this.experience = 0;
        this.coins = 0;
        this.inventory = new Inventory();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = Objects.requireNonNull(playerName, "playerName must not be null");
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getCoins() {
        return coins;
    }

    public Fighter getFighter() {
        return fighter;
    }

    public void setFighter(Fighter fighter) {
        this.fighter = Objects.requireNonNull(fighter, "fighter must not be null");
    }

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Adds experience and levels the player up for each completed threshold.
     *
     * @param amount the experience to add
     */
    public void addExperience(int amount) {
        if (amount <= 0) {
            return;
        }

        experience += amount;
        while (experience >= EXPERIENCE_PER_LEVEL) {
            experience -= EXPERIENCE_PER_LEVEL;
            levelUp();
        }
    }

    /**
     * Adds coins to the player's balance.
     *
     * @param amount the number of coins to add
     */
    public void addCoins(int amount) {
        if (amount > 0) {
            coins += amount;
        }
    }

    /**
     * Spends coins when the player has enough available.
     *
     * @param amount the number of coins to spend
     * @return true when the purchase was affordable and completed
     */
    public boolean spendCoins(int amount) {
        if (amount < 0 || amount > coins) {
            return false;
        }

        coins -= amount;
        return true;
    }

    /**
     * Advances the player's progress by one level.
     */
    public void levelUp() {
        level++;
    }
}
