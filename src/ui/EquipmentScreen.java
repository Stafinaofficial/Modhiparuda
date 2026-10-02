package ui;

import fighters.Fighter;
import items.Armor;
import items.Item;
import items.Weapon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import player.Player;

public class EquipmentScreen extends BorderPane {
    private final Player player;
    private final VBox contents = new VBox(14);

    public EquipmentScreen(Player player, Runnable backToPreviousScreen) {
        this.player = player;
        setPadding(new Insets(24));
        setStyle("-fx-background-color: #172033;");

        Label title = new Label("EQUIPMENT");
        title.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 28px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        setTop(title);

        contents.setAlignment(Pos.CENTER);
        contents.setPadding(new Insets(20));
        setCenter(contents);
        refreshContents();

        Button backButton = new Button("BACK");
        backButton.setStyle(buttonStyle());
        backButton.setOnAction(event -> backToPreviousScreen.run());
        BorderPane.setAlignment(backButton, Pos.CENTER);
        BorderPane.setMargin(backButton, new Insets(18, 0, 0, 0));
        setBottom(backButton);
    }

    public Player getPlayer() {
        return player;
    }

    private void refreshContents() {
        contents.getChildren().clear();
        if (player == null) {
            Label noPlayer = new Label("No player selected.");
            noPlayer.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 16px;");
            contents.getChildren().add(noPlayer);
            return;
        }

        contents.getChildren().add(createFighterPanel(player.getFighter()));
        contents.getChildren().add(createCurrentEquipmentRow());
        contents.getChildren().add(createAvailableItemsPanel());
    }

    private VBox createFighterPanel(Fighter fighter) {
        Label heading = new Label("CURRENT FIGHTER");
        heading.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label name = new Label(fighter.getName() + " (" + fighter.getClass().getSimpleName() + ")");
        name.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label stats = new Label(String.format(
                "HP: %d/%d%nBase Attack: %d%nWeapon Bonus: +%d%nEffective Attack: %d%n"
                        + "Base Defense: %d%nArmor Bonus: +%d%nEffective Defense: %d%nSpeed: %d",
                fighter.getHealth(), fighter.getMaxHealth(), fighter.getBaseAttack(),
                fighter.getWeaponBonus(), fighter.getEffectiveAttack(), fighter.getBaseDefense(),
                fighter.getArmorBonus(), fighter.getEffectiveDefense(), fighter.getSpeed()));
        stats.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 14px;");

        VBox panel = new VBox(8, heading, name, stats);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(14));
        panel.setPrefWidth(340);
        panel.setStyle(cardStyle());
        return panel;
    }

    private HBox createCurrentEquipmentRow() {
        VBox weaponPanel = createEquippedPanel("EQUIPPED WEAPON", player.getEquippedWeapon(),
                player.getEquippedWeapon() == null ? "No weapon equipped" : String.format(
                        "%s%nDamage: %d%nValue: %d", player.getEquippedWeapon().getItemName(),
                        player.getEquippedWeapon().getDamage(), player.getEquippedWeapon().getValue()),
                player.getEquippedWeapon() != null, () -> {
                    player.unequipWeapon();
                    refreshContents();
                });
        VBox armorPanel = createEquippedPanel("EQUIPPED ARMOR", player.getEquippedArmor(),
                player.getEquippedArmor() == null ? "No armor equipped" : String.format(
                        "%s%nDefense: %d%nValue: %d", player.getEquippedArmor().getItemName(),
                        player.getEquippedArmor().getDefense(), player.getEquippedArmor().getValue()),
                player.getEquippedArmor() != null, () -> {
                    player.unequipArmor();
                    refreshContents();
                });

        HBox row = new HBox(16, weaponPanel, armorPanel);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private VBox createEquippedPanel(String heading, Item item, String details,
            boolean canUnequip, Runnable unequipAction) {
        Label title = new Label(heading);
        title.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label information = new Label(details);
        information.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 14px;");

        Button unequipButton = new Button("UNEQUIP");
        unequipButton.setStyle(buttonStyle());
        unequipButton.setDisable(!canUnequip);
        unequipButton.setOnAction(event -> unequipAction.run());

        VBox panel = new VBox(8, title, information, unequipButton);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(14));
        panel.setPrefWidth(300);
        panel.setStyle(cardStyle());
        return panel;
    }

    private VBox createAvailableItemsPanel() {
        Label title = new Label("AVAILABLE EQUIPMENT");
        title.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 16px; -fx-font-weight: bold;");

        VBox weapons = createAvailableSection("Available Weapons", Weapon.class);
        VBox armor = createAvailableSection("Available Armor", Armor.class);
        VBox panel = new VBox(8, title, weapons, armor);
        panel.setAlignment(Pos.CENTER);
        return panel;
    }

    private <T extends Item> VBox createAvailableSection(String heading, Class<T> itemType) {
        Label sectionTitle = new Label(heading);
        sectionTitle.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 14px; -fx-font-weight: bold;");

        VBox section = new VBox(6, sectionTitle);
        section.setAlignment(Pos.CENTER);
        boolean found = false;
        for (Item item : player.getInventory().getItems()) {
            if (itemType.isInstance(item)) {
                found = true;
                section.getChildren().add(createAvailableItemRow(item, itemType));
            }
        }
        if (!found) {
            Label empty = new Label(itemType == Weapon.class
                    ? "No weapon available." : "No armor available.");
            empty.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 13px;");
            section.getChildren().add(empty);
        }
        return section;
    }

    private <T extends Item> HBox createAvailableItemRow(Item item, Class<T> itemType) {
        Label name = new Label(item.getItemName());
        name.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

        Button equipButton = new Button("EQUIP");
        equipButton.setStyle(buttonStyle());
        boolean equipped = itemType == Weapon.class
                ? item == player.getEquippedWeapon() : item == player.getEquippedArmor();
        equipButton.setDisable(equipped);
        equipButton.setOnAction(event -> {
            if (itemType == Weapon.class) {
                player.equipWeapon((Weapon) item);
            } else {
                player.equipArmor((Armor) item);
            }
            refreshContents();
        });

        HBox row = new HBox(10, name, equipButton);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private String cardStyle() {
        return "-fx-background-color: #202d47; -fx-border-color: #30466f; "
                + "-fx-border-width: 2px; -fx-border-radius: 4px;";
    }

    private String buttonStyle() {
        return "-fx-background-color: #30466f; -fx-text-fill: white; "
                + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 7px;";
    }
}
