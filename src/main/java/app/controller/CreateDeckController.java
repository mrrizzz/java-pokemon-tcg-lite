// CreateDeckController.java
package app.controller;

import app.model.PokemonCard;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class CreateDeckController {
    @FXML
    private TextField deckNameField;
    @FXML
    private ListView<PokemonCard> availableCards;
    @FXML
    private ListView<PokemonCard> selectedCards;
    @FXML
    private Button addCardButton;
    @FXML
    private Button removeCardButton;
    @FXML
    private Button saveDeckButton;

    @FXML
    private void initialize() {
        // Initialize your card lists here
    }

    @FXML
    private void handleAddCard() {
        PokemonCard selected = availableCards.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedCards.getItems().add(selected);
            availableCards.getItems().remove(selected);
        }
    }

    @FXML
    private void handleRemoveCard() {
        PokemonCard selected = selectedCards.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableCards.getItems().add(selected);
            selectedCards.getItems().remove(selected);
        }
    }

    @FXML
    private void handleSaveDeck() {
        // Save deck logic here
    }
}

