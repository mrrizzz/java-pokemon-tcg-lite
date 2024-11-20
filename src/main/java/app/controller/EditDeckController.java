package app.controller;

import app.model.Deck;
import app.utils.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditDeckController extends DeckHandler {
    @FXML
    private TextField deckNameField;

    private Deck deck;

    public void setDeck(Deck deck) {
        this.deck = deck;
        deckNameField.setText(deck.getName());
        initialize();
    }

    private void initialize() {
        initializeLists();
        selectedCardList.addAll(deck.getCards());
        loadCards();
        setupListViews();
    }

    @Override
    protected void handleSaveDeck(ActionEvent event) {
        String deckName = deckNameField.getText();

        if (validateDeck(deckName)) {
            updateDeck();
            navigateToView(ViewManager.DASHBOARD_VIEW, event);
        }
    }

    private void updateDeck() {
        deck.setName(deckNameField.getText());
        selectedCardList.forEach(deck::addCards);
        showInfo("Success", "Deck updated successfully");
    }

    @FXML
    public void handleBackToPreviousPage(ActionEvent event) {
        if (withoutSavingConfirmation()){
            return;
        }
        navigateToView(ViewManager.VIEW_DECKS_VIEW, event);
    }
}
