package app.controller;

import app.model.Deck;
import app.model.PokemonCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DeckDetailsController {
    @FXML
    public Label deckTitle;

    private Deck deck;

    private ObservableList<PokemonCard> availableCardList;

    @FXML
    ListView<PokemonCard> availableCardsListView;

    public void setDeck(Deck deck){
        this.deck = deck;
        initializeDetails();
    }
    private void initializeDetails(){

        if (deck == null) {
            showError("Error: No deck data available");
            return;
        }
        deckTitle.setText(deck.getName() + "'s Deck Details");

        availableCardList = FXCollections.observableArrayList();
        try {
            List<PokemonCard> cards = deck.getCards();
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

            availableCardsListView.setItems(availableCardList);

            System.out.println("ini dari details " + deck.getName());
        } catch (Exception e) {
            showError("Error initializing dashboard: " + e.getMessage());
        }
    }

    public void handleBackToDashboard(ActionEvent event) {
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

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleEditDeck(ActionEvent event) {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editDeck.fxml"));
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
