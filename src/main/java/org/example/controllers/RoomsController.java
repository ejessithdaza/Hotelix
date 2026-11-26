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

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Controller for the Hotelix rooms catalog and filtering screen.
 */
public class RoomsController {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> typeFilterCombo;

    @FXML
    private ComboBox<String> statusFilterCombo;

    @FXML
    private Label summaryLabel;

    @FXML
    private Label showingLabel;

    @FXML
    private Button newRoomButton;

    @FXML
    private TableView<RoomRow> roomsTable;

    @FXML
    private TableColumn<RoomRow, String> numberColumn;

    @FXML
    private TableColumn<RoomRow, String> typeColumn;

    @FXML
    private TableColumn<RoomRow, String> floorColumn;

    @FXML
    private TableColumn<RoomRow, String> capacityColumn;

    @FXML
    private TableColumn<RoomRow, String> rateColumn;

    @FXML
    private TableColumn<RoomRow, String> statusColumn;

    @FXML
    private TableColumn<RoomRow, String> actionsColumn;

    private final ObservableList<RoomRow> allRooms = createSampleRooms();
    private FilteredList<RoomRow> filteredRooms;

    private Runnable onNavigateHomeHandler;
    private Runnable onNavigateReservationsHandler;
    private Runnable onNavigateCalendarHandler;
    private Runnable onNavigateGuestsHandler;
    private Runnable onNewRoomHandler;
    private Consumer<RoomRow> onEditRoomHandler;

    @FXML
    private void initialize() {
        configureFilters();
        configureTableColumns();

        filteredRooms = new FilteredList<>(allRooms, room -> true);
        roomsTable.setItems(filteredRooms);

        updateSummary();
        updateShowingCount();
    }

    private void configureFilters() {
        typeFilterCombo.setItems(FXCollections.observableArrayList("Todas", "Individual", "Doble", "Suite"));
        typeFilterCombo.setValue("Todas");

        statusFilterCombo.setItems(FXCollections.observableArrayList(
                "Todos", "Disponible", "Ocupada", "Mantenimiento", "Fuera de servicio"
        ));
        statusFilterCombo.setValue("Todos");

        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        typeFilterCombo.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        statusFilterCombo.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
    }

    private void configureTableColumns() {
        configureRightAlignedColumn(numberColumn, RoomRow::number);
        typeColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().type()));
        configureRightAlignedColumn(floorColumn, RoomRow::floor);
        configureRightAlignedColumn(capacityColumn, RoomRow::capacity);
        configureRightAlignedColumn(rateColumn, RoomRow::nightlyRate);
        statusColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().status()));
        actionsColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().number()));

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
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Hyperlink editLink = new Hyperlink("Editar");
                    editLink.getStyleClass().add("link-burgundy");
                    editLink.setOnAction(event -> {
                        RoomRow row = getTableView().getItems().get(getIndex());
                        if (onEditRoomHandler != null) {
                            onEditRoomHandler.accept(row);
                        }
                    });
                    setAlignment(Pos.CENTER_RIGHT);
                    setGraphic(editLink);
                    setText(null);
                }
            }
        });
    }

    private void configureRightAlignedColumn(TableColumn<RoomRow, String> column, Function<RoomRow, String> extractor) {
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
            case "Ocupada" -> "status-badge-paid";
            case "Mantenimiento" -> "status-badge-maintenance";
            case "Fuera de servicio" -> "status-badge-out-of-service";
            default -> "status-badge-pending";
        };
    }

    private void applyFilters() {
        String query = searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
        String selectedType = typeFilterCombo.getValue();
        String selectedStatus = statusFilterCombo.getValue();

        filteredRooms.setPredicate(room -> {
            boolean matchesQuery = query.isEmpty() || room.number().toLowerCase().contains(query);
            boolean matchesType = "Todas".equals(selectedType) || room.type().equals(selectedType);
            boolean matchesStatus = "Todos".equals(selectedStatus) || room.status().equals(selectedStatus);
            return matchesQuery && matchesType && matchesStatus;
        });

        updateShowingCount();
    }

    private void updateSummary() {
        int available = 0;
        int occupied = 0;
        int maintenance = 0;
        int outOfService = 0;

        for (RoomRow room : allRooms) {
            switch (room.status()) {
                case "Disponible" -> available++;
                case "Ocupada" -> occupied++;
                case "Mantenimiento" -> maintenance++;
                case "Fuera de servicio" -> outOfService++;
                default -> { }
            }
        }

        summaryLabel.setText(String.format(
                "%d habitaciones · %d disponibles · %d ocupadas · %d en mantenimiento · %d fuera de servicio",
                allRooms.size(), available, occupied, maintenance, outOfService
        ));
    }

    private void updateShowingCount() {
        showingLabel.setText(String.format("Mostrando %d de %d habitaciones", filteredRooms.size(), allRooms.size()));
    }

    private ObservableList<RoomRow> createSampleRooms() {
        return FXCollections.observableArrayList(
                new RoomRow("101", "Individual", "1", "1", "95 €", "Disponible"),
                new RoomRow("102", "Doble", "1", "2", "140 €", "Ocupada"),
                new RoomRow("103", "Doble", "1", "2", "140 €", "Disponible"),
                new RoomRow("104", "Suite", "1", "4", "280 €", "Disponible"),
                new RoomRow("201", "Doble", "2", "2", "150 €", "Mantenimiento"),
                new RoomRow("202", "Suite", "2", "4", "280 €", "Fuera de servicio"),
                new RoomRow("203", "Individual", "2", "1", "95 €", "Disponible"),
                new RoomRow("204", "Doble", "2", "2", "150 €", "Ocupada")
        );
    }

    @FXML
    private void handleNavHome() {
        if (onNavigateHomeHandler != null) {
            onNavigateHomeHandler.run();
        }
    }

    @FXML
    private void handleNavReservations() {
        if (onNavigateReservationsHandler != null) {
            onNavigateReservationsHandler.run();
        }
    }

    @FXML
    private void handleNavCalendar() {
        if (onNavigateCalendarHandler != null) {
            onNavigateCalendarHandler.run();
        }
    }

    @FXML
    private void handleNavGuests() {
        if (onNavigateGuestsHandler != null) {
            onNavigateGuestsHandler.run();
        }
    }

    @FXML
    private void handleNewRoom() {
        if (onNewRoomHandler != null) {
            onNewRoomHandler.run();
        }
    }

    public void setOnNavigateHomeHandler(Runnable onNavigateHomeHandler) {
        this.onNavigateHomeHandler = onNavigateHomeHandler;
    }

    public void setOnNavigateReservationsHandler(Runnable onNavigateReservationsHandler) {
        this.onNavigateReservationsHandler = onNavigateReservationsHandler;
    }

    public void setOnNavigateCalendarHandler(Runnable onNavigateCalendarHandler) {
        this.onNavigateCalendarHandler = onNavigateCalendarHandler;
    }

    public void setOnNavigateGuestsHandler(Runnable handler) {
        this.onNavigateGuestsHandler = handler;
    }

    public void setOnNewRoomHandler(Runnable onNewRoomHandler) {
        this.onNewRoomHandler = onNewRoomHandler;
    }

    public void setOnEditRoomHandler(Consumer<RoomRow> onEditRoomHandler) {
        this.onEditRoomHandler = onEditRoomHandler;
    }

    public record RoomRow(
            String number,
            String type,
            String floor,
            String capacity,
            String nightlyRate,
            String status
    ) {
    }
}
