package app.controller;

import app.model.Deck;
import app.model.PokemonCard;
import app.model.User;
import app.utils.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewDecksController {
    @FXML
    private ListView<String> availableDecksListView;

    private ObservableList<String> availableDeckList;

    @FXML
    private void initialize() {
        User user = UserManager.getInstance().getCurrentUser();
        System.out.println("ini nami dari viewdeck" + user.getUsername());
        List<Deck> decks = user.getDecks();
        availableDeckList = FXCollections.observableArrayList();


        List<String> decktitles = new ArrayList<>();
        for (Deck deck : decks){
            System.out.println(deck.getName());
            decktitles.add(deck.getName());
        }
        availableDeckList.addAll(decktitles);
        availableDecksListView.setItems(availableDeckList);
        // Load and display all cards in the collection
    }

    public void handleViewDeckDetails(ActionEvent event) {
        String selected = availableDecksListView.getSelectionModel()
                .getSelectedItem();
        if (selected != null) {
            Deck viewDetails = null;
            User user = UserManager.getInstance().getCurrentUser();

            List<Deck> decks = user.getDecks();
            for (Deck deck : decks) {
                if (deck.getName().equals(selected)) {
                    viewDetails = deck;
                    break;
                }
            }

            if (viewDetails == null) {
                showAlert("Error", "Selected deck not found");
                return;
            }

            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewDeckDetails.fxml"));
                Parent dashboardRoot = loader.load();

                DeckDetailsController deckDetailsController = loader.getController();
                deckDetailsController.setDeck(viewDetails);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

                Scene scene = new Scene(dashboardRoot);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                showAlert("Error loading FXML: ", e.getMessage());
                System.err.println("Error loading FXML: " + e.getMessage());
            }
        }
    }

    public void handleDeleteDeck(ActionEvent event) {
        String selected = availableDecksListView.getSelectionModel()
                .getSelectedItem();
        if (selected != null){
            Deck toRemove = null;
            User user = UserManager.getInstance().getCurrentUser();

            List<Deck> decks = user.getDecks();
            for (Deck deck : decks){
                if (deck.getName().equals(selected)){
                    toRemove = deck;
                    break;
                }
            }

            if (toRemove != null){
                user.removeDeck(toRemove);
                availableDeckList.remove(selected);
                showAlert("SUccess", selected + "Deck deleted successfully");
            }
        }
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}