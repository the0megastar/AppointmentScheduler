package com.michaelpirlis.appointmentscheduler.controller;

import com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL;
import com.michaelpirlis.appointmentscheduler.helper.TimeConversions;
import com.michaelpirlis.appointmentscheduler.model.Appointment;
import com.michaelpirlis.appointmentscheduler.model.Contact;
import com.michaelpirlis.appointmentscheduler.model.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ResourceBundle;

import static com.michaelpirlis.appointmentscheduler.controller.MainMenuController.displayScene;
import static com.michaelpirlis.appointmentscheduler.controller.MainMenuController.updateAppointment;

public class AppointmentUpdateController extends AppointmentAddController implements Initializable {

    @FXML
    private TextField apptIDText;
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


    @Override
    public void start(Stage stage) throws IOException {
        displayScene("appointment-update.fxml", stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTime();
        setupAppointmentType();
        setupCustomerCombo();
        setupContactCombo();
        updateAppointmentImport();
        customerImport();
        contactImport();
    }

    // Get the customerID for the updateAppointment
    // Go through each item in the ComboBox
    // If the customerID matches the selectedCustomerID, select this item in the ComboBox
    private void customerImport() {
        int selectedCustomerID = updateAppointment.getCustomerID();

        for (Customer customer : customerComboBox.getItems()) {

            if (customer.getCustomerId() == selectedCustomerID) {
                customerComboBox.getSelectionModel().select(customer);
                break;
            }
        }
    }

    private void contactImport() {
        int selectedContactID = updateAppointment.getContactID();

        for (Contact contact : contactComboBox.getItems()) {

            if (contact.getContactID() == selectedContactID) {
                contactComboBox.getSelectionModel().select(contact);
                break;
            }
        }
    }

    private void updateAppointmentImport() {
        if (updateAppointment != null) {
            apptIDText.setText(String.valueOf(updateAppointment.getApptID()));
            apptTitleText.setText(updateAppointment.getApptTitle());
            apptDescriptionText.setText(updateAppointment.getApptDescription());
            apptLocationText.setText(updateAppointment.getApptLocation());
            appointmentType.setValue(updateAppointment.getApptType());
            startDate.setValue(updateAppointment.getApptStart().toLocalDate());
            endDate.setValue(updateAppointment.getApptEnd().toLocalDate());
            startHour.setValue(String.format("%02d", updateAppointment.getApptStart().getHour()));
            startMinute.setValue(String.format("%02d", updateAppointment.getApptStart().getMinute()));
            endHour.setValue(String.format("%02d", updateAppointment.getApptEnd().getHour()));
            endMinute.setValue(String.format("%02d", updateAppointment.getApptEnd().getMinute()));
        }
    }

    private void setContactID() {
        Contact selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
        contactID = selectedContact.getContactID();
    }

    private void initializeApptForm() {
        apptIDText.clear();
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

    @FXML
    private void updateAppointmentButton() {
        apptErrorHandling();
        setContactID();

        Timestamp lastUpdate = Timestamp.from(Instant.now());

        if (errorCheck) {
            int userID = 1;
            String currentUser = "Mike"; // later to currentUser;

            Appointment appointment = new Appointment(
                    Integer.parseInt(apptIDText.getText()),
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
            appointment.printAppointment();

            AppointmentSQL.updateAppointment(appointment);
            initializeApptForm();
            errorCheck = false;
        }
    }

}
