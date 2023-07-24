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

import static com.michaelpirlis.appointmentscheduler.controller.LoginController.currentUserID;
import static com.michaelpirlis.appointmentscheduler.controller.LoginController.currentUsername;
import static com.michaelpirlis.appointmentscheduler.controller.MainMenuController.displayScene;
import static com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL.appointmentOverlap;
import static com.michaelpirlis.appointmentscheduler.helper.TimeConversions.businessHours;

public class AppointmentAddController extends Application implements Initializable {

    protected ObservableList<String> hours = FXCollections.observableArrayList();
    protected ObservableList<String> minutes = FXCollections.observableArrayList();
    protected ZonedDateTime zonedStart;
    protected ZonedDateTime zonedEnd;
    protected int customerID;
    protected int contactID;
    protected boolean errorCheck = false;
    protected StringBuilder errorMessage = new StringBuilder();
    protected int userID = currentUserID;
    protected String currentUser = currentUsername;
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

    /**
     * Loads the 'appointment-add.fxml' scene when the application starts.
     *
     * @param stage The primary stage.
     * @throws IOException If the file 'appointment-add.fxml' is not found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        displayScene("appointment-add.fxml", stage);
    }

    /**
     * Initializes the scene, setting up the time combo box, appointment type, customer combo box, and contact combo box.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTime();
        setupAppointmentType();
        setupCustomerCombo();
        setupContactCombo();
    }

    /**
     * Sets up the time combo boxes with hours and minutes. From the Date Picker controller in the Code Repository.
     */
    protected void setupTime() {
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        startHour.setItems(hours);
        startMinute.setItems(minutes);
        endHour.setItems(hours);
        endMinute.setItems(minutes);
    }

    /**
     * Sets up the appointment type combo box with the appointment types I chose.
     */
    protected void setupAppointmentType() {
        ObservableList<String> options = FXCollections.observableArrayList("Coding Session",
                "Data Structures", "Coding Puzzles");
        appointmentType.setItems(options);
    }

    /**
     * Sets up the customer combo box with all customers from the database.
     */
    protected void setupCustomerCombo() {
        ObservableList<Customer> customers = CustomerSQL.allCustomers();
        customerComboBox.setItems(customers);
    }

    /**
     * Sets up the contact combo box with all contacts from the database.
     */
    protected void setupContactCombo() {
        ObservableList<Contact> contacts = ContactSQL.allContacts();
        contactComboBox.setItems(contacts);
    }

    /**
     * Handles the action when the 'Add Appointment' button is clicked.
     * Loads the 'appointment-add.fxml' scene.
     *
     * @param event The action event.
     * @throws IOException If the file 'appointment-add.fxml' is not found.
     */
    @FXML
    private void addAppointmentButton(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        displayScene("appointment-add.fxml", appStage);
    }

    /**
     * Handles the action when the 'Add Customer' button is clicked.
     * Loads the 'customer-add.fxml' scene.
     *
     * @param event The action event.
     * @throws IOException If the file 'customer-add.fxml' is not found.
     */
    @FXML
    private void addCustomerButton(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        displayScene("customer-add.fxml", appStage);
    }

    /**
     * Navigates the user back to the main menu scene.
     */
    @FXML
    protected void backButton(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        displayScene("main-menu.fxml", appStage);
    }

    /**
     * Closes the JavaFX application when the exit button is clicked.
     */
    @FXML
    private void exitButton() {
        Platform.exit();
    }

    /**
     * Handles any errors in the appointment form by checking if all fields are filled in.
     * Appends error messages to a StringBuilder if any fields are empty or incorrect. reused frm my Software I project.
     */
    protected void apptErrorHandling() {
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

        if (zonedEnd.isBefore(zonedStart)) {
            errorMessage.append("End time must be after start time.\n");
        }

        if (!zonedStart.toLocalDate().equals(zonedEnd.toLocalDate())) {
            errorMessage.append("Start and end time must be on the same day.\n");
        }

        displayErrors(errorMessage);

        if (errorMessage.length() == 0) {
            errorCheck = true;
        }
    }

    /**
     * Displays a message with the error messages from the StringBuilder if it is not empty.
     *
     * @param errorMessage The StringBuilder containing the error messages.
     */
    private void displayErrors(StringBuilder errorMessage) {
        if (errorMessage.length() > 0) {
            Alert saveError = new Alert(Alert.AlertType.INFORMATION);
            saveError.setTitle("Unable To Save");
            saveError.setHeaderText(null);
            saveError.setContentText(errorMessage.toString());
            saveError.showAndWait();
        }
    }

    /**
     * Initializes the appointment form by clearing all fields and selections.
     */
    protected void initializeApptForm() {
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

    /**
     * Sets the customer ID to the ID of the selected customer.
     */
    private void setCustomerID() {
        Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
        customerID = selectedCustomer.getCustomerId();
    }

    /**
     * Sets the contact ID to the ID of the selected contact.
     */
    private void setContactID() {
        Contact selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
        contactID = selectedContact.getContactID();
    }

    /**
     * Sets the start and end times of the appointment. Constructs a ZonedDateTime in UTC in the timeCollection method.
     */
    protected void setTimes() {
        zonedStart = TimeConversions.timeCollection(startHour, startMinute, startDate);
        zonedEnd = TimeConversions.timeCollection(endHour, endMinute, endDate);
    }

    /**
     * Saves the appointment to the database if there are no errors in the form.
     * Initializes the appointment form and navigates back to the previous scene.
     * Sets Appointment ID to 0 so database can create and auto increment. Checks for an overlapping appointment.
     */
    @FXML
    private void saveAppointmentButton(ActionEvent event) throws IOException {
        int appointmentID = 0;
        errorMessage = new StringBuilder();

        setTimes();

        String overlapCheck = appointmentOverlap(appointmentID, zonedStart, zonedEnd);
        if (overlapCheck != null) {
            errorMessage.append(overlapCheck);
        }

        apptErrorHandling();
        setContactID();

        Timestamp lastUpdate = Timestamp.from(Instant.now());

        if (errorCheck) {

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
            backButton(event);
        }
    }

}