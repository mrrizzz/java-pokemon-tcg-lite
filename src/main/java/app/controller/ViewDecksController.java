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
        System.out.println(decks);
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

    public void refreshCollection() {
        // Refresh the displayed cards
    }

    public void handleViewDeckDetails(ActionEvent event) {
    }

    public void handleDeleteDeck(ActionEvent event) {
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
}