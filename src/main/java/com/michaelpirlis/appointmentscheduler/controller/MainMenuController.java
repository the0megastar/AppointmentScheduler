package com.michaelpirlis.appointmentscheduler.controller;

import com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL;
import com.michaelpirlis.appointmentscheduler.dao.CustomerSQL;
import com.michaelpirlis.appointmentscheduler.model.Appointment;
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
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL.*;
import static com.michaelpirlis.appointmentscheduler.dao.ContactSQL.getContactName;
import static com.michaelpirlis.appointmentscheduler.dao.CustomerSQL.allCustomers;

public class MainMenuController extends Application implements Initializable {

    public static Customer updateCustomer;
    public static Appointment updateAppointment;
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

    /**
     * Sets up the appointment table with the columns and data.
     *
     * @param allAppointmentTable          The TableView to set up.
     * @param appointmentIdColumn          The column for the appointment ID.
     * @param appointmentTitleColumn       The column for the appointment title.
     * @param appointmentDescriptionColumn The column for the appointment description.
     * @param appointmentLocationColumn    The column for the appointment location.
     * @param appointmentContactColumn     The column for the appointment contact.
     * @param appointmentTypeColumn        The column for the appointment type.
     * @param appointmentStartColumn       The column for the appointment start time.
     * @param appointmentEndColumn         The column for the appointment end time.
     * @param appointmentCustomerIdColumn  The column for the customer ID associated with the appointment.
     * @param appointmentUserId            The column for the user ID associated with the appointment.
     */
    public static void appointmentTableSetup(TableView<Appointment> allAppointmentTable,
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

    /**
     * Sets up the customer table with the columns and data.
     *
     * @param allCustomerTable      The TableView to set up.
     * @param customerIdColumn      The column for the customer ID.
     * @param customerNameColumn    The column for the customer name.
     * @param customerStreetColumn  The column for the customer's street.
     * @param customerPostalColumn  The column for the customer's postal code.
     * @param customerPhoneColumn   The column for the customer's phone number.
     * @param customerStateColumn   The column for the customer's state.
     * @param customerCountryColumn The column for the customer's country.
     */
    public static void customerTableSetup(TableView<Customer> allCustomerTable,
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

    /**
     * Loads the specified scene. Crated a method to provide the fxml instead of this code block.
     *
     * @param fxmlFile The file of the scene to display.
     * @param stage    The stage to display the scene in.
     * @throws IOException If the file is not found.
     */
    public static void displayScene(String fxmlFile, Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource(
                "/com/michaelpirlis/appointmentscheduler/" + fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Checks for any appointments coming up within the next 15 minutes and displays an alert message.
     * The alert message varies on whether there is an upcoming appointment or not. The scope did not contain it,
     * I would have preferred no message unless an appointment was found and initialize it when the 'menu-menu'
     * was called. Runs the SQL check to the database and saves the Appointment if found so that it may be displayed
     * in the alert.
     */
    public static void appointmentCheck() {
        Appointment upcomingAppointment = upcomingAppointment();

        if (upcomingAppointment != null) {

            Alert upcomingAppointmentAlert = new Alert(Alert.AlertType.INFORMATION);
            upcomingAppointmentAlert.setTitle("Upcoming Appointment");
            upcomingAppointmentAlert.setHeaderText(null);
            upcomingAppointmentAlert.setContentText("There is an appointment coming up in 15 minutes!\n\n" +
                    "Appointment ID: " + upcomingAppointment.getApptID() + "\n" +
                    "Appointment Date: " + upcomingAppointment.getApptStart().toLocalDate() + "\n" +
                    "Appointment Time: " + upcomingAppointment.getApptStart().toLocalTime() + "\n" +
                    "User ID: " + upcomingAppointment.getUserID());
            upcomingAppointmentAlert.showAndWait();
        } else {

            Alert noUpcomingAppointmentAlert = new Alert(Alert.AlertType.INFORMATION);
            noUpcomingAppointmentAlert.setTitle("No Upcoming Appointments");
            noUpcomingAppointmentAlert.setHeaderText(null);
            noUpcomingAppointmentAlert.setContentText("There are no appointments in the next 15 minutes.");
            noUpcomingAppointmentAlert.showAndWait();
        }

    }

    /**
     * Initializes the scene, setting up the appointment filter and table, and the customer table.
     */
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

    /**
     * Sets up the appointment filter with options and sets the default selection to "All".
     */
    private void setupAppointmentFilter() {
        ObservableList<String> options = FXCollections.observableArrayList("All", "Weekly", "Monthly");
        appointmentFilter.setItems(options);
        appointmentFilter.getSelectionModel().select("All");
    }

    /**
     * Changes the items displayed in the appointment table based on the selected filter option. LAMBDA expressions are
     * used throughout the application when multiple selections are needed. It helps in my case to compare the old and
     * new selection the run the code. In this case clearing the table and then getting the appointment time frames.
     * IntelliJ also recommend the format for the updated cases. The code repository had a great example for alerts.
     * alert.showAndWait().ifPresent((response ->
     * I found this example in https://stackoverflow.com/questions/41323945/javafx-combobox-add-listener-on-selected-item-value
     * with the second answer with 8 upvotes.
     */
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

    /**
     * Loads the 'main-menu.fxml' scene.
     *
     * @param stage The primary stage.
     * @throws IOException If the file 'main-menu.fxml' is not found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        displayScene("main-menu.fxml", stage);
    }

    /**
     * Displays an alert message when no item is selected from a table.
     */
    private void noSelection() {
        Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
        noSelection.setTitle("No Selection");
        noSelection.setHeaderText(null);
        noSelection.setContentText("Please select an item from the table.");
        noSelection.showAndWait();
    }

    /**
     * Deletes the selected appointment from the database.
     * Displays an alert message to confirm the deletion.
     */
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

    /**
     * Deletes the selected customer from the database.
     * Also deletes any appointments associated with the customer.
     * Displays an alert message to confirm the deletion.
     */
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
     * Handles the action when the 'Update Appointment' button is clicked.
     * Checks if an appointment is selected and loads the 'appointment-update.fxml' scene.
     * If no appointment is selected, it calls the noSelection() method.
     *
     * @param event The action event.
     * @throws IOException If the file 'appointment-update.fxml' is not found.
     */
    @FXML
    private void updateAppointmentButton(ActionEvent event) throws IOException {
        updateAppointment = allAppointmentTable.getSelectionModel().getSelectedItem();

        if (updateAppointment == null) {
            noSelection();
        } else {
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            displayScene("appointment-update.fxml", appStage);
        }
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
     * Handles the action when the 'Update Customer' button is clicked.
     * Checks if a customer is selected and loads the 'customer-update.fxml' scene.
     * If no customer is selected, it calls the noSelection() method.
     *
     * @param event The action event.
     * @throws IOException If the file 'customer-update.fxml' is not found.
     */
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

    /**
     * Handles the action when the 'Reports' button is clicked.
     * Loads and displays the 'reports.fxml' scene.
     *
     * @param event The action event.
     * @throws IOException If the file 'reports.fxml' is not found.
     */
    @FXML
    private void reportsButton(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        displayScene("reports.fxml", appStage);
    }

    /**
     * Closes the JavaFX application when the exit button is clicked.
     */
    @FXML
    private void exitButton() {
        Platform.exit();
    }

}
