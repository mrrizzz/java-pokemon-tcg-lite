// Refactored DashboardController
package app.controller;

import app.model.User;
import app.utils.UserManager;
import app.utils.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController extends BaseController implements NavigationAware {
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
        navigateToView(ViewManager.CREATE_DECK_VIEW, event);
    }

    @FXML
    public void handleViewDecksButton(ActionEvent event) {
        navigateToView(ViewManager.VIEW_DECKS_VIEW, event);
    }

    @FXML
    public void handleVersusButton() {
        showInfo("Feature Not Available",
                "This feature would be available if the deadline was longer than a week 🙏");
    }
}
