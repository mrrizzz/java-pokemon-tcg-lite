package app;

import app.model.User;
import app.utils.UserManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import static app.controller.ViewManager.LOGIN_VIEW;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        UserManager.getInstance().setCurrentUser(new User());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(LOGIN_VIEW));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setTitle("Pokemon TCG Lite");
            Image icon = new Image(Objects.requireNonNull(getClass().getResource("/assets/icon.png")).toString());
            stage.getIcons().add(icon);

            stage.setOnCloseRequest(event -> {
                event.consume();
                handleCloseRequest(stage);
            });

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Error loading resources: " + e.getMessage());
        }
    }

    private void handleCloseRequest(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Exit Application");
        alert.setContentText("Are you sure you want to exit the application?");
        alert.initOwner(stage);

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}