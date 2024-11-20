module POKEMON.TCG.LITE {
    requires java.desktop;
    requires javafx.controls;
    requires java.smartcardio;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports app;
    opens app to javafx.fxml;

    exports app.controller;
    opens app.controller to javafx.fxml;

    exports app.model;
    opens app.model to javafx.fxml;
    exports app.utils;
    opens app.utils to javafx.fxml;
}