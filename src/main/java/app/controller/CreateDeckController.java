package app.controller;

import app.model.Deck;
import app.model.User;
import app.utils.UserManager;
import javafx.event.ActionEvent;

public class CreateDeckController extends DeckHandler implements PostFXMLInitialization {
    @Override
    public void onNavigatedTo() {
        initializeLists();
        loadCards();
        setupListViews();
    }

    @Override
    protected void handleSaveDeck(ActionEvent event) {
        String deckName = deckNameField.getText();

        if (validateDeck(deckName)) {
            saveDeckToUser(deckName);
            navigateToView(DASHBOARD_VIEW, event);
        }
    }

    private void saveDeckToUser(String deckName) {
        Deck newDeck = new Deck(deckName);
        selectedCardList.forEach(newDeck::addCard);

        User currentUser = UserManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUser.addDeck(newDeck);
            showInfo("Success", "Deck saved successfully");
        }
    }
}
