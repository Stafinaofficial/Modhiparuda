package fighters;

/**
 * Abstract base class for all arena fighters.
 */
public abstract class Fighter {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int mana;
    protected int maxMana;
    protected int attack;
    protected int defense;
    protected int speed;
    protected int level;
    protected int experience;
    protected double criticalChance;

    /**
     * Creates a fighter with the given base attributes.
     *
     * @param name the fighter's name
     * @param maxHealth the maximum health value
     * @param maxMana the maximum mana value
     * @param attack the base attack power
     * @param defense the base defense value
     * @param speed the movement and initiative stat
     * @param level the starting level
     * @param criticalChance the chance of landing a critical hit
     */
    protected Fighter(String name, int maxHealth, int maxMana, int attack, int defense, int speed, int level, double criticalChance) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.maxMana = maxMana;
        this.mana = maxMana;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.level = level;
        this.experience = 0;
        this.criticalChance = criticalChance;
    }

    /**
     * Performs a standard attack against an enemy fighter.
     *
     * @param enemy the target fighter
     */
    public void attack(Fighter enemy) {
        if (!isAlive() || enemy == null || !enemy.isAlive()) {
            return;
        }

        int damage = calculateDamage(this.attack, enemy.defense, false);
        if (Math.random() < criticalChance) {
            damage = (int) (damage * 1.5);
            System.out.println(name + " lands a critical hit!");
        }

        enemy.takeDamage(damage);
        System.out.println(name + " attacks " + enemy.getName() + " for " + damage + " damage.");
    }

    /**
     * Performs a stronger attack against an enemy fighter.
     *
     * @param enemy the target fighter
     */
    public void heavyAttack(Fighter enemy) {
        if (!isAlive() || enemy == null || !enemy.isAlive()) {
            return;
        }

        int damage = calculateDamage(this.attack + 6, enemy.defense, true);
        enemy.takeDamage(damage);
        System.out.println(name + " uses a heavy attack on " + enemy.getName() + " for " + damage + " damage.");
    }

    /**
     * Increases defense temporarily and restores a small amount of health.
     */
    public void defend() {
        if (!isAlive()) {
            return;
        }

        defense += 2;
        health = Math.min(maxHealth, health + 4);
        System.out.println(name + " braces for impact.");
    }

    /**
     * Uses the fighter's class-specific skill.
     *
     * @param enemy the target fighter
     */
    public abstract void useSkill(Fighter enemy);

    /**
     * Applies damage to this fighter.
     *
     * @param damage the incoming damage amount
     */
    public void takeDamage(int damage) {
        int actualDamage = Math.max(0, damage - (defense / 2));
        health = Math.max(0, health - actualDamage);

        if (!isAlive()) {
            System.out.println(name + " has been defeated.");
        }
    }

    /**
     * Restores health up to the maximum health value.
     *
     * @param amount the amount of health to recover
     */
    public void heal(int amount) {
        if (!isAlive()) {
            return;
        }

        health = Math.min(maxHealth, health + amount);
    }

    /**
     * Checks whether the fighter is still alive.
     *
     * @return true if health is above zero
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Increases the fighter's level and improves its stats.
     */
    public void levelUp() {
        level++;
        maxHealth += 12;
        maxMana += 6;
        attack += 3;
        defense += 2;
        speed += 1;
        health = maxHealth;
        mana = maxMana;
        experience = Math.max(0, experience - 100);
        System.out.println(name + " reached level " + level + "!");
    }

    /**
     * Prints the fighter's current stats to the console.
     */
    public void displayStats() {
        System.out.println("=== " + name + " ===");
        System.out.println("Level: " + level);
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Mana: " + mana + "/" + maxMana);
        System.out.println("Attack: " + attack);
        System.out.println("Defense: " + defense);
        System.out.println("Speed: " + speed);
        System.out.println("Experience: " + experience);
    }

    /**
     * Calculates the final damage for an attack.
     *
     * @param baseAttack the base attack power
     * @param enemyDefense the target defense value
     * @param heavy whether this is a heavy attack
     * @return the final damage amount
     */
    protected int calculateDamage(int baseAttack, int enemyDefense, boolean heavy) {
        int modifier = heavy ? 4 : 1;
        return Math.max(1, baseAttack + speed / 3 + modifier - (enemyDefense / 2));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(maxHealth, health));
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = Math.max(1, maxHealth);
        this.health = Math.min(this.health, this.maxHealth);
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(maxMana, mana));
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = Math.max(1, maxMana);
        this.mana = Math.min(this.mana, this.maxMana);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = Math.max(1, attack);
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = Math.max(0, defense);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = Math.max(1, speed);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(1, level);
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = Math.max(0, experience);
    }
}
