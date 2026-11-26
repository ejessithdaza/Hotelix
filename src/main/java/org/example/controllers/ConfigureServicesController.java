package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Controller for the initial hotel services configuration screen.
 */
public class ConfigureServicesController {

    private static final String SELECTED_CARD_CLASS = "service-card-selected";

    @FXML
    private GridPane servicesGrid;

    @FXML
    private HBox breakfastCard;
    @FXML
    private CheckBox breakfastCheckBox;

    @FXML
    private HBox roomServiceCard;
    @FXML
    private CheckBox roomServiceCheckBox;

    @FXML
    private HBox laundryCard;
    @FXML
    private CheckBox laundryCheckBox;

    @FXML
    private HBox parkingCard;
    @FXML
    private CheckBox parkingCheckBox;

    @FXML
    private HBox gymCard;
    @FXML
    private CheckBox gymCheckBox;

    @FXML
    private HBox spaCard;
    @FXML
    private CheckBox spaCheckBox;

    @FXML
    private HBox airportTransferCard;
    @FXML
    private CheckBox airportTransferCheckBox;

    @FXML
    private HBox premiumWifiCard;
    @FXML
    private CheckBox premiumWifiCheckBox;

    @FXML
    private Label servicesCountLabel;

    @FXML
    private Button saveButton;

    private final List<ServiceCardItem> serviceItems = new ArrayList<>();
    private Consumer<List<String>> onSaveHandler;

    @FXML
    private void initialize() {
        registerCard(breakfastCard, breakfastCheckBox);
        registerCard(roomServiceCard, roomServiceCheckBox);
        registerCard(laundryCard, laundryCheckBox);
        registerCard(parkingCard, parkingCheckBox);
        registerCard(gymCard, gymCheckBox);
        registerCard(spaCard, spaCheckBox);
        registerCard(airportTransferCard, airportTransferCheckBox);
        registerCard(premiumWifiCard, premiumWifiCheckBox);

        updateSelectionState();
    }

    private void registerCard(HBox card, CheckBox checkBox) {
        serviceItems.add(new ServiceCardItem(card, checkBox));

        checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> updateSelectionState());
        card.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getTarget() != checkBox && !isDescendantOf(event.getTarget(), checkBox)) {
                checkBox.setSelected(!checkBox.isSelected());
            }
        });
    }

    private boolean isDescendantOf(Object target, CheckBox checkBox) {
        if (!(target instanceof javafx.scene.Node node)) {
            return false;
        }
        javafx.scene.Node current = node;
        while (current != null) {
            if (current == checkBox) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    private void updateSelectionState() {
        int selectedCount = 0;
        for (ServiceCardItem item : serviceItems) {
            boolean selected = item.checkBox().isSelected();
            if (selected) {
                selectedCount++;
                if (!item.card().getStyleClass().contains(SELECTED_CARD_CLASS)) {
                    item.card().getStyleClass().add(SELECTED_CARD_CLASS);
                }
            } else {
                item.card().getStyleClass().remove(SELECTED_CARD_CLASS);
            }
        }

        servicesCountLabel.setText(selectedCount == 1
                ? "1 servicio seleccionado"
                : selectedCount + " servicios seleccionados");
    }

    @FXML
    private void handleSave() {
        if (onSaveHandler != null) {
            onSaveHandler.accept(getSelectedServices());
        }
    }

    public List<String> getSelectedServices() {
        List<String> selected = new ArrayList<>();
        for (ServiceCardItem item : serviceItems) {
            if (item.checkBox().isSelected() && item.checkBox().getUserData() != null) {
                selected.add(item.checkBox().getUserData().toString());
            }
        }
        return selected;
    }

    public void setOnSaveHandler(Consumer<List<String>> onSaveHandler) {
        this.onSaveHandler = onSaveHandler;
    }

    private record ServiceCardItem(HBox card, CheckBox checkBox) {
    }
}
