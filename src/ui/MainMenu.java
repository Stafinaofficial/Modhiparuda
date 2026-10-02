package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import player.Player;

public class MainMenu extends BorderPane {
    public MainMenu(Stage stage, Runnable startGame, Runnable openInventory,
            Runnable openEquipment, Player player) {
        setStyle("-fx-background-color: #172033;");

        Label title = new Label("ARENA FIGHTERS");
        title.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 32px; -fx-font-weight: bold;");

        Label coins = new Label("Coins: " + (player == null ? 0 : player.getCoins()));
        coins.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 15px;");

        Button startButton = new Button("START GAME");
        startButton.setOnAction(event -> startGame.run());

        Button inventoryButton = new Button("INVENTORY");
        inventoryButton.setDisable(player == null);
        inventoryButton.setOnAction(event -> openInventory.run());

        Button equipmentButton = new Button("EQUIPMENT");
        equipmentButton.setOnAction(event -> openEquipment.run());

        Button exitButton = new Button("EXIT");
        exitButton.setOnAction(event -> stage.close());

        String buttonStyle = "-fx-background-color: #30466f; -fx-text-fill: white; "
                + "-fx-font-size: 15px; -fx-font-weight: bold; -fx-pref-width: 180px; "
                + "-fx-padding: 10px;";
        startButton.setStyle(buttonStyle);
        inventoryButton.setStyle(buttonStyle);
        equipmentButton.setStyle(buttonStyle);
        exitButton.setStyle(buttonStyle);

        VBox menu = new VBox(18, title, coins, startButton, inventoryButton, equipmentButton, exitButton);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        setCenter(menu);
    }
}