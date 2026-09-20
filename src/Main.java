import fighters.*;
import battle.Battle;
import skills.Fireball;
import skills.ShieldSlam;
import skills.Skill;
import player.Player;
import inventory.Inventory;
import items.Armor;
import items.Item;
import items.Potion;
import items.Weapon;

/**
 * Entry point for the Arena Fighters test scenario.
 */
public class Main {
    public static void main(String[] args) {
        Knight arthur = new Knight("Arthur");
        Mage merlin = new Mage("Merlin");

        Player player = new Player("Stafina", arthur);
        System.out.println("===== PLAYER TEST =====");
        System.out.println("Player: " + player.getPlayerName());
        System.out.println("Level: " + player.getLevel());
        System.out.println("Coins: " + player.getCoins());
        player.getFighter().displayStats();

        player.addExperience(150);
        player.addCoins(100);
        player.spendCoins(40);
        System.out.println("After progress update:");
        System.out.println("Level: " + player.getLevel());
        System.out.println("Experience: " + player.getExperience());
        System.out.println("Coins: " + player.getCoins());

        Inventory inventory = player.getInventory();
        Item sword = new Weapon("Iron Sword", 100, 20);
        Item armor = new Armor("Steel Armor", 120, 15);
        Item healthPotion = new Potion("Health Potion", 50, "Restore", 30, "HP");
        inventory.addItem(sword);
        inventory.addItem(armor);
        inventory.addItem(healthPotion);
        inventory.displayInventory();
        System.out.println("Has Iron Sword: " + inventory.hasItem(sword));
        System.out.println("Items in inventory: " + inventory.getItemCount());

        System.out.println("===== SKILL POLYMORPHISM TEST =====");
        Skill skill1 = new Fireball();
        Skill skill2 = new ShieldSlam();
        Mage testMage = new Mage("Test Mage");
        Knight testKnight = new Knight("Test Knight");
        skill1.use(testMage, testKnight);
        skill2.use(testKnight, testMage);

        Battle battle = new Battle(player.getFighter(), merlin);
        battle.startBattle();
    }
}