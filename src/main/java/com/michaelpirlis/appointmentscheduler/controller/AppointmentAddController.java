package com.michaelpirlis.appointmentscheduler.controller;

import com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL;
import com.michaelpirlis.appointmentscheduler.dao.ContactSQL;
import com.michaelpirlis.appointmentscheduler.dao.CustomerSQL;
import com.michaelpirlis.appointmentscheduler.helper.TimeConversions;
import com.michaelpirlis.appointmentscheduler.model.Appointment;
import com.michaelpirlis.appointmentscheduler.model.Contact;
import com.michaelpirlis.appointmentscheduler.model.Customer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static com.michaelpirlis.appointmentscheduler.MainMenuController.displayScene;
import static com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL.appointmentOverlap;
import static com.michaelpirlis.appointmentscheduler.helper.TimeConversions.createTimestamp;

public class AppointmentAddController extends Application implements Initializable {

    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();
    @FXML
    private TextField apptTitleText;
    @FXML
    private TextField apptDescriptionText;
    @FXML
    private TextField apptLocationText;
    @FXML
    private ComboBox<String> appointmentType;
    @FXML
    private ComboBox<String> startHour;
    @FXML
    private ComboBox<String> startMinute;
    @FXML
    private ComboBox<String> endHour;
    @FXML
    private ComboBox<String> endMinute;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<Contact> contactComboBox;
    private ZonedDateTime zonedStart;
    private ZonedDateTime zonedEnd;
    private int customerID;
    private int contactID;
    private boolean errorCheck = false;

    @Override
    public void start(Stage stage) throws IOException {
        displayScene("appointment-add.fxml", stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTime();
        setupAppointmentType();
        setupCustomerCombo();
        setupContactCombo();
    }

    private void setupTime() {
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        startHour.setItems(hours);
        startMinute.setItems(minutes);
        endHour.setItems(hours);
        endMinute.setItems(minutes);
    }

    private void setupAppointmentType() {
        ObservableList<String> options = FXCollections.observableArrayList("Code Review Session",
                "Pair Programming Session", "Technical Interview Preparation");
        appointmentType.setItems(options);
    }

    protected void setupCustomerCombo() {
        ObservableList<Customer> customers = CustomerSQL.allCustomers();
        customerComboBox.setItems(customers);
    }

    protected void setupContactCombo() {
        ObservableList<Contact> contacts = ContactSQL.allContacts();
        contactComboBox.setItems(contacts);
    }

    @FXML
    private void addAppointmentButton(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        displayScene("appointment-add.fxml", appStage);
    }

    @FXML
    private void addCustomerButton(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        displayScene("customer-add.fxml", appStage);
    }

    @FXML
    private void backButton(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        displayScene("main-menu.fxml", appStage);
    }

    @FXML
    private void exitButton() {
        Platform.exit();
    }

    void apptErrorHandling() {
        StringBuilder errorMessage = new StringBuilder();

        setTimes();
        setCustomerID();

        if (apptTitleText.getText().isEmpty()) {
            errorMessage.append("Appointment Title is required.\n");
        }

        if (apptDescriptionText.getText().isEmpty()) {
            errorMessage.append("Appointment Description is required.\n");
        }

        if (apptLocationText.getText().isEmpty()) {
            errorMessage.append("Appointment Location is required.\n");
        }

        if (startHour.getSelectionModel().getSelectedItem() == null) {
            errorMessage.append("Appointment Start Hour is required.\n");
        }

        if (startMinute.getSelectionModel().getSelectedItem() == null) {
            errorMessage.append("Appointment Start Minute is required.\n");
        }

        if (startDate.getValue() == null) {
            errorMessage.append("Appointment Start Date is required.\n");
        }

        if (endHour.getSelectionModel().getSelectedItem() == null) {
            errorMessage.append("Appointment End Hour is required.\n");
        }

        if (endMinute.getSelectionModel().getSelectedItem() == null) {
            errorMessage.append("Appointment End Minute is required.\n");
        }

        if (endDate.getValue() == null) {
            errorMessage.append("Appointment End Date is required.\n");
        }

        if (customerComboBox.getSelectionModel().getSelectedItem() == null) {
            errorMessage.append("Customer is required.\n");
        }

        if (contactComboBox.getSelectionModel().getSelectedItem() == null) {
            errorMessage.append("Contact is required.\n");
        }

        String overlapCheck = appointmentOverlap(customerID, zonedStart, zonedEnd);
        if (overlapCheck != null) {
            errorMessage.append(overlapCheck);
        }

        displayErrors(errorMessage);

        if (errorMessage.length() == 0) {
            errorCheck = true;
        }
    }

    private void displayErrors(StringBuilder errorMessage) {
        if (errorMessage.length() > 0) {
            Alert saveError = new Alert(Alert.AlertType.INFORMATION);
            saveError.setTitle("Unable To Save");
            saveError.setHeaderText(null);
            saveError.setContentText(errorMessage.toString());
            saveError.showAndWait();
        }
    }

    void initializeApptForm() {
        apptTitleText.clear();
        apptDescriptionText.clear();
        apptLocationText.clear();
        startHour.getSelectionModel().clearSelection();
        startMinute.getSelectionModel().clearSelection();
        startDate.setValue(null);
        endHour.getSelectionModel().clearSelection();
        endMinute.getSelectionModel().clearSelection();
        endDate.setValue(null);
        customerComboBox.getSelectionModel().clearSelection();
        contactComboBox.getSelectionModel().clearSelection();
        errorCheck = false;
    }

    private void setCustomerID() {
        Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
        customerID = selectedCustomer.getCustomerId();
    }

    private void setContactID() {
        Contact selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
        contactID = selectedContact.getContactID();
    }

    private void setTimes() {
        zonedStart = TimeConversions.timeCollection(startHour, startMinute, startDate);
        zonedEnd = TimeConversions.timeCollection(endHour, endMinute, endDate);
    }

    @FXML
    private void saveAppointmentButton() {
        apptErrorHandling();
        setContactID();

        if (errorCheck) {
            int appointmentID = 0;
            int userID = 1;
            String currentUser = "Mike"; // later to currentUser;
            Timestamp lastUpdate = createTimestamp();

            Appointment appointment = new Appointment(
                    appointmentID,
                    apptTitleText.getText(),
                    apptDescriptionText.getText(),
                    apptLocationText.getText(),
                    appointmentType.getValue(),
                    zonedStart,
                    zonedEnd,
                    TimeConversions.currentZoned(),
                    currentUser,
                    lastUpdate,
                    currentUser,
                    customerID,
                    userID,
                    contactID
            );

            AppointmentSQL.createAppointment(appointment);
            initializeApptForm();
            errorCheck = false;
        }
    }
}
