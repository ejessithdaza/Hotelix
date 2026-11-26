package org.example.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.function.Function;

/**
 * Controller for the Hotelix reservations management screen.
 */
public class ReservationsController {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> statusFilterCombo;

    @FXML
    private TextField fromDateField;

    @FXML
    private TextField toDateField;

    @FXML
    private Label summaryLabel;

    @FXML
    private Label showingLabel;

    @FXML
    private Button newReservationButton;

    @FXML
    private TableView<ReservationRow> reservationsTable;

    @FXML
    private TableColumn<ReservationRow, String> guestColumn;

    @FXML
    private TableColumn<ReservationRow, String> roomColumn;

    @FXML
    private TableColumn<ReservationRow, String> checkInColumn;

    @FXML
    private TableColumn<ReservationRow, String> checkOutColumn;

    @FXML
    private TableColumn<ReservationRow, String> nightsColumn;

    @FXML
    private TableColumn<ReservationRow, String> statusColumn;

    @FXML
    private TableColumn<ReservationRow, String> amountColumn;

    @FXML
    private TableColumn<ReservationRow, String> actionsColumn;

    private final ObservableList<ReservationRow> allReservations = createSampleReservations();
    private FilteredList<ReservationRow> filteredReservations;

    private Runnable onNavigateHomeHandler;
    private Runnable onNavigateRoomsHandler;
    private Runnable onNavigateCalendarHandler;
    private Runnable onNewReservationHandler;

    @FXML
    private void initialize() {
        configureFilters();
        configureTableColumns();

        filteredReservations = new FilteredList<>(allReservations, r -> true);
        reservationsTable.setItems(filteredReservations);

        updateSummary();
        updateShowingCount();
    }

    private void configureFilters() {
        statusFilterCombo.setItems(FXCollections.observableArrayList(
                "Todas", "Confirmada", "Activa", "Finalizada", "Cancelada"
        ));
        statusFilterCombo.setValue("Todas");

        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        statusFilterCombo.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
    }

