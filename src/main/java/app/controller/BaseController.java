package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public abstract class BaseController {
    protected Object navigateToView(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            Object controller = loader.getController();
            if (controller instanceof initByFXMLRunTime) {
                ((initByFXMLRunTime) controller).onNavigatedTo();
            }
            return controller;
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            showError("Navigation Error", "Error loading view: " + e.getMessage());
        }
        return null;
    }

    protected void showError(String title, String message) {
        showAlert(Alert.AlertType.ERROR, title, message);
    }

    protected void showInfo(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    protected boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    protected boolean withoutSavingConfirmation() {
        return !showConfirmation(
                "Discard Without Saving",
                "Are you sure you want to discard your deck?"
        );
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}