// ViewDecksController.java
package app.controller;

import app.model.Deck;
import app.model.User;
import app.utils.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.stream.Collectors;

public class ViewDecksController extends BaseController implements PostFXMLInitialization {
    @FXML
    private ListView<String> availableDecksListView;
    private ObservableList<String> availableDeckList;

    @Override
    public void onNavigatedTo() {
        loadDecks();
    }

    private void loadDecks() {
        User user = UserManager.getInstance().getCurrentUser();
        List<Deck> decks = user.getDecks();

        availableDeckList = FXCollections.observableArrayList(
                decks.stream()
                        .map(Deck::getName)
                        .collect(Collectors.toList())
        );

        availableDecksListView.setItems(availableDeckList);
    }

    @FXML
    public void handleViewDeckDetails(ActionEvent event) {
        String selected = availableDecksListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Deck selectedDeck = findDeckByName(selected);
        if (selectedDeck == null) {
            showError("Error", "Selected deck not found");
            return;
        }

        try {
            ViewDeckDetailsController controller = (ViewDeckDetailsController)
                    navigateToView(DECK_DETAILS_VIEW, event);
            controller.setDeck(selectedDeck);
        } catch (Exception e) {
            showError("Navigation Error", "Error viewing deck details: " + e.getMessage());
        }
    }

    private Deck findDeckByName(String deckName) {
        User user = UserManager.getInstance().getCurrentUser();
        return user.getDecks().stream()
                .filter(deck -> deck.getName().equals(deckName))
                .findFirst()
                .orElse(null);
    }

    @FXML
    public void handleDeleteDeck() {
        String selected = availableDecksListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        boolean commitDelete = showConfirmation("Deleting Selected Deck", "Are you sure want to delete your deck");
        if (!commitDelete){
            return;
        }
        Deck deckToRemove = findDeckByName(selected);
        if (deckToRemove != null) {
            User user = UserManager.getInstance().getCurrentUser();
            user.removeDeck(deckToRemove);
            availableDeckList.remove(selected);
            showInfo("Success", selected + " deck deleted successfully");
        }
    }

    @FXML
    public void handleBackToDashboard(ActionEvent event) {
        navigateToView(DASHBOARD_VIEW, event);
    }
}