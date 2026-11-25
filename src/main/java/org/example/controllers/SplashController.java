package org.example.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * Controller for the Hotelix ivory splash screen.
 */
public class SplashController {

    @FXML
    private VBox rootPane;

    @FXML
    private Label promptLabel;

    private Runnable onContinueHandler;
    private boolean continued = false;

    @FXML
    private void initialize() {
        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> triggerContinue());
        Platform.runLater(() -> rootPane.requestFocus());
    }

    /**
     * Registers an action to execute when the user presses a key to continue.
     *
     * @param onContinueHandler callback invoked once upon key press
     */
    public void setOnContinueHandler(Runnable onContinueHandler) {
        this.onContinueHandler = onContinueHandler;
    }

    private void triggerContinue() {
        if (continued || onContinueHandler == null) {
            return;
        }
        continued = true;
        onContinueHandler.run();
    }
}
