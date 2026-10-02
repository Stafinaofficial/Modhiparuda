package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import player.Player;

public class MainApplication extends Application {
    private Stage stage;
    private Player selectedPlayer;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Arena Fighters");
        showMainMenu();
        stage.show();
    }

    private void showMainMenu() {
        MainMenu mainMenu = new MainMenu(
            stage,
            this::showFighterSelection,
            this::showInventory,
            this::showEquipment,
            selectedPlayer);
        stage.setScene(new Scene(mainMenu, 600, 400));
    }

    private void showFighterSelection() {
        FighterSelection fighterSelection = new FighterSelection(
                this::showBattleScreen,
                this::showMainMenu,
                selectedPlayer);
        stage.setScene(new Scene(fighterSelection, 600, 500));
    }

    private void showBattleScreen(Player player) {
        selectedPlayer = player;
        BattleScreen battleScreen = new BattleScreen(player, this::showFighterSelection);
        stage.setScene(new Scene(battleScreen, 900, 700));
    }

    private void showInventory() {
        if (selectedPlayer == null) {
            return;
        }

        InventoryScreen inventoryScreen = new InventoryScreen(selectedPlayer, this::showMainMenu);
        stage.setScene(new Scene(inventoryScreen, 600, 500));
    }

    private void showEquipment() {
        EquipmentScreen equipmentScreen = new EquipmentScreen(selectedPlayer, this::showMainMenu);
        stage.setScene(new Scene(equipmentScreen, 700, 550));
    }

    public static void main(String[] args) {
        launch(args);
    }
}