package app.controller;

import app.model.Deck;
import app.model.PokemonCard;
import app.utils.PokemonCardCellFactory;
import app.utils.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ViewDeckDetailsController extends BaseController {
    @FXML
    private Label deckTitle;
    @FXML
    private ListView<PokemonCard> availableCardsListView;

    private Deck deck;

    public void setDeck(Deck deck) {
        this.deck = deck;
        initializeDetails();
    }

    private void initializeDetails() {
        if (deck == null) {
            showError("Error", "No deck data available");
            return;
        }

        deckTitle.setText(deck.getName() + "'s Deck Details");
        setupCardList();
    }

    private void setupCardList() {
        ObservableList<PokemonCard> cardList = FXCollections.observableArrayList(deck.getCards());
        availableCardsListView.setCellFactory(new PokemonCardCellFactory());
        availableCardsListView.setItems(cardList);
    }

    @FXML
    public void handleEditDeck(ActionEvent event) {
        try {
            EditDeckController controller = (EditDeckController)
                    navigateToView(ViewManager.EDIT_DECK_VIEW, event);
            controller.setDeck(deck);
        } catch (Exception e) {
            showError("Navigation Error", "Error navigating to edit deck: " + e.getMessage());
        }
    }

    @FXML
    public void handleBackToPreviousPage(ActionEvent event) {
        navigateToView(ViewManager.VIEW_DECKS_VIEW, event);
    }

    @FXML
    public void handleBackToDashboard(ActionEvent event) {
        navigateToView(ViewManager.DASHBOARD_VIEW, event);
    }
}
