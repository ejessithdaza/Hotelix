package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Controller for the Hotelix occupancy calendar matrix screen.
 */
public class OccupancyCalendarController {

    private static final int DAYS_IN_WINDOW = 14;
    private static final Locale SPANISH_LOCALE = Locale.forLanguageTag("es-ES");
    private static final LocalDate INITIAL_TODAY = LocalDate.of(2026, 11, 23);

    private static final Map<String, String> STATE_STYLE_MAP = Map.of(
            ".", "cell-available",
            "O", "cell-occupied",
            "R", "cell-reserved",
            "L", "cell-arrival",
            "S", "cell-departure",
            "F", "cell-out-of-service"
    );

    private static final Map<String, String> STATE_NAME_MAP = Map.of(
            ".", "Disponible",
            "O", "Ocupada",
            "R", "Reserva futura",
            "L", "Llegada hoy",
            "S", "Salida hoy",
            "F", "Fuera de servicio"
    );

    @FXML
    private Label dateRangeLabel;

    @FXML
    private GridPane occupancyGrid;

    @FXML
    private Label detailEmptyLabel;

    @FXML
    private VBox detailContentBox;

    @FXML
    private Label detailRoomLabel;

    @FXML
    private Label detailGuestLabel;

    @FXML
    private Label detailDatesLabel;

    @FXML
    private Label detailStatusLabel;

    private LocalDate windowStartDate = INITIAL_TODAY;
    private Button selectedCellButton;
    private Runnable onNavigateHomeHandler;
    private Runnable onNavigateRoomsHandler;
    private Runnable onNewReservationHandler;

    private final Map<String, List<String>> sampleSchedule = createSampleSchedule();
    private final Map<String, List<ReservationRange>> sampleReservations = createSampleReservations();

    @FXML
    private void initialize() {
        renderCalendar();
    }

    private void renderCalendar() {
        occupancyGrid.getChildren().clear();
        selectedCellButton = null;
        resetDetailPanel();

        LocalDate windowEndDate = windowStartDate.plusDays(DAYS_IN_WINDOW - 1L);
        dateRangeLabel.setText(formatRangeTitle(windowStartDate, windowEndDate));

        Label roomHeader = new Label("Hab.");
        roomHeader.getStyleClass().add("label-md");
        roomHeader.setMinWidth(56);
        occupancyGrid.add(roomHeader, 0, 0);

        for (int dayIndex = 0; dayIndex < DAYS_IN_WINDOW; dayIndex++) {
            LocalDate date = windowStartDate.plusDays(dayIndex);
            VBox dayHeader = createDayHeader(date);
            occupancyGrid.add(dayHeader, dayIndex + 1, 0);
        }

        int currentRow = 1;
        currentRow = renderFloorGroup("Planta 1", List.of("101", "102", "103", "104"), currentRow);
        renderFloorGroup("Planta 2", List.of("201", "202", "203", "204"), currentRow);
    }

    private VBox createDayHeader(LocalDate date) {
        String dayName = capitalize(formatShortDayOfWeek(date.getDayOfWeek()));
        Label dayNameLabel = new Label(dayName);
        dayNameLabel.getStyleClass().add("label-md");

        Label dayNumberLabel = new Label(String.valueOf(date.getDayOfMonth()));
        dayNumberLabel.getStyleClass().add("label-md");

        VBox headerBox = new VBox(dayNameLabel, dayNumberLabel);
        headerBox.getStyleClass().add("calendar-day-header");
        if (date.equals(INITIAL_TODAY)) {
            headerBox.getStyleClass().add("calendar-day-today");
        }
        return headerBox;
    }

