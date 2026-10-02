package player;

import fighters.Archer;
import fighters.Assassin;
import fighters.Fighter;
import fighters.Knight;
import fighters.Mage;
import inventory.Inventory;
import items.Armor;
import items.Potion;
import items.Weapon;

import java.util.Objects;

/**
 * Represents the person playing the game and their progress.
 * A Player has a Fighter, but is not a Fighter.
 */
public class Player {
    private static final int EXPERIENCE_PER_LEVEL = 100;
    private static final int VICTORY_EXPERIENCE_REWARD = 50;
    private static final int VICTORY_COIN_REWARD = 100;
    private static final int STARTING_BATTLE = 1;

    private String playerName;
    private int level;
    private int experience;
    private int coins;
    private int currentBattle;
    private Fighter fighter;
    private final Inventory inventory;
    private Weapon equippedWeapon;
    private Armor equippedArmor;

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
        this.currentBattle = STARTING_BATTLE;
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

    public int getCurrentBattle() {
        return currentBattle;
    }

    public int advanceBattle() {
        currentBattle++;
        return currentBattle;
    }

    public Fighter createEnemyForCurrentBattle() {
        Fighter enemy = createEnemyForBattle(currentBattle);
        scaleEnemyForBattle(enemy, currentBattle);
        return enemy;
    }

    private Fighter createEnemyForBattle(int battleNumber) {
        int enemyIndex = (battleNumber - 1) % 4;
        return switch (enemyIndex) {
            case 0 -> new Mage("Opponent Mage");
            case 1 -> new Knight("Opponent Knight");
            case 2 -> new Archer("Opponent Archer");
            default -> new Assassin("Opponent Assassin");
        };
    }

    private void scaleEnemyForBattle(Fighter enemy, int battleNumber) {
        int level = Math.max(1, battleNumber);
        int levelOffset = Math.max(0, level - 1);

        enemy.setLevel(level);
        enemy.setMaxHealth(enemy.getMaxHealth() + levelOffset * 18);
        enemy.setHealth(enemy.getMaxHealth());
        enemy.setMaxMana(enemy.getMaxMana() + levelOffset * 8);
        enemy.setMana(enemy.getMaxMana());
        enemy.setAttack(enemy.getBaseAttack() + levelOffset * 3);
        enemy.setDefense(enemy.getBaseDefense() + levelOffset * 2);
    }

    public Fighter getFighter() {
        return fighter;
    }

    public void setFighter(Fighter fighter) {
        this.fighter = Objects.requireNonNull(fighter, "fighter must not be null");
        updateEquipmentBonuses();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    /**
     * Equips an inventory weapon and refreshes the fighter's derived bonus.
     *
     * @param weapon the weapon to equip
     * @return true when the weapon belongs to this player's inventory
     */
    public boolean equipWeapon(Weapon weapon) {
        if (weapon == null || !inventory.hasItem(weapon)) {
            return false;
        }

        equippedWeapon = weapon;
        updateEquipmentBonuses();
        return true;
    }

    public void unequipWeapon() {
        equippedWeapon = null;
        updateEquipmentBonuses();
    }

    /**
     * Equips an inventory armor item and refreshes the fighter's derived bonus.
     *
     * @param armor the armor to equip
     * @return true when the armor belongs to this player's inventory
     */
    public boolean equipArmor(Armor armor) {
        if (armor == null || !inventory.hasItem(armor)) {
            return false;
        }

        equippedArmor = armor;
        updateEquipmentBonuses();
        return true;
    }

    public void unequipArmor() {
        equippedArmor = null;
        updateEquipmentBonuses();
    }

    /**
     * Uses one HP potion from this player's inventory.
     *
     * @param potion the potion to use
     * @return the amount of health restored, or zero when it was not used
     */
    public int usePotion(Potion potion) {
        if (potion == null || !inventory.hasItem(potion) || !fighter.isAlive()
                || !"HP".equalsIgnoreCase(potion.getEffectUnit())
                || fighter.getHealth() >= fighter.getMaxHealth()) {
            return 0;
        }

        int healthBefore = fighter.getHealth();
        fighter.heal(potion.getEffectAmount());
        int healthRestored = fighter.getHealth() - healthBefore;
        if (healthRestored > 0) {
            inventory.removeItem(potion);
        }
        return healthRestored;
    }

    private void updateEquipmentBonuses() {
        int weaponBonus = equippedWeapon == null ? 0 : equippedWeapon.getDamage();
        int armorBonus = equippedArmor == null ? 0 : equippedArmor.getDefense();
        fighter.setEquipmentBonuses(weaponBonus, armorBonus);
    }

    /**
     * Adds experience and levels the player up for each completed threshold.
     *
     * @param amount the experience to add
     */
    public int addExperience(int amount) {
        if (amount <= 0) {
            return 0;
        }

        experience += amount;
        int levelsGained = 0;
        while (experience >= EXPERIENCE_PER_LEVEL) {
            experience -= EXPERIENCE_PER_LEVEL;
            levelUp();
            levelsGained++;
        }
        return levelsGained;
    }

    public int getVictoryExperienceReward() {
        return VICTORY_EXPERIENCE_REWARD;
    }

    public int awardVictoryExperience() {
        return addExperience(VICTORY_EXPERIENCE_REWARD);
    }

    public int getVictoryCoinReward() {
        return VICTORY_COIN_REWARD;
    }

    public int awardVictoryCoins() {
        addCoins(VICTORY_COIN_REWARD);
        return VICTORY_COIN_REWARD;
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
        fighter.levelUp();
    }

    public int getExperienceForNextLevel() {
        return EXPERIENCE_PER_LEVEL;
    }
}
