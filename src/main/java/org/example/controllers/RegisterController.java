package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for the Hotelix account registration screen.
 */
public class RegisterController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField hotelNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private Hyperlink loginLink;

    private Runnable onRegisterSubmitHandler;
    private Runnable onLoginHandler;

    @FXML
    private void handleRegister() {
        if (onRegisterSubmitHandler != null) {
            onRegisterSubmitHandler.run();
        }
    }

    @FXML
    private void handleGoToLogin() {
        if (onLoginHandler != null) {
            onLoginHandler.run();
        }
    }

    public String getFullName() {
        return fullNameField.getText() == null ? "" : fullNameField.getText().trim();
    }

    public String getHotelName() {
        return hotelNameField.getText() == null ? "" : hotelNameField.getText().trim();
    }

    public String getEmail() {
        return emailField.getText() == null ? "" : emailField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText() == null ? "" : passwordField.getText();
    }

    public String getConfirmPassword() {
        return confirmPasswordField.getText() == null ? "" : confirmPasswordField.getText();
    }

    public void setOnRegisterSubmitHandler(Runnable onRegisterSubmitHandler) {
        this.onRegisterSubmitHandler = onRegisterSubmitHandler;
    }

    public void setOnLoginHandler(Runnable onLoginHandler) {
        this.onLoginHandler = onLoginHandler;
    }
}
