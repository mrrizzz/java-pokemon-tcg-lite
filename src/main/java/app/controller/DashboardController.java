package app.controller;

import app.model.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label welcomeLabel;
    public void sayHello(){
        String username = UserManager.getInstance().getCurrentUser().getUsername();
        welcomeLabel.setText("Hello, " + username + "!");
    }

    @FXML
    public void handleCreateDeckButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/create-deck.fxml"));
        Parent dashboardRoot = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Scene scene = new Scene(dashboardRoot);
        stage.setScene(scene);
        stage.show();
    }
}
