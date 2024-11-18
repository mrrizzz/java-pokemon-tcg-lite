package app.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;

public class CollectionController {
    @FXML
    private GridPane cardGrid;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private void initialize() {
        // Load and display all cards in the collection
    }

    public void refreshCollection() {
        // Refresh the displayed cards
    }
}