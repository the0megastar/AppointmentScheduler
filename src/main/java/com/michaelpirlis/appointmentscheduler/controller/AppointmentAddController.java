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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static com.michaelpirlis.appointmentscheduler.MainMenuController.displayScene;
import static com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL.appointmentOverlap;
import static com.michaelpirlis.appointmentscheduler.helper.TimeConversions.businessHours;

public class AppointmentAddController extends Application implements Initializable {

    protected ObservableList<String> hours = FXCollections.observableArrayList();
    protected ObservableList<String> minutes = FXCollections.observableArrayList();

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
    protected ZonedDateTime zonedStart;
    protected ZonedDateTime zonedEnd;
    protected int customerID;
    protected int contactID;
    protected boolean errorCheck = false;

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

    protected void setupTime() {
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        startHour.setItems(hours);
        startMinute.setItems(minutes);
        endHour.setItems(hours);
        endMinute.setItems(minutes);
    }

    protected void setupAppointmentType() {
        ObservableList<String> options = FXCollections.observableArrayList("Coding Session",
                "Data Structures", "Coding Puzzles");
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

    protected void apptErrorHandling() {
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

        if (!businessHours(zonedStart, zonedEnd)) {
            errorMessage.append("Appointments must be within 8:00AM - 10:00PM Eastern Time.\n");
        }
//
//        String overlapCheck = appointmentOverlap(customerID, zonedStart, zonedEnd);
//        if (overlapCheck != null) {
//            errorMessage.append(overlapCheck);
//        }

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

    private void initializeApptForm() {
        apptTitleText.clear();
        apptDescriptionText.clear();
        apptLocationText.clear();
        startHour.getSelectionModel().clearSelection();
        startMinute.getSelectionModel().clearSelection();
        startDate.setValue(null);
        endHour.getSelectionModel().clearSelection();
        endMinute.getSelectionModel().clearSelection();
        endDate.setValue(null);
        appointmentType.getSelectionModel().clearSelection();
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

//        if (startHour.getSelectionModel().getSelectedItem() != null &&
//                startMinute.getSelectionModel().getSelectedItem() != null &&
//                startDate.getValue() != null) {
//            zonedStart = TimeConversions.timeCollection(startHour, startMinute, startDate);
//            System.out.println("zonedStart set to: " + zonedStart);
//        }
//
//        if (endHour.getSelectionModel().getSelectedItem() != null &&
//                endMinute.getSelectionModel().getSelectedItem() != null &&
//                endDate.getValue() != null) {
//            zonedEnd = TimeConversions.timeCollection(endHour, endMinute, endDate);
//            System.out.println("zonedEnd set to: " + zonedEnd);
//        }
    }

    @FXML
    private void saveAppointmentButton() {
        apptErrorHandling();
        setContactID();

        /*
          The line Timestamp lastUpdate = Timestamp.from(Instant.now());
          creates a Timestamp representing the current time in the local time zone (Eastern Time).
         */
        Timestamp lastUpdate = Timestamp.from(Instant.now());

        if (errorCheck) {
            int appointmentID = 0;
            int userID = 1;
            String currentUser = "Mike"; // later to currentUser;

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