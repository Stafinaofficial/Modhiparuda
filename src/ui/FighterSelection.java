package ui;

import fighters.Archer;
import fighters.Assassin;
import fighters.Fighter;
import fighters.Knight;
import fighters.Mage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import player.Player;

import java.util.function.Consumer;

public class FighterSelection extends BorderPane {
    private final Consumer<Player> playerSelected;
    private final Label selectionMessage = new Label();
    private Button selectedButton;
    private Player selectedPlayer;

    public FighterSelection(Consumer<Player> playerSelected, Runnable backToMainMenu) {
        this(playerSelected, backToMainMenu, null);
    }

    public FighterSelection(Consumer<Player> playerSelected, Runnable backToMainMenu,
            Player existingPlayer) {
        selectedPlayer = existingPlayer;
        this.playerSelected = playerSelected;
        setPadding(new Insets(24));
        setStyle("-fx-background-color: #172033;");

        Label title = new Label("CHOOSE YOUR FIGHTER");
        title.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 28px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        setTop(title);

        GridPane fighterGrid = new GridPane();
        fighterGrid.setHgap(16);
        fighterGrid.setVgap(16);
        fighterGrid.setAlignment(Pos.CENTER);

        fighterGrid.add(createFighterCard("KNIGHT", new Knight("Knight")), 0, 0);
        fighterGrid.add(createFighterCard("MAGE", new Mage("Mage")), 1, 0);
        fighterGrid.add(createFighterCard("ARCHER", new Archer("Archer")), 0, 1);
        fighterGrid.add(createFighterCard("ASSASSIN", new Assassin("Assassin")), 1, 1);
        setCenter(fighterGrid);

        Button backButton = new Button("BACK");
        backButton.setOnAction(event -> backToMainMenu.run());
        backButton.setStyle(buttonStyle());

        selectionMessage.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 14px;");
        VBox bottom = new VBox(10, selectionMessage, backButton);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(18, 0, 0, 0));
        setBottom(bottom);
    }

    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    private VBox createFighterCard(String className, Fighter fighter) {
        Label name = new Label(className);
        name.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 20px; -fx-font-weight: bold;");

        Label stats = new Label(String.format(
                "Health: %d/%d%nAttack: %d%nDefense: %d%nSpeed: %d",
                fighter.getHealth(), fighter.getMaxHealth(), fighter.getAttack(),
                fighter.getDefense(), fighter.getSpeed()));
        stats.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 14px;");

        Button selectButton = new Button("SELECT");
        selectButton.setStyle(buttonStyle());
        selectButton.setOnAction(event -> selectFighter(selectButton, fighter));

        VBox card = new VBox(10, name, stats, selectButton);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(16));
        card.setPrefSize(220, 150);
        return card;
    }

    private void selectFighter(Button button, Fighter fighter) {
        if (selectedPlayer == null) {
            selectedPlayer = new Player("Player", fighter);
        } else {
            selectedPlayer.setFighter(fighter);
        }
        playerSelected.accept(selectedPlayer);

        if (selectedButton != null) {
            selectedButton.setStyle(buttonStyle());
        }
        selectedButton = button;
        selectedButton.setStyle(buttonStyle() + "-fx-border-color: #f4c95d; -fx-border-width: 2px;");
        selectionMessage.setText(fighter.getName() + " selected!");
    }

    private String buttonStyle() {
        return "-fx-background-color: #30466f; -fx-text-fill: white; "
                + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-pref-width: 120px; "
                + "-fx-padding: 8px;";
    }

}