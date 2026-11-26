package org.example;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.controllers.LoginController;
import org.example.controllers.RegisterController;
import org.example.controllers.SplashController;
import org.example.ui.FontRegistry;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Entry point for the Hotelix desktop JavaFX application.
 */
public class Main extends Application {

    private static final double MIN_WINDOW_WIDTH = 1280;
    private static final double MIN_WINDOW_HEIGHT = 800;
    private static final Duration TRANSITION_DURATION = Duration.millis(120);

    private Stage primaryStage;
    private Scene mainScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        FontRegistry.loadFonts();

        FXMLLoader splashLoader = createLoader("/fxml/splash-view.fxml");
        Parent splashRoot = splashLoader.load();
        SplashController splashController = splashLoader.getController();

        mainScene = new Scene(splashRoot, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
        URL themeUrl = Objects.requireNonNull(
                getClass().getResource("/styles/hotelix-theme.css"),
                "Theme CSS resource not found"
        );
        mainScene.getStylesheets().add(themeUrl.toExternalForm());

        splashController.setOnContinueHandler(this::showLoginView);

        stage.setTitle("Hotelix — Gestión hotelera");
        stage.setMinWidth(MIN_WINDOW_WIDTH);
        stage.setMinHeight(MIN_WINDOW_HEIGHT);
        stage.setScene(mainScene);
        stage.show();
    }

    private void showLoginView() {
        FXMLLoader loader = switchView("/fxml/login-view.fxml", "Hotelix — Iniciar sesión");
        LoginController controller = loader.getController();
        controller.setOnRegisterHandler(this::showRegisterView);
    }

    private void showRegisterView() {
        FXMLLoader loader = switchView("/fxml/register-view.fxml", "Hotelix — Crear cuenta");
        RegisterController controller = loader.getController();
        controller.setOnLoginHandler(this::showLoginView);
    }

    private FXMLLoader switchView(String fxmlPath, String windowTitle) {
        try {
            FXMLLoader loader = createLoader(fxmlPath);
            Parent root = loader.load();
            root.setOpacity(0.0);

            mainScene.setRoot(root);
            primaryStage.setTitle(windowTitle);

            FadeTransition fadeIn = new FadeTransition(TRANSITION_DURATION, root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
            return loader;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load view: " + fxmlPath, e);
        }
    }

    private FXMLLoader createLoader(String fxmlPath) {
        URL fxmlUrl = Objects.requireNonNull(
                getClass().getResource(fxmlPath),
                "FXML resource not found: " + fxmlPath
        );
        return new FXMLLoader(fxmlUrl);
    }
}
