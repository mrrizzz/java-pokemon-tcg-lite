package app.controller;

import app.model.Deck;
import app.model.PokemonCard;
import app.model.User;
import app.utils.CardLoader;
import app.utils.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EditDeckController {
    private Deck deck;

    private List<PokemonCard> alreadySelectedCards;

    @FXML
    private TextField deckNameField;
    @FXML
    private ListView<PokemonCard> availableCardsListView;
    @FXML
    private ListView<PokemonCard> selectedCardsListView;

    private ObservableList<PokemonCard> availableCardList;
    private ObservableList<PokemonCard> selectedCardList;

    public void setDeck(Deck deck){
        this.deck = deck;
        deckNameField.setText(deck.getName());
        alreadySelectedCards = deck.getCards();

        initialize();
    }

    private void initialize() {
        availableCardList = FXCollections.observableArrayList();
        selectedCardList = FXCollections.observableArrayList();


//        selectedCardList.addAll(deck.getCards());
        try {
            URL resourceUrl = getClass().getResource("/data/pokemondata.json");

            if (resourceUrl == null) {
                throw new IllegalArgumentException("File pokemondata.json tidak ditemukan");
            }
            Path path = Paths.get(resourceUrl.toURI());

            List<PokemonCard> cards = CardLoader.loadPokemonCardsFromJson(path.toString());
            availableCardList.addAll(cards);

            availableCardsListView.setCellFactory(pokemonCardListView -> new ListCell<PokemonCard>(){
                private ImageView imageView = new ImageView();

                @Override
                protected void updateItem(PokemonCard card, boolean empty){
                    super.updateItem(card, empty);

                    if (empty || card == null){
                        setText(null);
                        setGraphic(null);
                    }

                    else {
                        setText(card.getName() + "(" + card.getSubtypes() + ")");
                        try {
                            String imageUrl = card.getImageUrl();
                            URL resourceUrl = getClass().getResource(imageUrl);

                            if (resourceUrl == null) {
                                System.out.println("Resource tidak ditemukan: " + imageUrl);
                                setGraphic(null);
                                return;
                            }

                            Image image = new Image(resourceUrl.toString());
                            imageView.setImage(image);
                            imageView.setFitHeight(140);
                            imageView.setFitWidth(100);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            setGraphic(null);
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

            selectedCardsListView.setCellFactory(pokemonCardListView -> new ListCell<PokemonCard>(){
                private ImageView imageView = new ImageView();

                @Override
                protected void updateItem(PokemonCard card, boolean empty){
                    super.updateItem(card, empty);

                    if (empty || card == null){
                        setText(null);
                        setGraphic(null);
                    }

                    else {
                        setText(card.getName() + "(" + card.getSubtypes() + ")");
                        try {
                            String imageUrl = card.getImageUrl();
                            URL resourceUrl = getClass().getResource(imageUrl);


                            if (resourceUrl == null) {
                                System.out.println("Resource tidak ditemukan: " + imageUrl);
                                setGraphic(null);
                                return;
                            }

                            Image image = new Image(resourceUrl.toString());
                            imageView.setImage(image);
                            imageView.setFitHeight(140);
                            imageView.setFitWidth(100);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            setGraphic(null);
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            selectedCardList.addAll(alreadySelectedCards);
            availableCardList.removeAll(alreadySelectedCards);

            availableCardsListView.setItems(availableCardList);
            selectedCardsListView.setItems(selectedCardList);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleAddCard() {
        PokemonCard selected = availableCardsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (selectedCardList.size() < 20){
                selectedCardsListView.getItems().add(selected);
                availableCardsListView.getItems().remove(selected);
            }
            else {
                showAlert("Deck penuh", "Tidak bisa melebihi 20 kartu per deck");
            }
        }
    }

    @FXML
    private void handleRemoveCard() {
        PokemonCard selected = selectedCardsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableCardsListView.getItems().add(selected);
            selectedCardsListView.getItems().remove(selected);
        }
    }

    @FXML
    private void handleSaveDeck(ActionEvent event) {
        String deckname = deckNameField.getText();

        if (deckname.isEmpty()){
            showAlert("Nama Deck Kosong", "SIlakan isi nama deck terlebih dahulu");
            return;
        }

        if (selectedCardList.isEmpty()){
            showAlert("Deck Kosong", "Silakan isi deck terlebih dahulu");
            return;
        }

        Deck newDeck = deck;
        for(PokemonCard card : selectedCardList){
            newDeck.addCards(card);
        }

        System.out.println("ini deck getname  saat saving" + newDeck.getName());
        User currentUser = UserManager.getInstance().getCurrentUser();

        if (currentUser != null){
            showAlert("Success", "Deck tersimpan");

            selectedCardList.clear();
            availableCardList.addAll(selectedCardsListView.getItems());
        }
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Parent dashboardRoot = loader.load();

            DashboardController dashboardController = loader.getController();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            Scene scene = new Scene(dashboardRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void handleBackToDashboard(ActionEvent event) {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Parent dashboardRoot = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            Scene scene = new Scene(dashboardRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }
}

