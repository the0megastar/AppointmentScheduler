package com.michaelpirlis.appointmentscheduler.controller;

import com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL;
import com.michaelpirlis.appointmentscheduler.helper.TimeConversions;
import com.michaelpirlis.appointmentscheduler.model.Appointment;
import com.michaelpirlis.appointmentscheduler.model.Contact;
import com.michaelpirlis.appointmentscheduler.model.Customer;
import javafx.event.ActionEvent;
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
import static com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL.appointmentOverlap;

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


    /**
     * Loads the 'appointment-update.fxml' scene.
     */
    @Override
    public void start(Stage stage) throws IOException {
        displayScene("appointment-update.fxml", stage);
    }

    /**
     * This method initializes the scene by setting up the combo boxes with importing customer, contact information,
     * and updating appointment details.
     */
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

    /**
     * This method selects the customer in the combo box that matches the customerID of the updateAppointment. It goes
     * through each item in the combo box to find the match.
     */
    private void customerImport() {
        int selectedCustomerID = updateAppointment.getCustomerID();

        for (Customer customer : customerComboBox.getItems()) {

            if (customer.getCustomerId() == selectedCustomerID) {
                customerComboBox.getSelectionModel().select(customer);
                break;
            }
        }
    }

    /**
     * This method selects the contact in the contact combo box that matches the contactID of the updateAppointment.
     */
    private void contactImport() {
        int selectedContactID = updateAppointment.getContactID();

        for (Contact contact : contactComboBox.getItems()) {

            if (contact.getContactID() == selectedContactID) {
                contactComboBox.getSelectionModel().select(contact);
                break;
            }
        }
    }

    /**
     * This method imports the details of the updateAppointment and sets the corresponding fields in the scene.
     * Added formatting so that I could continue to use my combo boxes for hours and minutes.
     */
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

    /**
     * This method sets the contactID to the contactID of the selected contact in the combo box.
     */
    private void setContactID() {
        Contact selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
        contactID = selectedContact.getContactID();
    }

    /**
     * This method is triggered when the updateAppointmentButton is clicked. It validates the inputs, creates an Appointment,
     * calls the updateAppointment method to update the appointment in the database, and then clears the form. I also moved
     * logic in here to exlude this appointment from overlapping itself by using the ID to exclude it.
     */
    @FXML
    private void updateAppointmentButton(ActionEvent event) throws IOException {

        int appointmentID = Integer.parseInt(apptIDText.getText());
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

            AppointmentSQL.updateAppointment(appointment);
            initializeApptForm();
            errorCheck = false;
            backButton(event);
        }
    }

}
