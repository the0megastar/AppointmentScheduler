package com.michaelpirlis.appointmentscheduler;

import com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL;
import com.michaelpirlis.appointmentscheduler.dao.CustomerSQL;
import com.michaelpirlis.appointmentscheduler.model.Appointment;
import com.michaelpirlis.appointmentscheduler.model.Contact;
import com.michaelpirlis.appointmentscheduler.model.Customer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL.*;
import static com.michaelpirlis.appointmentscheduler.dao.ContactSQL.getContactName;
import static com.michaelpirlis.appointmentscheduler.dao.CustomerSQL.allCustomers;

public class MainMenuController extends Application implements Initializable {

    @FXML
    private TableColumn<Object, Object> appointmentIdColumn;
    @FXML
    private TableColumn<Object, Object> appointmentTitleColumn;
    @FXML
    private TableColumn<Object, Object> appointmentDescriptionColumn;
    @FXML
    private TableColumn<Object, Object> appointmentLocationColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentContactColumn;
    @FXML
    private TableColumn<Object, Object> appointmentTypeColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentStartColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentEndColumn;
    @FXML
    private TableColumn<Object, Object> appointmentCustomerIdColumn;
    @FXML
    private TableColumn<Object, Object> appointmentUserId;
    @FXML
    private TableView<Appointment> allAppointmentTable;

    @FXML
    private TableColumn<Object, Object> customerIdColumn;
    @FXML
    private TableColumn<Object, Object> customerNameColumn;
    @FXML
    private TableColumn<Object, Object> customerStreetColumn;
    @FXML
    private TableColumn<Object, Object> customerStateColumn;
    @FXML
    private TableColumn<Object, Object> customerCountryColumn;
    @FXML
    private TableColumn<Object, Object> customerPostalColumn;
    @FXML
    private TableColumn<Object, Object> customerPhoneColumn;
    @FXML
    private TableView<Customer> allCustomerTable;

    @FXML
    private ComboBox<String> appointmentFilter;
    @FXML
    private Button deleteAppointmentButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Button reportsButton;

    public static Customer updateCustomer;

