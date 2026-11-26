package org.example.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

/**
 * Controller for the Hotelix Huéspedes (Guests) screen.
 * Adapts hotelix_hu_spedes/code.html to JavaFX.
 *
 * Layout:
 *  - Left: table with search + preference filter.
 *  - Right: detail panel that updates when a row is selected.
 */
public class GuestsController {

    // ── Navigation buttons ──────────────────────────────────────────
    @FXML private Button navHomeButton;
    @FXML private Button navRoomsButton;
    @FXML private Button navReservationsButton;
    @FXML private Button navCalendarButton;

    // ── Filters ─────────────────────────────────────────────────────
    @FXML private TextField        searchField;
    @FXML private ComboBox<String> preferenceFilterCombo;

    // ── Summary / showing ───────────────────────────────────────────
    @FXML private Label summaryLabel;
    @FXML private Label showingLabel;

    // ── Table ───────────────────────────────────────────────────────
    @FXML private TableView<GuestRow>            guestsTable;
    @FXML private TableColumn<GuestRow, String>  nameColumn;
    @FXML private TableColumn<GuestRow, String>  emailColumn;
    @FXML private TableColumn<GuestRow, String>  phoneColumn;
    @FXML private TableColumn<GuestRow, String>  staysColumn;
    @FXML private TableColumn<GuestRow, String>  lastVisitColumn;
    @FXML private TableColumn<GuestRow, String>  prefsColumn;

    // ── Detail panel ────────────────────────────────────────────────
    @FXML private VBox    detailPanel;
    @FXML private Label   detailName;
    @FXML private Label   detailEmail;
    @FXML private Label   detailPhone;
    @FXML private CheckBox prefElevatorCheck;
    @FXML private CheckBox prefBreakfastCheck;
    @FXML private CheckBox prefBedCheck;
    @FXML private CheckBox prefAccessCheck;
    @FXML private VBox    historyBox;

    // ── Data ────────────────────────────────────────────────────────
    private final ObservableList<GuestRow> allGuests = buildSampleGuests();
    private FilteredList<GuestRow> filteredGuests;

    // ── Navigation callbacks ─────────────────────────────────────────
    private Runnable onNavigateHomeHandler;
    private Runnable onNavigateRoomsHandler;
    private Runnable onNavigateReservationsHandler;
    private Runnable onNavigateCalendarHandler;

    public void setOnNavigateHomeHandler(Runnable h)         { this.onNavigateHomeHandler = h; }
    public void setOnNavigateRoomsHandler(Runnable h)        { this.onNavigateRoomsHandler = h; }
    public void setOnNavigateReservationsHandler(Runnable h) { this.onNavigateReservationsHandler = h; }
    public void setOnNavigateCalendarHandler(Runnable h)     { this.onNavigateCalendarHandler = h; }

    // ── Initialisation ───────────────────────────────────────────────
    @FXML
    private void initialize() {
        configureFilters();
        configureTable();

        filteredGuests = new FilteredList<>(allGuests, r -> true);
        guestsTable.setItems(filteredGuests);

        // Select Sofía Herrera by default (last row, as in the HTML)
        guestsTable.getSelectionModel().selectLast();
        updateDetailPanel(guestsTable.getSelectionModel().getSelectedItem());

        updateSummary();
    }

    // ── Filter setup ─────────────────────────────────────────────────
    private void configureFilters() {
        preferenceFilterCombo.setItems(FXCollections.observableArrayList(
                "Todas", "Lejos del ascensor", "Desayuno incluido",
                "Cama específica", "Accesibilidad"
        ));
        preferenceFilterCombo.setValue("Todas");

        searchField.textProperty().addListener((obs, o, n) -> applyFilters());
        preferenceFilterCombo.valueProperty().addListener((obs, o, n) -> applyFilters());
    }

    private void applyFilters() {
        String query = searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
        String pref  = preferenceFilterCombo.getValue();

        filteredGuests.setPredicate(row -> {
            boolean matchesQuery = query.isEmpty()
                    || row.name().toLowerCase().contains(query)
                    || row.email().toLowerCase().contains(query);
            boolean matchesPref = "Todas".equals(pref)
                    || row.prefs().contains(pref);
            return matchesQuery && matchesPref;
        });

        int visible = filteredGuests.size();
        int total   = allGuests.size();
        showingLabel.setText("Mostrando " + visible + " de " + total + " huéspedes");
    }

