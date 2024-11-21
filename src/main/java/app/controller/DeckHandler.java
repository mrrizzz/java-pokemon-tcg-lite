package app.controller;

import app.model.PokemonCard;
import app.utils.CardLoader;
import app.utils.PokemonCardCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public abstract class DeckHandler extends BaseController {
    @FXML
    protected TextField deckNameField;
    @FXML
    protected ListView<PokemonCard> availableCardsListView;
    @FXML
    protected ListView<PokemonCard> selectedCardsListView;
    @FXML
    protected ComboBox<String> typeFilterComboBox;

    protected FilteredList<PokemonCard> filteredCards;
    protected ObservableList<PokemonCard> availableCardList;
    protected ObservableList<PokemonCard> selectedCardList;

    protected void initializeLists() {
        availableCardList = FXCollections.observableArrayList();
        selectedCardList = FXCollections.observableArrayList();
        initializeTypeFilter();
    }

    protected void loadCards() {
        try {
            URL resourceUrl = getClass().getResource("/data/pokemondata1.json");
            if (resourceUrl == null) {
                throw new IllegalArgumentException("Pokemon data file not found");
            }
            Path path = Paths.get(resourceUrl.toURI());
            List<PokemonCard> cards = CardLoader.loadPokemonCardsFromJson(path.toString());
            availableCardList.addAll(cards);
            availableCardList.removeAll(selectedCardList);
            setupFiltering();
        } catch (Exception e) {
            showError("Loading Error", "Error loading Pokemon cards: " + e.getMessage());
        }
    }

    private void initializeTypeFilter() {
        ObservableList<String> types = FXCollections.observableArrayList(
                "All", "Fire", "Lightning", "Water", "Grass",
                "Darkness", "Psychic", "Colorless", "Dragon"
        );
        typeFilterComboBox.setItems(types);
        typeFilterComboBox.setValue("All");
    }

    private void setupFiltering() {
        filteredCards = new FilteredList<>(availableCardList);
        typeFilterComboBox.setOnAction(event -> filterCards());
        availableCardsListView.setItems(filteredCards);
    }

    private void filterCards() {
        String selectedType = typeFilterComboBox.getValue();

        filteredCards.setPredicate(card -> {
            if (selectedType == null || selectedType.equals("ALL")) {
                return true;
            }
            String cardType = card.getTypes();
            return cardType != null && cardType.equalsIgnoreCase(selectedType);
        });
    }

    protected void setupListViews() {
        PokemonCardCellFactory cellFactory = new PokemonCardCellFactory();
        availableCardsListView.setCellFactory(cellFactory);
        selectedCardsListView.setCellFactory(cellFactory);
        selectedCardsListView.setItems(selectedCardList);
    }

    protected boolean validateDeck(String deckName) {
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

    @FXML
    protected abstract void handleSaveDeck(ActionEvent event);

    @FXML
    protected void handleAddCard() {
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
    protected void handleRemoveCard() {
        PokemonCard selected = selectedCardsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableCardList.add(selected);
            selectedCardList.remove(selected);
        }
    }

    @FXML
    protected void handleBackToDashboard(ActionEvent event) {
        if (withoutSavingConfirmation()) {
            return;
        }
        navigateToView(DASHBOARD_VIEW, event);
    }
}