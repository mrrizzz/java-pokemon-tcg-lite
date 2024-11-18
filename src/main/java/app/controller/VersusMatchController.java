package app.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class VersusMatchController {
    @FXML
    private HBox playerDeck;
    @FXML
    private HBox opponentDeck;
    @FXML
    private Label playerLifePoints;
    @FXML
    private Label opponentLifePoints;
    @FXML
    private Button drawCardButton;
    @FXML
    private Button endTurnButton;

    @FXML
    private void initialize() {
        // Initialize game state
        playerLifePoints.setText("LP: 100");
        opponentLifePoints.setText("LP: 100");
    }

    @FXML
    private void handleDrawCard() {
        // Draw card logic
    }

    @FXML
    private void handleEndTurn() {
        // End turn logic
    }
}