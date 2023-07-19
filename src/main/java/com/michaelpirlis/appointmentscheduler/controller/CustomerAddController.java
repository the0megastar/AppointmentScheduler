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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.michaelpirlis.appointmentscheduler.MainMenuController.displayScene;

public class CustomerAddController extends Application implements Initializable {

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

    protected boolean errorCheck = false;

    @Override
    public void start(Stage stage) throws IOException {
        displayScene("customer-add.fxml", stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCountryCombo();
        changeCountryFilter();
        divisionComboBox.setPromptText("Please Choose a Country");
    }

    protected void setupCountryCombo() {
        ObservableList<Country> countries = CountrySQL.allCountries();
        countryComboBox.setItems(countries);
    }

    @FXML
    protected void changeCountryFilter() {
        countryComboBox.valueProperty().addListener((observable, oldCountry, newCountry) -> {
            if (newCountry != null) {
                ObservableList<Division> divisions;
                try {
                    divisions = DivisionSQL.getDivisionByCountry(newCountry.getCountryId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                divisionComboBox.setItems(divisions);
                divisionComboBox.getSelectionModel().select(0);
            }
        });
    }

    /**
     * Used to clear all text fields and enable the InHouse radio button and form. Created a default view.
     */
    void initializeCustomerForm() {
        customerIDTextField.clear();
        customerNameTextField.clear();
        addressTextField.clear();
        postalCodeTextField.clear();
        PhoneNumberTextField.clear();
        divisionComboBox.getItems().clear();
        divisionComboBox.getSelectionModel().clearSelection();
        setupCountryCombo();
    }

    void customerErrorHandling() {
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

        if (errorMessage.length() > 0) {
            Alert saveError = new Alert(Alert.AlertType.INFORMATION);
            saveError.setTitle("Unable To Save");
            saveError.setHeaderText(null);
            saveError.setContentText(errorMessage.toString());
            saveError.showAndWait();
        }

        if (errorMessage.length() == 0) {
            errorCheck = true;
        }
    }

    @FXML
    private void saveCustomerButton() {
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
        }
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

}
