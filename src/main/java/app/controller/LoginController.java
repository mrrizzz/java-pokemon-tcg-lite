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
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    public TextField usernameField;



    @FXML
    public void handleLogin(ActionEvent event) {

        User currentUser = new User();
        String username = usernameField.getText();
        if (username.isEmpty()) {
            showError("Username tidak boleh kosong!");
            return;
        }
        System.out.println(username);
        currentUser.setUsername(username);
        System.out.println(currentUser.getUsername());

        UserManager.getInstance().setCurrentUser(currentUser);
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
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
