package app.controller;

import app.model.User;
import app.model.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        System.out.println(username);
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Parent dashboardRoot = loader.load();
            DashboardController dashboardController = loader.getController();

            currentUser.setUsername(username);
            System.out.println(currentUser.getUsername());

            UserManager.getInstance().setCurrentUser(currentUser);
            dashboardController.sayHello();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            Scene scene = new Scene(dashboardRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }
}