    private void configureTableColumns() {
        guestColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().guest()));
        configureRightAlignedColumn(roomColumn, ReservationRow::room);
        configureRightAlignedColumn(checkInColumn, ReservationRow::checkIn);
        configureRightAlignedColumn(checkOutColumn, ReservationRow::checkOut);
        configureRightAlignedColumn(nightsColumn, ReservationRow::nights);
        statusColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().status()));
        configureRightAlignedColumn(amountColumn, ReservationRow::amount);
        actionsColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().status()));

        statusColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Label badge = new Label(status);
                    badge.getStyleClass().add(resolveStatusBadgeClass(status));
                    setGraphic(badge);
                    setText(null);
                }
            }
        });

        actionsColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    HBox box = new HBox(16);
                    box.setAlignment(Pos.CENTER_RIGHT);

                    switch (status) {
                        case "Confirmada" -> {
                            Hyperlink checkInLink = createActionLink("Check-in");
                            Hyperlink cancelLink = createActionLink("Cancelar");
                            box.getChildren().addAll(checkInLink, cancelLink);
                        }
                        case "Activa" -> {
                            Hyperlink addChargeLink = createActionLink("Añadir cargo");
                            Hyperlink checkOutLink = createActionLink("Check-out");
                            box.getChildren().addAll(addChargeLink, checkOutLink);
                        }
                        default -> {
                            Hyperlink viewDetailLink = createActionLink("Ver detalle");
                            box.getChildren().add(viewDetailLink);
                        }
                    }

                    setAlignment(Pos.CENTER_RIGHT);
                    setGraphic(box);
                    setText(null);
                }
            }
        });
    }

    private Hyperlink createActionLink(String text) {
        Hyperlink link = new Hyperlink(text);
        link.getStyleClass().add("link-burgundy");
        return link;
    }

    private void configureRightAlignedColumn(TableColumn<ReservationRow, String> column, Function<ReservationRow, String> extractor) {
        column.setCellValueFactory(cell -> new SimpleStringProperty(extractor.apply(cell.getValue())));
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                getStyleClass().remove("cell-right");
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    getStyleClass().add("cell-right");
                }
            }
        });
    }

    private String resolveStatusBadgeClass(String status) {
        return switch (status) {
            case "Activa" -> "status-badge-paid";
            case "Finalizada" -> "status-badge-maintenance";
            case "Cancelada" -> "status-badge-cancelled";
            default -> "status-badge-pending"; // Confirmada
        };
    }

    private void applyFilters() {
        String query = searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
        String selectedStatus = statusFilterCombo.getValue();

        filteredReservations.setPredicate(r -> {
            boolean matchesQuery = query.isEmpty() ||
                    r.guest().toLowerCase().contains(query) ||
                    r.room().toLowerCase().contains(query);
            boolean matchesStatus = "Todas".equals(selectedStatus) || r.status().equals(selectedStatus);
            return matchesQuery && matchesStatus;
        });

        updateShowingCount();
    }

    private void updateSummary() {
        int confirmed = 0;
        int active = 0;
        int finished = 0;
        int cancelled = 0;

        for (ReservationRow r : allReservations) {
            switch (r.status()) {
                case "Confirmada" -> confirmed++;
                case "Activa" -> active++;
                case "Finalizada" -> finished++;
                case "Cancelada" -> cancelled++;
                default -> { }
            }
        }

        summaryLabel.setText(String.format(
                "%s · %s · %s · %s · %s",
                formatCount(allReservations.size(), "reserva", "reservas"),
                formatCount(confirmed, "confirmada", "confirmadas"),
                formatCount(active, "activa", "activas"),
                formatCount(finished, "finalizada", "finalizadas"),
                formatCount(cancelled, "cancelada", "canceladas")
        ));
    }

    private String formatCount(int count, String singular, String plural) {
        return count + " " + (count == 1 ? singular : plural);
    }

    private void updateShowingCount() {
        showingLabel.setText(String.format("Mostrando %d de %d reservas", filteredReservations.size(), allReservations.size()));
    }

    private ObservableList<ReservationRow> createSampleReservations() {
        return FXCollections.observableArrayList(
                new ReservationRow("Laura Méndez", "101", "23 nov", "26 nov", "3", "Confirmada", "285 €"),
                new ReservationRow("Marta Solís", "102", "20 nov", "25 nov", "5", "Activa", "700 €"),
                new ReservationRow("Javier Ortega", "104", "21 nov", "27 nov", "6", "Activa", "1.680 €"),
                new ReservationRow("Carmen Vidal", "203", "28 nov", "30 nov", "2", "Confirmada", "190 €"),
                new ReservationRow("Pablo Rey", "204", "19 nov", "22 nov", "3", "Finalizada", "450 €"),
                new ReservationRow("Elena Navarro", "103", "24 nov", "26 nov", "2", "Cancelada", "0 €"),
                new ReservationRow("Diego Ferrer", "202", "1 dic", "4 dic", "3", "Confirmada", "840 €")
        );
    }

    @FXML
    private void handleNavHome() {
        if (onNavigateHomeHandler != null) {
            onNavigateHomeHandler.run();
        }
    }

    @FXML
    private void handleNavRooms() {
        if (onNavigateRoomsHandler != null) {
            onNavigateRoomsHandler.run();
        }
    }

    @FXML
    private void handleNavCalendar() {
        if (onNavigateCalendarHandler != null) {
            onNavigateCalendarHandler.run();
        }
    }

    @FXML
    private void handleNewReservation() {
        if (onNewReservationHandler != null) {
            onNewReservationHandler.run();
        }
    }

    public void setOnNavigateHomeHandler(Runnable onNavigateHomeHandler) {
        this.onNavigateHomeHandler = onNavigateHomeHandler;
    }

    public void setOnNavigateRoomsHandler(Runnable onNavigateRoomsHandler) {
        this.onNavigateRoomsHandler = onNavigateRoomsHandler;
    }

    public void setOnNavigateCalendarHandler(Runnable onNavigateCalendarHandler) {
        this.onNavigateCalendarHandler = onNavigateCalendarHandler;
    }

    public void setOnNewReservationHandler(Runnable onNewReservationHandler) {
        this.onNewReservationHandler = onNewReservationHandler;
    }

    public record ReservationRow(
            String guest,
            String room,
            String checkIn,
            String checkOut,
            String nights,
            String status,
            String amount
    ) {
    }
}
