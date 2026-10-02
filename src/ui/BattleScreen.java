package ui;

import fighters.Fighter;
import fighters.Mage;
import items.Potion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import player.Player;

import java.util.function.Consumer;

public class BattleScreen extends BorderPane {
    private final Player player;
    private final int battleNumber;
    private final Fighter playerFighter;
    private final Fighter opponent;
    private final ProgressBar playerHealthBar = new ProgressBar();
    private final ProgressBar opponentHealthBar = new ProgressBar();
    private final Label playerStats = createStatsLabel();
    private final Label opponentStats = createStatsLabel();
    private final Label turnLabel = new Label("YOUR TURN");
    private final TextArea battleLog = new TextArea();
    private final Button normalAttackButton = createActionButton("ATTACK");
    private final Button heavyAttackButton = createActionButton("HEAVY ATTACK");
    private final Button skillButton = createActionButton("SKILL");
    private final Button defendButton = createActionButton("DEFEND");
    private final Button potionButton = createActionButton("USE POTION");
    private boolean playerTurn = true;
    private boolean battleOver;
    private boolean rewardAwarded;

    public BattleScreen(Player player, Runnable backToFighterSelection) {
        this.player = player;
        this.battleNumber = player.getCurrentBattle();
        this.playerFighter = player.getFighter();
        this.opponent = player.createEnemyForCurrentBattle();

        setPadding(new Insets(20));
        setStyle("-fx-background-color: #172033;");

        Label title = new Label("BATTLE " + battleNumber);
        title.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 28px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        setTop(title);

        VBox playerPanel = createFighterPanel("PLAYER FIGHTER", playerFighter, playerStats, playerHealthBar);
        VBox opponentPanel = createFighterPanel("OPPONENT", opponent, opponentStats, opponentHealthBar);
        Label versus = new Label("VS");
        versus.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 24px; -fx-font-weight: bold;");

        HBox fighters = new HBox(20, playerPanel, versus, opponentPanel);
        fighters.setAlignment(Pos.CENTER);

        turnLabel.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 20px; -fx-font-weight: bold;");
        battleLog.setEditable(false);
        battleLog.setWrapText(true);
        battleLog.setPrefRowCount(5);
        battleLog.setStyle("-fx-control-inner-background: #202d47; -fx-text-fill: white;");
        appendLog("Battle started. Choose an action.");

        GridPane actionPanel = createActionPanel();
        VBox center = new VBox(12, turnLabel, fighters, actionPanel, battleLog);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(12, 0, 0, 0));
        setCenter(center);

        normalAttackButton.setOnAction(event -> runPlayerAction(
                "Normal attack", fighter -> fighter.attack(opponent)));
        heavyAttackButton.setOnAction(event -> runPlayerAction(
                "Heavy attack", fighter -> fighter.heavyAttack(opponent)));
        skillButton.setOnAction(event -> runPlayerAction(
                playerFighter.getSkill().getSkillName(), fighter -> fighter.useSkill(opponent)));
        defendButton.setOnAction(event -> runPlayerAction("Defend", Fighter::defend));
        potionButton.setOnAction(event -> usePotion());

        Button backButton = new Button("BACK TO FIGHTER SELECTION");
        backButton.setStyle(buttonStyle());
        backButton.setOnAction(event -> backToFighterSelection.run());

