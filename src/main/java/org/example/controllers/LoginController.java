package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for the Hotelix login screen.
 */
public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberCheckBox;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;

    private Runnable onLoginSubmitHandler;
    private Runnable onRegisterHandler;

    @FXML
    private void handleLogin() {
        if (onLoginSubmitHandler != null) {
            onLoginSubmitHandler.run();
        }
    }

    @FXML
    private void handleRegister() {
        if (onRegisterHandler != null) {
            onRegisterHandler.run();
        }
    }

    public String getEmail() {
        return emailField.getText() == null ? "" : emailField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText() == null ? "" : passwordField.getText();
    }

    public boolean isRememberSelected() {
        return rememberCheckBox.isSelected();
    }

    public void setOnLoginSubmitHandler(Runnable onLoginSubmitHandler) {
        this.onLoginSubmitHandler = onLoginSubmitHandler;
    }

    public void setOnRegisterHandler(Runnable onRegisterHandler) {
        this.onRegisterHandler = onRegisterHandler;
    }
}
