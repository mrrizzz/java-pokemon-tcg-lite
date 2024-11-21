// Refactored LoginController
package app.controller;

import app.model.User;
import app.utils.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {
    @FXML
    private TextField usernameField;

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();

        if (username.isEmpty()) {
            showError("Login Error", "Username cannot be empty!");
            return;
        }

        User currentUser = new User();
        currentUser.setUsername(username);
        UserManager.getInstance().setCurrentUser(currentUser);

        navigateToView(DASHBOARD_VIEW, event);
    }
}