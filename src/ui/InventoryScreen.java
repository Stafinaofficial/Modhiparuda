package ui;

import items.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import player.Player;

public class InventoryScreen extends BorderPane {
    private final Player player;

    public InventoryScreen(Player player, Runnable backToPreviousScreen) {
        this.player = player;
        setPadding(new Insets(24));
        setStyle("-fx-background-color: #172033;");

        Label title = new Label("INVENTORY");
        title.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 28px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        setTop(title);

        VBox contents = new VBox(12);
        contents.setAlignment(Pos.CENTER);
        contents.setPadding(new Insets(24));

        int itemCount = player.getInventory().getItemCount();
        Label count = new Label("Items: " + itemCount);
        count.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 15px;");
        contents.getChildren().add(count);

        if (itemCount == 0) {
            Label emptyMessage = new Label("Inventory is empty.");
            emptyMessage.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 16px;");
            contents.getChildren().add(emptyMessage);
        } else {
            for (Item item : player.getInventory().getItems()) {
                contents.getChildren().add(createItemCard(item));
            }
        }
        setCenter(contents);

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

    private VBox createItemCard(Item item) {
        Label name = new Label(item.getItemName());
        name.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label details = new Label(String.format("Type: %s%nValue: %d",
                item.getClass().getSimpleName(), item.getValue()));
        details.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 14px;");

        VBox card = new VBox(8, name, details);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(12));
        card.setPrefWidth(280);
        card.setStyle("-fx-background-color: #202d47; -fx-border-color: #30466f; "
                + "-fx-border-width: 2px; -fx-border-radius: 4px;");
        return card;
    }

    private String buttonStyle() {
        return "-fx-background-color: #30466f; -fx-text-fill: white; "
                + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-pref-width: 180px; "
                + "-fx-padding: 8px;";
    }
}