    // ── Table setup ───────────────────────────────────────────────────
    private void configureTable() {
        nameColumn.setCellValueFactory(c      -> new SimpleStringProperty(c.getValue().name()));
        emailColumn.setCellValueFactory(c     -> new SimpleStringProperty(c.getValue().email()));
        phoneColumn.setCellValueFactory(c     -> new SimpleStringProperty(c.getValue().phone()));
        staysColumn.setCellValueFactory(c     -> new SimpleStringProperty(String.valueOf(c.getValue().stays())));
        lastVisitColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().lastVisit()));
        prefsColumn.setCellValueFactory(c     -> new SimpleStringProperty(String.valueOf(c.getValue().prefs().size())));

        // Right-align numeric columns
        staysColumn.setCellFactory(col -> rightAlignedCell());
        prefsColumn.setCellFactory(col -> rightAlignedCell());

        // Row selection → update detail panel
        guestsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, old, selected) -> { if (selected != null) updateDetailPanel(selected); }
        );
    }

    private TableCell<GuestRow, String> rightAlignedCell() {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); } else {
                    setText(item);
                    setStyle("-fx-alignment: CENTER_RIGHT;");
                }
            }
        };
    }

    // ── Detail panel update ───────────────────────────────────────────
    private void updateDetailPanel(GuestRow guest) {
        if (guest == null) return;

        detailName.setText(guest.name());
        detailEmail.setText(guest.email());
        detailPhone.setText(guest.phone());

        prefElevatorCheck.setSelected(guest.prefs().contains("Lejos del ascensor"));
        prefBreakfastCheck.setSelected(guest.prefs().contains("Desayuno incluido"));
        prefBedCheck.setSelected(guest.prefs().contains("Cama específica"));
        prefAccessCheck.setSelected(guest.prefs().contains("Accesibilidad"));

        historyBox.getChildren().clear();
        for (String entry : guest.history()) {
            Label lbl = new Label(entry);
            lbl.getStyleClass().add("body-md");
            lbl.setWrapText(true);
            historyBox.getChildren().add(lbl);
        }
    }

    // ── Summary ───────────────────────────────────────────────────────
    private void updateSummary() {
        long accessCount = allGuests.stream()
                .filter(g -> g.prefs().contains("Accesibilidad"))
                .count();
        summaryLabel.setText(allGuests.size() + " huéspedes · "
                + accessCount + " con preferencias de accesibilidad");
    }

    // ── Action handlers ───────────────────────────────────────────────
    @FXML private void handleNavHome()         { if (onNavigateHomeHandler         != null) onNavigateHomeHandler.run(); }
    @FXML private void handleNavRooms()        { if (onNavigateRoomsHandler        != null) onNavigateRoomsHandler.run(); }
    @FXML private void handleNavReservations() { if (onNavigateReservationsHandler != null) onNavigateReservationsHandler.run(); }
    @FXML private void handleNavCalendar()     { if (onNavigateCalendarHandler     != null) onNavigateCalendarHandler.run(); }

    @FXML
    private void handleNewGuest() {
        System.out.println("Nuevo huésped — pendiente de implementar formulario");
    }

    @FXML
    private void handleEditGuest() {
        GuestRow selected = guestsTable.getSelectionModel().getSelectedItem();
        if (selected != null) System.out.println("Editar: " + selected.name());
    }

    @FXML
    private void handleSaveGuest() {
        GuestRow selected = guestsTable.getSelectionModel().getSelectedItem();
        if (selected != null) System.out.println("Guardar cambios de: " + selected.name());
    }

    // ── Sample data ───────────────────────────────────────────────────
    private static ObservableList<GuestRow> buildSampleGuests() {
        return FXCollections.observableArrayList(
                new GuestRow("Laura Méndez",   "laura.mendez@correo.es",   "+34 612 000 111", 4, "26 nov 2026",
                        List.of("Lejos del ascensor", "Desayuno incluido"),
                        List.of("Estancia 12 de octubre · habitación 101", "Estancia 3 de julio · habitación 103")),
                new GuestRow("Marta Solís",    "marta.solis@correo.es",    "+34 612 000 222", 1, "25 nov 2026",
                        List.of("Accesibilidad"),
                        List.of("Estancia 20 de noviembre · habitación 102")),
                new GuestRow("Javier Ortega",  "javier.ortega@correo.es",  "+34 612 000 333", 6, "27 nov 2026",
                        List.of("Lejos del ascensor", "Cama específica", "Accesibilidad"),
                        List.of("Estancia 21 de noviembre · habitación 104", "Estancia 4 de septiembre · habitación 202")),
                new GuestRow("Carmen Vidal",   "carmen.vidal@correo.es",   "+34 612 000 444", 2, "30 nov 2026",
                        List.of(),
                        List.of("Estancia 15 de octubre · habitación 203")),
                new GuestRow("Pablo Rey",      "pablo.rey@correo.es",      "+34 612 000 555", 3, "22 nov 2026",
                        List.of("Desayuno incluido"),
                        List.of("Estancia 19 de noviembre · habitación 204")),
                new GuestRow("Elena Navarro",  "elena.navarro@correo.es",  "+34 612 000 666", 1, "24 nov 2026",
                        List.of(),
                        List.of("Estancia 10 de julio · habitación 103")),
                new GuestRow("Sofía Herrera",  "sofia.herrera@correo.es",  "+34 612 000 777", 5, "23 nov 2026",
                        List.of("Desayuno incluido", "Accesibilidad"),
                        List.of("Estancia 1 de noviembre · habitación 102", "Estancia 14 de agosto · habitación 204"))
        );
    }

    // ── Inner record ──────────────────────────────────────────────────
    public record GuestRow(
            String name,
            String email,
            String phone,
            int stays,
            String lastVisit,
            List<String> prefs,
            List<String> history
    ) {}
}
