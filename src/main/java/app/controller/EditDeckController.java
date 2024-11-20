package app.controller;

import app.model.Deck;
import app.model.PokemonCard;
import app.utils.CardLoader;
import app.utils.PokemonCardCellFactory;
import app.utils.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EditDeckController extends BaseController {
    @FXML
    private TextField deckNameField;
    @FXML
    private ListView<PokemonCard> availableCardsListView;
    @FXML
    private ListView<PokemonCard> selectedCardsListView;

    private Deck deck;
    private ObservableList<PokemonCard> availableCardList;
    private ObservableList<PokemonCard> selectedCardList;

    public void setDeck(Deck deck) {
        this.deck = deck;
        deckNameField.setText(deck.getName());
        initialize();
    }

    private void initialize() {
        initializeLists();
        loadCards();
        setupListViews();
    }

    private void initializeLists() {
        availableCardList = FXCollections.observableArrayList();
        selectedCardList = FXCollections.observableArrayList(deck.getCards());
    }

    private void loadCards() {
        try {
            URL resourceUrl = getClass().getResource("/data/pokemondata.json");
            if (resourceUrl == null) {
                throw new IllegalArgumentException("Pokemon data file not found");
            }
            Path path = Paths.get(resourceUrl.toURI());
            List<PokemonCard> cards = CardLoader.loadPokemonCardsFromJson(path.toString());
            availableCardList.addAll(cards);
            availableCardList.removeAll(selectedCardList);
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

        if (validateDeck(deckName)) {
            updateDeck();
            showInfo("Success", "Deck updated successfully");
            navigateToView(ViewManager.DASHBOARD_VIEW, event);
        }
    }

    private boolean validateDeck(String deckName) {
        if (deckName.isEmpty()) {
            showError("Validation Error", "Please enter a deck name");
            return false;
        }

        if (selectedCardList.isEmpty()) {
            showError("Validation Error", "Please add cards to your deck");
            return false;
        }

        return true;
    }

    private void updateDeck() {
        deck.setName(deckNameField.getText());
        selectedCardList.forEach(deck::addCards);
    }

    @FXML
    public void handleBackToDashboard(ActionEvent event) {
        navigateToView(ViewManager.DASHBOARD_VIEW, event);
    }
}