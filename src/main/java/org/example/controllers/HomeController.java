package org.example.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller for the Hotelix home dashboard screen.
 */
public class HomeController {

    @FXML
    private Label occupancyValueLabel;

    @FXML
    private Label revenueValueLabel;

    @FXML
    private Label arrivalsValueLabel;

    @FXML
    private Label departuresValueLabel;

    @FXML
    private Button newReservationButton;

    @FXML
    private TableView<RecentReservationRow> recentReservationsTable;

    @FXML
    private TableColumn<RecentReservationRow, String> guestColumn;

    @FXML
    private TableColumn<RecentReservationRow, String> roomColumn;

    @FXML
    private TableColumn<RecentReservationRow, String> checkInColumn;

    @FXML
    private TableColumn<RecentReservationRow, String> checkOutColumn;

    @FXML
    private TableColumn<RecentReservationRow, String> statusColumn;

    private Runnable onNewReservationHandler;
    private Runnable onNavigateRoomsHandler;
    private Runnable onNavigateCalendarHandler;

    @FXML
    private void initialize() {
        configureTableColumns();
        recentReservationsTable.setItems(createSampleReservations());
    }

    private void configureTableColumns() {
        guestColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().guestName()));
        roomColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().roomNumber()));
        checkInColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().checkInDate()));
        checkOutColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().checkOutDate()));
        statusColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().status()));

        roomColumn.setCellFactory(column -> new TableCell<>() {
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

        statusColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Label badge = new Label(status);
                    boolean isPaid = "Pagada".equalsIgnoreCase(status);
                    badge.getStyleClass().add(isPaid ? "status-badge-paid" : "status-badge-pending");
                    setGraphic(badge);
                    setText(null);
                }
            }
        });
    }

    private ObservableList<RecentReservationRow> createSampleReservations() {
        return FXCollections.observableArrayList(
                new RecentReservationRow("Elena Navarro", "204", "23 nov 2026", "27 nov 2026", "Pagada"),
                new RecentReservationRow("Mateo Serrano", "108", "23 nov 2026", "25 nov 2026", "Pendiente"),
                new RecentReservationRow("Lucía Valverde", "312", "22 nov 2026", "26 nov 2026", "Pagada"),
                new RecentReservationRow("Javier Morales", "115", "23 nov 2026", "24 nov 2026", "Pendiente"),
                new RecentReservationRow("Sofía Domínguez", "209", "21 nov 2026", "25 nov 2026", "Pagada")
        );
    }

    @FXML
    private void handleNewReservation() {
        if (onNewReservationHandler != null) {
            onNewReservationHandler.run();
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

    public void setOnNewReservationHandler(Runnable onNewReservationHandler) {
        this.onNewReservationHandler = onNewReservationHandler;
    }

    public void setOnNavigateRoomsHandler(Runnable onNavigateRoomsHandler) {
        this.onNavigateRoomsHandler = onNavigateRoomsHandler;
    }

    public void setOnNavigateCalendarHandler(Runnable onNavigateCalendarHandler) {
        this.onNavigateCalendarHandler = onNavigateCalendarHandler;
    }

    public record RecentReservationRow(
            String guestName,
            String roomNumber,
            String checkInDate,
            String checkOutDate,
            String status
    ) {
    }
}
