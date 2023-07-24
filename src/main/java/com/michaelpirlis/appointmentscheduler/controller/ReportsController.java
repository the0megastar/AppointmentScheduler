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
import static com.michaelpirlis.appointmentscheduler.dao.ContactSQL.getContactName;
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

    @Override
    public void start(Stage stage) throws Exception {
        displayScene("reports.fxml", stage);
    }

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

    private void setupContactCombo() {
        ObservableList<Contact> contacts = ContactSQL.allContacts();
        contactComboBox.setItems(contacts);
    }

    @FXML
    public void changeContactFilter() {
        contactComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {

            if (newSelection != null) {
                ObservableList<Appointment> appointments = AppointmentSQL.contactAppointments(newSelection.getContactID());
                allAppointmentTable.setItems(appointments);
            }
        });
    }

    private void setupCountryCombo() {
        ObservableList<Country> countries = CountrySQL.allCountries();
        countryComboBox.setItems(countries);
    }

    private void changeCountryFilter() {
        countryComboBox.valueProperty().addListener((observable, oldSelection, newSelection) -> {
            updateCustomerCount();
        });
    }

    private void updateCustomerCount() {
        String selectedCountry = String.valueOf(countryComboBox.getValue());

        if (selectedCountry != null) {
            int count = getCustomerCountry(selectedCountry);
            countryCountLabel.setText("Total Customers: " + count);
        }
    }


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

    @FXML
    public void changeAppointmentTypeLabel() {
        appointmentType.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            updateAppointmentCountLabel();
        });

        monthComboBox.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            updateAppointmentCountLabel();
        });
    }

    private void updateAppointmentCountLabel() {
        String selectedType = appointmentType.getValue();
        String selectedMonth = monthComboBox.getValue();

        if (selectedType != null && selectedMonth != null) {
            int count = appointmentMonthType(selectedType, selectedMonth);
            appointmentCountLabel.setText("Total Appointments: " + count);
        }
    }



    private void setupAppointmentType() {
        ObservableList<String> options = FXCollections.observableArrayList("Coding Session",
                "Data Structures", "Coding Puzzles");
        appointmentType.setItems(options);
    }

    private void setupMonthCombo() {
        ObservableList<String> monthNames = FXCollections.observableArrayList();
        for (Month month : Month.values()) {
            monthNames.add(month.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        monthComboBox.setItems(monthNames);
    }

    @FXML
    protected void backButton(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        displayScene("main-menu.fxml", appStage);
    }

    @FXML
    private void exitButton() {
        Platform.exit();
    }
}
