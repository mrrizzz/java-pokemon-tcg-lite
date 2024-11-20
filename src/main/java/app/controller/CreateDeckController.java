package app.controller;

import app.model.Deck;
import app.model.PokemonCard;
import app.model.User;
import app.utils.CardLoader;
import app.utils.PokemonCardCellFactory;
import app.utils.UserManager;
import app.utils.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CreateDeckController extends BaseController implements NavigationAware {
    @FXML
    private TextField deckNameField;
    @FXML
    private ListView<PokemonCard> availableCardsListView;
    @FXML
    private ListView<PokemonCard> selectedCardsListView;

    private ObservableList<PokemonCard> availableCardList;
    private ObservableList<PokemonCard> selectedCardList;

    @Override
    public void onNavigatedTo() {
        initializeLists();
        loadCards();
        setupListViews();
    }

    private void initializeLists() {
        availableCardList = FXCollections.observableArrayList();
        selectedCardList = FXCollections.observableArrayList();
    }

    private void loadCards() {
        try {
            URL resourceUrl = getClass().getResource("/data/pokemondata1.json");
            if (resourceUrl == null) {
                throw new IllegalArgumentException("Pokemon data file not found");
            }
            Path path = Paths.get(resourceUrl.toURI());
            List<PokemonCard> cards = CardLoader.loadPokemonCardsFromJson(path.toString());
            availableCardList.addAll(cards);
        } catch (Exception e) {
            showError("Loading Error", "Error loading Pokemon cards: " + e.getMessage());
        }
    }

    private void setupListViews() {
        PokemonCardCellFactory cellFactory = new PokemonCardCellFactory();
        availableCardsListView.setCellFactory(cellFactory);
        selectedCardsListView.setCellFactory(cellFactory);

        availableCardsListView.setItems(availableCardList);
        selectedCardsListView.setItems(selectedCardList);
    }

    @FXML
    private void handleAddCard() {
        PokemonCard selected = availableCardsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (selectedCardList.size() < 20) {
                selectedCardList.add(selected);
                availableCardList.remove(selected);
            } else {
                showInfo("Deck Full", "Cannot exceed 20 cards per deck");
            }
        }
    }

    @FXML
    private void handleRemoveCard() {
        PokemonCard selected = selectedCardsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableCardList.add(selected);
            selectedCardList.remove(selected);
        }
    }

    @FXML
    private void handleSaveDeck(ActionEvent event) {
        String deckName = deckNameField.getText();

        if (deckName.isEmpty()) {
            showError("Validation Error", "Please enter a deck name");
            return;
        }

        if (selectedCardList.isEmpty()) {
            showError("Validation Error", "Please add cards to your deck");
            return;
        }

        saveDeckToUser(deckName);
        navigateToView(ViewManager.DASHBOARD_VIEW, event);
    }

    private void saveDeckToUser(String deckName) {
        Deck newDeck = new Deck(deckName);
        selectedCardList.forEach(newDeck::addCards);

        User currentUser = UserManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUser.addDeck(newDeck);
            showInfo("Success", "Deck saved successfully");
        }
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        navigateToView(ViewManager.DASHBOARD_VIEW, event);
    }
}