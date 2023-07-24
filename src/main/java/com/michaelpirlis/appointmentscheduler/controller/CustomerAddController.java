package com.michaelpirlis.appointmentscheduler.controller;

import com.michaelpirlis.appointmentscheduler.dao.CountrySQL;
import com.michaelpirlis.appointmentscheduler.dao.CustomerSQL;
import com.michaelpirlis.appointmentscheduler.dao.DivisionSQL;
import com.michaelpirlis.appointmentscheduler.model.Country;
import com.michaelpirlis.appointmentscheduler.model.Customer;
import com.michaelpirlis.appointmentscheduler.model.Division;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.michaelpirlis.appointmentscheduler.controller.MainMenuController.displayScene;

public class CustomerAddController extends Application implements Initializable {

    protected boolean errorCheck = false;
    @FXML
    private TextField customerIDTextField;
    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField PhoneNumberTextField;
    @FXML
    private ComboBox<Country> countryComboBox;
    @FXML
    private ComboBox<Division> divisionComboBox;
    @FXML
    private Button cancelButton;

    /**
     * Loads the 'customer-add.fxml' scene.
     *
     * @param stage The primary stage.
     * @throws IOException If the file 'main-menu.fxml' is not found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        displayScene("customer-add.fxml", stage);
    }

    /**
     * Initializes the scene, setting up the country combo box and its filter.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCountryCombo();
        changeCountryFilter();
        divisionComboBox.setPromptText("Please Choose a Country");
    }

    /**
     * Sets up the country combo box with all countries from the database.
     */
    protected void setupCountryCombo() {
        ObservableList<Country> countries = CountrySQL.allCountries();
        countryComboBox.setItems(countries);
    }

    /**
     * Changes the divisions displayed in the division combo box based on the selected country. LAMBDA Expression.
     */
    @FXML
    protected void changeCountryFilter() {
        countryComboBox.valueProperty().addListener((observable, oldCountry, newCountry) -> {
            if (newCountry != null) {
                ObservableList<Division> divisions;
                try {
                    divisions = DivisionSQL.getDivisionByCountry(newCountry.getCountryID());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                divisionComboBox.setItems(divisions);
                divisionComboBox.getSelectionModel().select(0);
            }
        });
    }

    /**
     * Initializes the customer form by clearing all fields and selections.
     */
    protected void initializeCustomerForm() {
        customerIDTextField.clear();
        customerNameTextField.clear();
        addressTextField.clear();
        postalCodeTextField.clear();
        PhoneNumberTextField.clear();
        divisionComboBox.getItems().clear();
        divisionComboBox.getSelectionModel().clearSelection();
        setupCountryCombo();
    }

    /**
     * Handles any errors in the customer form by checking if all fields are filled in.
     * Appends error messages to a StringBuilder if any fields are empty. I reused this logic from my Software I project,
     * which linked to a logicCheck. I modified it here and included some logic here and in the save method.
     */
    protected void customerErrorHandling() {
        StringBuilder errorMessage = new StringBuilder();

        if (customerNameTextField.getText().isEmpty()) {
            errorMessage.append("Customer Name is required.\n");
        }

        if (addressTextField.getText().isEmpty()) {
            errorMessage.append("Address is required.\n");
        }

        if (divisionComboBox.getSelectionModel().getSelectedItem() == null) {
            errorMessage.append("State or Province is required.\n");
        }

        if (countryComboBox.getSelectionModel().getSelectedItem() == null) {
            errorMessage.append("Country is required.\n");
        }

        if (postalCodeTextField.getText().isEmpty()) {
            errorMessage.append("Postal Code is required.\n");
        }

        if (PhoneNumberTextField.getText().isEmpty()) {
            errorMessage.append("Phone Number is required.\n");
        }

        displayErrors(errorMessage);

        if (errorMessage.length() == 0) {
            errorCheck = true;
        }
    }

    /**
     * Displays an alert message with the error messages from the StringBuilder if it is not empty.
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
     * Saves the customer to the database if there are no errors.
     * Initializes the customer form and returns back to the previous scene. Customer ID set to 0 as database
     * automatically generates ID and increments.
     *
     * @param event The action event.
     * @throws IOException If the previous scene file is not found.
     */
    @FXML
    private void saveCustomerButton(ActionEvent event) throws IOException {
        customerErrorHandling();

        if (errorCheck) {
            int customerId = 0;

            Customer customer = new Customer(
                    customerId,
                    customerNameTextField.getText(),
                    addressTextField.getText(),
                    postalCodeTextField.getText(),
                    PhoneNumberTextField.getText(),
                    divisionComboBox.getValue().getDivisionId()
            );

            CustomerSQL.createCustomer(customer);
            initializeCustomerForm();
            errorCheck = false;
            backButton(event);
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
    void backButton(ActionEvent event) throws IOException {
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

}
