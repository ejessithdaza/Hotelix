package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ui.FontRegistry;

import java.net.URL;
import java.util.Objects;

/**
 * Entry point for the Hotelix desktop JavaFX application.
 */
public class Main extends Application {

    private static final double MIN_WINDOW_WIDTH = 1280;
    private static final double MIN_WINDOW_HEIGHT = 800;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FontRegistry.loadFonts();

        URL fxmlUrl = Objects.requireNonNull(
                getClass().getResource("/fxml/splash-view.fxml"),
                "Splash FXML resource not found"
        );
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
        URL themeUrl = Objects.requireNonNull(
                getClass().getResource("/styles/hotelix-theme.css"),
                "Theme CSS resource not found"
        );
        scene.getStylesheets().add(themeUrl.toExternalForm());

        stage.setTitle("Hotelix — Gestión hotelera");
        stage.setMinWidth(MIN_WINDOW_WIDTH);
        stage.setMinHeight(MIN_WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}
