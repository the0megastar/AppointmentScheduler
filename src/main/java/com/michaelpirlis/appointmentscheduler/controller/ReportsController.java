package com.michaelpirlis.appointmentscheduler.controller;

import com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL;
import com.michaelpirlis.appointmentscheduler.dao.ContactSQL;
import com.michaelpirlis.appointmentscheduler.dao.CountrySQL;
import com.michaelpirlis.appointmentscheduler.model.Appointment;
import com.michaelpirlis.appointmentscheduler.model.Contact;
import com.michaelpirlis.appointmentscheduler.model.Country;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.michaelpirlis.appointmentscheduler.controller.MainMenuController.displayScene;
import static com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL.allAppointments;
import static com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL.appointmentMonthType;
import static com.michaelpirlis.appointmentscheduler.dao.CustomerSQL.getCustomerCountry;

public class ReportsController extends Application implements Initializable {

    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private ComboBox<String> appointmentType;
    @FXML
    private ComboBox<String> monthComboBox;
    @FXML
    private TableColumn<Object, Object> appointmentIdColumn;
    @FXML
    private TableColumn<Object, Object> appointmentTitleColumn;
    @FXML
    private TableColumn<Object, Object> appointmentDescriptionColumn;
    @FXML
    private TableColumn<Object, Object> appointmentTypeColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentStartColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentEndColumn;
    @FXML
    private TableColumn<Object, Object> appointmentCustomerIdColumn;
    @FXML
    private TableView<Appointment> allAppointmentTable;
    @FXML
    private Label appointmentCountLabel;
    @FXML
    private ComboBox<Country> countryComboBox;
    @FXML
    private Label countryCountLabel;

    /**
     * Sets up the appointment table with the columns and data.
     *
     * @param allAppointmentTable          The TableView to set up.
     * @param appointmentIdColumn          The column for the appointment ID.
     * @param appointmentTitleColumn       The column for the appointment title.
     * @param appointmentDescriptionColumn The column for the appointment description.
     * @param appointmentTypeColumn        The column for the appointment type.
     * @param appointmentStartColumn       The column for the appointment start time.
     * @param appointmentEndColumn         The column for the appointment end time.
     * @param appointmentCustomerIdColumn  The column for the customer ID associated with the appointment.
     */
    public static void appointmentTableSetup(TableView<Appointment> allAppointmentTable,
                                             TableColumn<Object, Object> appointmentIdColumn,
                                             TableColumn<Object, Object> appointmentTitleColumn,
                                             TableColumn<Object, Object> appointmentDescriptionColumn,
                                             TableColumn<Object, Object> appointmentTypeColumn,
                                             TableColumn<Appointment, String> appointmentStartColumn,
                                             TableColumn<Appointment, String> appointmentEndColumn,
                                             TableColumn<Object, Object> appointmentCustomerIdColumn) {
        allAppointmentTable.setItems(allAppointments());
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        appointmentCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        appointmentStartColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getApptStart().format(formatter)));
        appointmentEndColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getApptEnd().format(formatter)));
    }

    /**
     * Loads the 'reports.fxml' scene.
     */
    @Override
    public void start(Stage stage) throws Exception {
        displayScene("reports.fxml", stage);
    }

    /**
     * Initializes the controller and sets up  GUI elements and listeners.
     * Contact combo box with all contacts from the database.
     * Check for changes in the Contact combo box and update the appointment table view accordingly.
     * Appointment Type combo box with preset options.
     * Month combo box with all months and changes in the Appointment Type combo box and Month combo box selection.
     * Update the appointment count label accordingly.
     * Country combo box with all countries from the database and look for changes in the Country combo box selection,
     * update the customer count label accordingly. Lastly set up the appointment table with specified columns.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupContactCombo();
        changeContactFilter();
        setupAppointmentType();
        setupMonthCombo();
        changeAppointmentTypeLabel();
        setupCountryCombo();
        changeCountryFilter();

        appointmentTableSetup(allAppointmentTable, appointmentIdColumn, appointmentTitleColumn,
                appointmentDescriptionColumn, appointmentTypeColumn, appointmentStartColumn,
                appointmentEndColumn, appointmentCustomerIdColumn);

    }

    /**
     * Sets up the combo box for contact selection.
     * It populates the combo box with all contacts from the database.
     */
    private void setupContactCombo() {
        ObservableList<Contact> contacts = ContactSQL.allContacts();
        contactComboBox.setItems(contacts);
    }

    /**
     * LAMBDA. Changes the appointment filter according to the selected contact.
     * It updates the appointment table view with appointments of the selected contact.
     */
    @FXML
    public void changeContactFilter() {
        contactComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {

            if (newSelection != null) {
                ObservableList<Appointment> appointments = AppointmentSQL.contactAppointments(newSelection.getContactID());
                allAppointmentTable.setItems(appointments);
            }
        });
    }

    /**
     * Sets up the combo box for country selection.
     * It populates the combo box with all countries from the database.
     */
    private void setupCountryCombo() {
        ObservableList<Country> countries = CountrySQL.allCountries();
        countryComboBox.setItems(countries);
    }

    /**
     * LAMBDA. Changes the customer count according to the selected country.
     * It updates the customer count label with the number of customers in the selected country.
     */
    private void changeCountryFilter() {
        countryComboBox.valueProperty().addListener((observable, oldSelection, newSelection) -> updateCustomerCount());
    }

    /**
     * Updates the label that displays the customer count for the selected country.
     */
    private void updateCustomerCount() {
        String selectedCountry = String.valueOf(countryComboBox.getValue());

        if (selectedCountry != null) {
            int count = getCustomerCountry(selectedCountry);
            countryCountLabel.setText("Total Customers: " + count);
        }
    }

    /**
     * LAMBDA. Changes the appointment count label according to the selected appointment type and month.
     */
    @FXML
    public void changeAppointmentTypeLabel() {
        appointmentType.valueProperty().addListener((obs, oldSelection, newSelection) -> updateAppointmentCountLabel());
        monthComboBox.valueProperty().addListener((obs, oldSelection, newSelection) -> updateAppointmentCountLabel());
    }

    /**
     * Updates the label that displays the appointment count for the selected appointment type and month.
     */
    private void updateAppointmentCountLabel() {
        String selectedType = appointmentType.getValue();
        String selectedMonth = monthComboBox.getValue();

        if (selectedType != null && selectedMonth != null) {
            int count = appointmentMonthType(selectedType, selectedMonth);
            appointmentCountLabel.setText("Total Appointments: " + count);
        }
    }


    /**
     * Sets up the combo box for appointment type.
     * It populates the combo box with preset options.
     */
    private void setupAppointmentType() {
        ObservableList<String> options = FXCollections.observableArrayList("Coding Session",
                "Data Structures", "Coding Puzzles");
        appointmentType.setItems(options);
    }

    /**
     * Sets up the combo box for month selection.
     * It populates the combo box with all months of the year.
     */
    private void setupMonthCombo() {
        ObservableList<String> monthNames = FXCollections.observableArrayList();
        for (Month month : Month.values()) {
            monthNames.add(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        }
        monthComboBox.setItems(monthNames);
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
     * Closes the application.
     */
    @FXML
    private void exitButton() {
        Platform.exit();
    }
}
