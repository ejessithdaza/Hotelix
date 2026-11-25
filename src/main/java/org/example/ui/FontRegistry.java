package org.example.ui;

import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.List;

/**
 * Loads application custom fonts into the JavaFX font registry.
 */
public final class FontRegistry {

    private static final List<String> FONT_RESOURCES = List.of(
            "/fonts/ElsieSwashCaps-Regular.ttf",
            "/fonts/ArimaMadurai-Bold.ttf",
            "/fonts/Maitree-Regular.ttf"
    );

    private static boolean loaded = false;

    private FontRegistry() {
    }

    /**
     * Loads all bundled fonts once so CSS rules can reference their family names.
     */
    public static void loadFonts() {
        if (loaded) {
            return;
        }
        for (String resourcePath : FONT_RESOURCES) {
            try (InputStream stream = FontRegistry.class.getResourceAsStream(resourcePath)) {
                if (stream == null) {
                    System.err.println("Font resource not found: " + resourcePath);
                    continue;
                }
                Font font = Font.loadFont(stream, 16);
                if (font == null) {
                    System.err.println("Failed to load font: " + resourcePath);
                }
            } catch (Exception e) {
                System.err.println("Error loading font " + resourcePath + ": " + e.getMessage());
            }
        }
        loaded = true;
    }
}