        setBottom(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER);
        BorderPane.setMargin(backButton, new Insets(10, 0, 0, 0));
        updateDisplay();
    }

    public Player getPlayer() {
        return player;
    }

    public Fighter getOpponent() {
        return opponent;
    }

    public boolean isBattleOver() {
        return battleOver;
    }

    private VBox createFighterPanel(String heading, Fighter fighter, Label stats, ProgressBar healthBar) {
        Label title = new Label(heading);
        title.setStyle("-fx-text-fill: #f4c95d; -fx-font-size: 17px; -fx-font-weight: bold;");

        Label name = new Label(fighter.getName() + " (" + fighter.getClass().getSimpleName() + ")");
        name.setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;");

        healthBar.setPrefWidth(210);
        healthBar.setStyle("-fx-accent: #d95d5d;");

        VBox panel = new VBox(8, title, name, healthBar, stats);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(14));
        panel.setPrefSize(235, 190);
        panel.setStyle("-fx-background-color: #202d47; -fx-border-color: #30466f; "
                + "-fx-border-width: 2px; -fx-border-radius: 4px;");
        return panel;
    }

    private Label createStatsLabel() {
        Label stats = new Label();
        stats.setStyle("-fx-text-fill: #d8dee9; -fx-font-size: 13px;");
        return stats;
    }

    private Button createActionButton(String text) {
        Button button = new Button(text);
        button.setStyle(buttonStyle());
        button.setPrefSize(150, 42);
        return button;
    }

    private GridPane createActionPanel() {
        GridPane actionPanel = new GridPane();
        actionPanel.setHgap(10);
        actionPanel.setVgap(10);
        actionPanel.setAlignment(Pos.CENTER);
        actionPanel.add(normalAttackButton, 0, 0);
        actionPanel.add(heavyAttackButton, 1, 0);
        actionPanel.add(skillButton, 2, 0);
        actionPanel.add(defendButton, 0, 1);
        actionPanel.add(potionButton, 1, 1);
        return actionPanel;
    }

    private void runPlayerAction(String actionName, Consumer<Fighter> action) {
        if (!playerTurn || battleOver) {
            return;
        }

        int targetHealthBefore = opponent.getHealth();
        int playerHealthBefore = playerFighter.getHealth();
        int playerManaBefore = playerFighter.getMana();
        int playerDefenseBefore = playerFighter.getDefense();
        action.accept(playerFighter);
        appendActionLog(playerFighter, actionName, opponent, targetHealthBefore,
                playerHealthBefore, playerManaBefore, playerDefenseBefore);
        updateDisplay();

        if (checkBattleEnd()) {
            return;
        }

        playerTurn = false;
        turnLabel.setText("ENEMY TURN");
        updateActionButtons();
        runEnemyTurn();
    }

    private void runEnemyTurn() {
        int targetHealthBefore = playerFighter.getHealth();
        int opponentHealthBefore = opponent.getHealth();
        int opponentManaBefore = opponent.getMana();
        int opponentDefenseBefore = opponent.getDefense();
        opponent.attack(playerFighter);
        appendActionLog(opponent, "Normal attack", playerFighter, targetHealthBefore,
                opponentHealthBefore, opponentManaBefore, opponentDefenseBefore);
        updateDisplay();

        if (checkBattleEnd()) {
            return;
        }

        playerTurn = true;
        turnLabel.setText("YOUR TURN");
        updateActionButtons();
    }

    private void usePotion() {
        if (!playerTurn || battleOver) {
            return;
        }

        Potion potion = findUsablePotion();
        if (potion == null) {
            updateActionButtons();
            return;
        }

        int healthRestored = player.usePotion(potion);
        if (healthRestored <= 0) {
            appendLog("Health is already full.");
            updateDisplay();
            return;
        }

        appendLog(playerFighter.getName() + " used " + potion.getItemName() + ".");
        appendLog(playerFighter.getName() + " recovered " + healthRestored + " HP.");
        updateDisplay();

        if (checkBattleEnd()) {
            return;
        }

        playerTurn = false;
        turnLabel.setText("ENEMY TURN");
        updateActionButtons();
        runEnemyTurn();
    }

    private void appendActionLog(Fighter actor, String actionName, Fighter target,
            int targetHealthBefore, int actorHealthBefore, int actorManaBefore,
            int actorDefenseBefore) {
        appendLog(actor.getName() + " used " + actionName + ".");
        int damage = targetHealthBefore - target.getHealth();
        if (damage > 0) {
            appendLog(target.getName() + " took " + damage + " damage."
                    + " " + target.getHealth() + " HP remaining.");
        }
        if (actor.getMana() < actorManaBefore) {
            appendLog(actor.getName() + " used "
                    + (actorManaBefore - actor.getMana()) + " mana.");
        }
        if (actor.getHealth() > actorHealthBefore) {
            appendLog(actor.getName() + " recovered "
                    + (actor.getHealth() - actorHealthBefore) + " HP.");
        }
        if (actor.getDefense() > actorDefenseBefore) {
            appendLog(actor.getName() + " defense increased.");
        }
    }

    private boolean checkBattleEnd() {
        if (!opponent.isAlive()) {
            finishBattle("VICTORY!");
            return true;
        }
        if (!playerFighter.isAlive()) {
            finishBattle("DEFEAT!");
            return true;
        }
        return false;
    }

    private void finishBattle(String result) {
        battleOver = true;
        turnLabel.setText(result);
        disableCombatButtons();
        appendLog(result);
        if ("VICTORY!".equals(result)) {
            turnLabel.setText("BATTLE " + battleNumber + " COMPLETE");
            awardVictoryRewards();
        } else {
            appendLog("XP gained: 0");
            appendLog("Coins gained: 0");
        }
    }

    private void awardVictoryRewards() {
        if (rewardAwarded) {
            return;
        }

        rewardAwarded = true;
        int levelBefore = player.getLevel();
        int levelsGained = player.awardVictoryExperience();
        int coinsGained = player.awardVictoryCoins();
        player.advanceBattle();
        appendLog("XP gained: " + player.getVictoryExperienceReward());
        appendLog("Coins gained: " + coinsGained);
        appendLog(String.format("Current XP: %d / %d",
                player.getExperience(), player.getExperienceForNextLevel()));
        for (int level = 0; level < levelsGained; level++) {
            appendLog("Level Up! You are now level " + (levelBefore + level + 1) + ".");
        }
        appendLog("Battle progression advanced to " + player.getCurrentBattle() + ".");
        updateDisplay();
    }

    private void disableCombatButtons() {
        normalAttackButton.setDisable(true);
        heavyAttackButton.setDisable(true);
        skillButton.setDisable(true);
        defendButton.setDisable(true);
        potionButton.setDisable(true);
    }

    private void updateDisplay() {
        playerStats.setText(formatStats(playerFighter));
        opponentStats.setText(formatStats(opponent));
        playerHealthBar.setProgress((double) playerFighter.getHealth() / playerFighter.getMaxHealth());
        opponentHealthBar.setProgress((double) opponent.getHealth() / opponent.getMaxHealth());
        updateActionButtons();
    }

    private Potion findUsablePotion() {
        if (playerFighter.getHealth() >= playerFighter.getMaxHealth()) {
            return null;
        }

        for (items.Item item : player.getInventory().getItems()) {
            if (item instanceof Potion potion
                    && "HP".equalsIgnoreCase(potion.getEffectUnit())
                    && potion.getEffectAmount() > 0) {
                return potion;
            }
        }
        return null;
    }

    private void updateActionButtons() {
        boolean disabled = battleOver || !playerTurn;
        normalAttackButton.setDisable(disabled);
        heavyAttackButton.setDisable(disabled);
        skillButton.setDisable(disabled);
        defendButton.setDisable(disabled);
        potionButton.setDisable(disabled || findUsablePotion() == null);
    }

    private String formatStats(Fighter fighter) {
        if (fighter == playerFighter) {
            return String.format("Level: %d%nXP: %d/%d%nHP: %d/%d%nMana: %d/%d%n"
                            + "Attack: %d%nDefense: %d%nSpeed: %d",
                    player.getLevel(), player.getExperience(), player.getExperienceForNextLevel(),
                    fighter.getHealth(), fighter.getMaxHealth(), fighter.getMana(), fighter.getMaxMana(),
                    fighter.getAttack(), fighter.getDefense(), fighter.getSpeed());
        }

        return String.format("Level: %d%nHP: %d/%d%nMana: %d/%d%n"
                        + "Attack: %d%nDefense: %d%nSpeed: %d",
                fighter.getLevel(), fighter.getHealth(), fighter.getMaxHealth(),
                fighter.getMana(), fighter.getMaxMana(), fighter.getAttack(),
                fighter.getDefense(), fighter.getSpeed());
    }

    private void appendLog(String message) {
        battleLog.appendText(message + "\n");
    }

    private String buttonStyle() {
        return "-fx-background-color: #30466f; -fx-text-fill: white; "
                + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 8px;";
    }
}
