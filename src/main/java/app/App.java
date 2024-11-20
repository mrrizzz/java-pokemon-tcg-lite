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


public class App extends Application {
    @Override
    public void start(Stage stage)  {
        UserManager.getInstance().setCurrentUser(new User());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginPage.fxml"));
            Parent loginRoot = loader.load();

            Scene scene = new Scene(loginRoot);
            scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());

            stage.setTitle("Pokemon TCG Lite");
            Image icon = new Image(getClass().getResource("/assets/icon.png").toString());
            stage.getIcons().add(icon);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            // Handle FXML loading error
            System.err.println("Error loading FXML: " + e.getMessage());
            // Bisa tambahkan alert dialog untuk user
        } catch (IllegalStateException e) {
            // Handle resource loading errors (CSS/Image)
            System.err.println("Error loading resources: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