    private int renderFloorGroup(String floorTitle, List<String> roomNumbers, int startRow) {
        Label floorLabel = new Label(floorTitle);
        floorLabel.getStyleClass().addAll("label-md", "calendar-floor-label");
        occupancyGrid.add(floorLabel, 0, startRow, DAYS_IN_WINDOW + 1, 1);

        int row = startRow + 1;
        for (String room : roomNumbers) {
            Label roomLabel = new Label(room);
            roomLabel.getStyleClass().add("label-md");
            roomLabel.setMinWidth(56);
            occupancyGrid.add(roomLabel, 0, row);

            List<String> codes = sampleSchedule.getOrDefault(room, List.of());
            for (int dayIndex = 0; dayIndex < DAYS_IN_WINDOW; dayIndex++) {
                LocalDate date = windowStartDate.plusDays(dayIndex);
                String code = windowStartDate.equals(INITIAL_TODAY) && dayIndex < codes.size()
                        ? codes.get(dayIndex)
                        : ".";
                Button cellButton = createCellButton(room, dayIndex, date, code);
                occupancyGrid.add(cellButton, dayIndex + 1, row);
            }
            row++;
        }
        return row;
    }

    private Button createCellButton(String room, int dayIndex, LocalDate date, String code) {
        String text = ".".equals(code) ? "" : code;
        Button button = new Button(text);
        button.getStyleClass().addAll("calendar-cell", STATE_STYLE_MAP.getOrDefault(code, "cell-available"));
        button.setOnAction(event -> handleCellSelected(button, room, dayIndex, date, code));
        return button;
    }

    private void handleCellSelected(Button button, String room, int dayIndex, LocalDate date, String code) {
        if (selectedCellButton != null) {
            selectedCellButton.getStyleClass().remove("calendar-cell-selected");
        }
        selectedCellButton = button;
        if (!button.getStyleClass().contains("calendar-cell-selected")) {
            button.getStyleClass().add("calendar-cell-selected");
        }

        ReservationRange reservation = windowStartDate.equals(INITIAL_TODAY)
                ? findReservation(room, dayIndex)
                : null;

        detailEmptyLabel.setVisible(false);
        detailEmptyLabel.setManaged(false);
        detailContentBox.setVisible(true);
        detailContentBox.setManaged(true);

        detailRoomLabel.setText("Habitación " + room);
        detailGuestLabel.setText(reservation != null ? reservation.guest() : "Sin reserva asignada");
        detailDatesLabel.setText(reservation != null ? reservation.dates() : formatSingleDate(date));
        detailStatusLabel.setText(STATE_NAME_MAP.getOrDefault(code, "Disponible"));
    }

    private void resetDetailPanel() {
        detailEmptyLabel.setVisible(true);
        detailEmptyLabel.setManaged(true);
        detailContentBox.setVisible(false);
        detailContentBox.setManaged(false);
    }

    private ReservationRange findReservation(String room, int dayIndex) {
        List<ReservationRange> ranges = sampleReservations.getOrDefault(room, List.of());
        for (ReservationRange range : ranges) {
            if (dayIndex >= range.startDay() && dayIndex <= range.endDay()) {
                return range;
            }
        }
        return null;
    }

    @FXML
    private void handlePreviousPeriod() {
        windowStartDate = windowStartDate.minusDays(DAYS_IN_WINDOW);
        renderCalendar();
    }

    @FXML
    private void handleNextPeriod() {
        windowStartDate = windowStartDate.plusDays(DAYS_IN_WINDOW);
        renderCalendar();
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

    public void setOnNewReservationHandler(Runnable onNewReservationHandler) {
        this.onNewReservationHandler = onNewReservationHandler;
    }

    private String formatShortDayOfWeek(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> "Lun";
            case TUESDAY -> "Mar";
            case WEDNESDAY -> "Mié";
            case THURSDAY -> "Jue";
            case FRIDAY -> "Vie";
            case SATURDAY -> "Sáb";
            case SUNDAY -> "Dom";
        };
    }

    private String formatRangeTitle(LocalDate start, LocalDate end) {
        String startMonth = cleanMonth(start);
        String endMonth = cleanMonth(end);
        if (start.getYear() == end.getYear() && start.getMonth() == end.getMonth()) {
            return start.getDayOfMonth() + " – " + end.getDayOfMonth() + " " + endMonth + " " + end.getYear();
        }
        return start.getDayOfMonth() + " " + startMonth + " – " + end.getDayOfMonth() + " " + endMonth + " " + end.getYear();
    }