    static void appointmentTableSetup(TableView<Appointment> allAppointmentTable,
                                      TableColumn<Object, Object> appointmentIdColumn,
                                      TableColumn<Object, Object> appointmentTitleColumn,
                                      TableColumn<Object, Object> appointmentDescriptionColumn,
                                      TableColumn<Object, Object> appointmentLocationColumn,
                                      TableColumn<Appointment, String> appointmentContactColumn,
                                      TableColumn<Object, Object> appointmentTypeColumn,
                                      TableColumn<Appointment, String> appointmentStartColumn,
                                      TableColumn<Appointment, String> appointmentEndColumn,
                                      TableColumn<Object, Object> appointmentCustomerIdColumn,
                                      TableColumn<Object, Object> appointmentUserId) {
        allAppointmentTable.setItems(allAppointments());
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        appointmentContactColumn.setCellValueFactory(p -> new SimpleStringProperty(getContactName(p.getValue().getContactID())));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        appointmentCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserId.setCellValueFactory(new PropertyValueFactory<>("userID"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        appointmentStartColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getApptStart().format(formatter)));
        appointmentEndColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getApptEnd().format(formatter)));
    }

    static void customerTableSetup(TableView<Customer> allCustomerTable,
                                   TableColumn<Object, Object> customerIdColumn,
                                   TableColumn<Object, Object> customerNameColumn,
                                   TableColumn<Object, Object> customerStreetColumn,
                                   TableColumn<Object, Object> customerPostalColumn,
                                   TableColumn<Object, Object> customerPhoneColumn,
                                   TableColumn<Object, Object> customerStateColumn,
                                   TableColumn<Object, Object> customerCountryColumn) {
        allCustomerTable.setItems(allCustomers());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerStreetColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostalColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerStateColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    }

    public static void displayScene(String fxmlFile, Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void initialize(URL location, ResourceBundle resources) {
        setupAppointmentFilter();
        changeAppointmentFilter();

        appointmentTableSetup(allAppointmentTable, appointmentIdColumn, appointmentTitleColumn,
                appointmentDescriptionColumn, appointmentLocationColumn, appointmentContactColumn,
                appointmentTypeColumn, appointmentStartColumn, appointmentEndColumn, appointmentCustomerIdColumn,
                appointmentUserId);

        customerTableSetup(allCustomerTable, customerIdColumn, customerNameColumn, customerStreetColumn,
                customerPostalColumn, customerPhoneColumn, customerStateColumn, customerCountryColumn);
    }

    private void setupAppointmentFilter() {
        ObservableList<String> options = FXCollections.observableArrayList("All", "Weekly", "Monthly");
        appointmentFilter.setItems(options);
        appointmentFilter.getSelectionModel().select("All");
    }

    @FXML
    private void changeAppointmentFilter() {
        appointmentFilter.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {

            if (newSelection != null) {
                allAppointmentTable.getItems().clear();

                switch (newSelection) {
                    case "All" -> allAppointmentTable.getItems().addAll(allAppointments());
                    case "Weekly" -> allAppointmentTable.getItems().addAll(weeklyAppointments());
                    case "Monthly" -> allAppointmentTable.getItems().addAll(monthlyAppointments());
                }
            }
        });
    }

    @Override
    public void start(Stage stage) throws IOException {
        displayScene("main-menu.fxml", stage);
    }

    private void noSelection() {
        Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
        noSelection.setTitle("No Selection");
        noSelection.setHeaderText(null);
        noSelection.setContentText("Please select an item from the table.");
        noSelection.showAndWait();
    }

    @FXML
    private void deleteAppointmentButton() {
        Appointment selectedAppointment = allAppointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            noSelection();

        } else {
            int appointmentId = selectedAppointment.getApptID();

            Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDeletion.setTitle("Confirm Appointment Deletion");
            confirmDeletion.setHeaderText(null);
            confirmDeletion.setContentText("Would you like to delete appointment "
                    + (selectedAppointment.getApptID()) + " " + (selectedAppointment.getApptType()) + " ?"
                    + "\nThis action is final and cannot be undone.");

            Optional<ButtonType> result = confirmDeletion.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentSQL.deleteAppointments(appointmentId);
            }

            allAppointmentTable.setItems(allAppointments());
        }
    }

    @FXML
    private void deleteCustomerButton() {
        Customer selectedCustomer = allCustomerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            noSelection();

        } else {
            int customerId = selectedCustomer.getCustomerId();

            Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDeletion.setTitle("Confirm Customer Deletion");
            confirmDeletion.setHeaderText(null);
            confirmDeletion.setContentText("Would you like to delete the customer "
                    + (selectedCustomer.getCustomerName()) + " ?"
                    + "\nAll associated appointments will be deleted."
                    + "\nThis action is final and cannot be undone.");

            Optional<ButtonType> result = confirmDeletion.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentSQL.deleteCustomerAppointments(customerId);
                CustomerSQL.deleteCustomer(customerId);
            }

            allCustomerTable.setItems(allCustomers());
            allAppointmentTable.setItems(allAppointments());
        }
    }

    @FXML
    private void addAppointmentButton(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        displayScene("appointment-add.fxml", appStage);
    }

    @FXML
    private void updateAppointmentButton(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        displayScene("appointment-update.fxml", appStage);
    }

    @FXML
    private void addCustomerButton(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        displayScene("customer-add.fxml", appStage);
    }

    @FXML
    private void updateCustomerButton(ActionEvent event) throws IOException {
        updateCustomer = allCustomerTable.getSelectionModel().getSelectedItem();

        if (updateCustomer == null) {
            noSelection();
        } else {
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            displayScene("customer-update.fxml", appStage);
        }
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

}
