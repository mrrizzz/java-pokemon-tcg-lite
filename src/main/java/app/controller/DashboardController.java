// Refactored DashboardController
package app.controller;

import app.model.User;
import app.utils.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController extends BaseController implements PostFXMLInitialization {
    @FXML
    private Label welcomeLabel;

    @Override
    public void onNavigatedTo() {
        sayHello();
    }

    private void sayHello() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        welcomeLabel.setText(currentUser != null
                ? "Hello, " + currentUser.getUsername() + "!"
                : "Hello, Guest!");
    }

    @FXML
    public void handleCreateDeckButton(ActionEvent event) {
        navigateToView(CREATE_DECK_VIEW, event);
    }

    @FXML
    public void handleViewDecksButton(ActionEvent event) {
        navigateToView(VIEW_DECKS_VIEW, event);
    }

    @FXML
    public void handleVersusButton() {
        showInfo("Feature Not Available",
                "This feature might be available if the deadline was longer than a week 🙏");
    }
}
