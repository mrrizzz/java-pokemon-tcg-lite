package app.controller;

import app.model.User;
import app.utils.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void initialize(){
        try {
            sayHello();
        } catch (Exception e) {
            showError("Error initializing dashboard: " + e.getMessage());
        }
    }

    private void sayHello() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            System.out.println("sayhello " + currentUser.getUsername());
            welcomeLabel.setText("Hello, " + currentUser.getUsername() + "!");
        } else {
            welcomeLabel.setText("Hello, Guest!");
        }
    }
    @FXML
    public void handleCreateDeckButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/createDeck.fxml"));
        Parent dashboardRoot = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Scene scene = new Scene(dashboardRoot);
        stage.setScene(scene);
        stage.show();
    }

    public void handleViewDecksButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewDecks.fxml"));
        try{
            Parent dashboardRoot = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            Scene scene = new Scene(dashboardRoot);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
