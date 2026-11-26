package org.example;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.controllers.ConfigureServicesController;
import org.example.controllers.HomeController;
import org.example.controllers.LoginController;
import org.example.controllers.OccupancyCalendarController;
import org.example.controllers.RegisterController;
import org.example.controllers.RoomsController;
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
        controller.setOnLoginSubmitHandler(this::showHomeView);
        controller.setOnRegisterHandler(this::showRegisterView);
    }

    private void showRegisterView() {
        FXMLLoader loader = switchView("/fxml/register-view.fxml", "Hotelix — Crear cuenta");
        RegisterController controller = loader.getController();
        controller.setOnLoginHandler(this::showLoginView);
        controller.setOnRegisterSubmitHandler(this::showConfigureServicesView);
    }

    private void showConfigureServicesView() {
        FXMLLoader loader = switchView("/fxml/configure-services-view.fxml", "Hotelix — Configurar servicios");
        ConfigureServicesController controller = loader.getController();
        controller.setOnSaveHandler(services -> showHomeView());
    }

    private void showHomeView() {
        FXMLLoader loader = switchView("/fxml/home-view.fxml", "Hotelix — Inicio");
        HomeController controller = loader.getController();
        controller.setOnNavigateRoomsHandler(this::showRoomsView);
        controller.setOnNavigateCalendarHandler(this::showOccupancyCalendarView);
    }

    private void showRoomsView() {
        FXMLLoader loader = switchView("/fxml/rooms-view.fxml", "Hotelix — Habitaciones");
        RoomsController controller = loader.getController();
        controller.setOnNavigateHomeHandler(this::showHomeView);
        controller.setOnNavigateCalendarHandler(this::showOccupancyCalendarView);
    }

    private void showOccupancyCalendarView() {
        FXMLLoader loader = switchView("/fxml/occupancy-calendar-view.fxml", "Hotelix — Calendario de ocupación");
        OccupancyCalendarController controller = loader.getController();
        controller.setOnNavigateHomeHandler(this::showHomeView);
        controller.setOnNavigateRoomsHandler(this::showRoomsView);
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
