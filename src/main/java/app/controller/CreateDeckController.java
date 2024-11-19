// CreateDeckController.java
package app.controller;

import app.model.Deck;
import app.model.PokemonCard;
import app.model.User;
import app.utils.CardLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CreateDeckController {
    @FXML
    private TextField deckNameField;
    @FXML
    private ListView<PokemonCard> availableCards;
    @FXML
    private ListView<PokemonCard> selectedCards;

    private User currentUser;
    private ObservableList<PokemonCard> availableCardList;
    private ObservableList<PokemonCard> selectedCardList;

    @FXML
    private void initialize() {
        availableCardList = FXCollections.observableArrayList();
        selectedCardList = FXCollections.observableArrayList();
        try {
            URL resourceUrl = getClass().getResource("/data/pokemondata.json");

            if (resourceUrl == null) {
                throw new IllegalArgumentException("File pokemondata.json tidak ditemukan");
            }
            Path path = Paths.get(resourceUrl.toURI());
            System.out.println(path);
            List<PokemonCard> cards = CardLoader.loadPokemonCardsFromJson(path.toString());
            availableCardList.addAll(cards);

            availableCards.setItems(availableCardList);
            selectedCards.setItems(selectedCardList);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleAddCard() {
        PokemonCard selected = availableCards.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (selectedCardList.size() < 20){
                selectedCards.getItems().add(selected);
                availableCards.getItems().remove(selected);
            }
            else {
                showAlert("Deck penuh", "Tidak bisa melebihi 20 kartu per deck");
            }
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
        String deckname = deckNameField.getText();

        if (deckname.isEmpty()){
            showAlert("Nama Deck Kosong", "SIlakan isi nama deck terlebih dahulu");
            return;
        }

        if (selectedCardList.isEmpty()){
            showAlert("Deck Kosong", "Silakan isi deck terlebih dahulu");
        }

        Deck newDeck = new Deck(deckname);
        for(PokemonCard card : selectedCardList){
            newDeck.addCards(card);
        }

        if (currentUser != null){
            currentUser.addDeck(newDeck);
            showAlert("Success", "Deck tersimpan");

            deckNameField.clear();
            selectedCardList.clear();
            availableCardList.addAll(selectedCards.getItems());
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

