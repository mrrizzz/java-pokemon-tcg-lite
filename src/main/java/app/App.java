package app;

import app.model.User;
import app.utils.UserManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import static app.utils.ViewManager.LOGIN_VIEW;

public class App extends Application {
    @Override
    public void start(Stage stage)  {
        UserManager.getInstance().setCurrentUser(new User());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(LOGIN_VIEW));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setTitle("Pokemon TCG Lite");
            Image icon = new Image(getClass().getResource("/assets/icon.png").toString());
            stage.getIcons().add(icon);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Error loading resources: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