    private String formatSingleDate(LocalDate date) {
        return date.getDayOfMonth() + " " + cleanMonth(date) + " " + date.getYear();
    }

    private String cleanMonth(LocalDate date) {
        return date.getMonth().getDisplayName(TextStyle.SHORT, SPANISH_LOCALE).replace(".", "");
    }

    private String capitalize(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    private Map<String, List<String>> createSampleSchedule() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        map.put("101", List.of("O", "O", "O", ".", ".", "R", "R", "R", ".", ".", ".", "R", "R", "R"));
        map.put("102", List.of("L", "O", "O", "O", ".", ".", ".", ".", "R", "R", "R", ".", ".", "."));
        map.put("103", List.of("S", ".", ".", "R", "R", "R", "R", ".", ".", ".", ".", ".", ".", "."));
        map.put("104", List.of("F", "F", "F", ".", ".", ".", ".", "R", "R", "R", "R", ".", ".", "."));
        map.put("201", List.of("O", "O", ".", ".", ".", "R", "R", "R", "R", ".", ".", ".", ".", "."));
        map.put("202", List.of(".", ".", "R", "R", "R", "R", ".", ".", ".", ".", "R", "R", "R", "."));
        map.put("203", List.of("L", "O", "O", "O", "O", ".", ".", ".", ".", "R", "R", "R", ".", "."));
        map.put("204", List.of("O", "O", "O", "O", "O", ".", ".", ".", "R", "R", "R", "R", ".", "."));
        return map;
    }

    private Map<String, List<ReservationRange>> createSampleReservations() {
        Map<String, List<ReservationRange>> map = new LinkedHashMap<>();
        map.put("101", List.of(
                new ReservationRange(0, 2, "Carmen Velasco", "21 nov – 26 nov 2026"),
                new ReservationRange(5, 7, "Andrés Montoya", "28 nov – 1 dic 2026"),
                new ReservationRange(11, 13, "Beatriz Soler", "4 dic – 7 dic 2026")
        ));
        map.put("102", List.of(
                new ReservationRange(0, 3, "Mateo Serrano", "23 nov – 27 nov 2026"),
                new ReservationRange(8, 10, "Isabel Paredes", "1 dic – 4 dic 2026")
        ));
        map.put("103", List.of(
                new ReservationRange(0, 0, "Diego Santamaría", "20 nov – 23 nov 2026"),
                new ReservationRange(3, 6, "Patricia Luna", "26 nov – 30 nov 2026")
        ));
        map.put("104", List.of(
                new ReservationRange(0, 2, "Sin huésped (Revisión eléctrica)", "23 nov – 25 nov 2026"),
                new ReservationRange(7, 10, "Fernando Ibarra", "30 nov – 4 dic 2026")
        ));
        map.put("201", List.of(
                new ReservationRange(0, 1, "Lucía Valverde", "22 nov – 25 nov 2026"),
                new ReservationRange(5, 8, "Hugo Mendoza", "28 nov – 2 dic 2026")
        ));
        map.put("202", List.of(
                new ReservationRange(2, 5, "Valeria Cortés", "25 nov – 29 nov 2026"),
                new ReservationRange(10, 12, "Raúl Benítez", "3 dic – 6 dic 2026")
        ));
        map.put("203", List.of(
                new ReservationRange(0, 4, "Javier Morales", "23 nov – 28 nov 2026"),
                new ReservationRange(9, 11, "Nuria Campos", "2 dic – 5 dic 2026")
        ));
        map.put("204", List.of(
                new ReservationRange(0, 4, "Elena Navarro", "23 nov – 28 nov 2026"),
                new ReservationRange(8, 11, "Tomás Quiroga", "1 dic – 5 dic 2026")
        ));
        return map;
    }

    private record ReservationRange(int startDay, int endDay, String guest, String dates) {
    }
}
