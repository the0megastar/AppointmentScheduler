package com.michaelpirlis.appointmentscheduler.controller;

import com.michaelpirlis.appointmentscheduler.dao.CountrySQL;
import com.michaelpirlis.appointmentscheduler.dao.CustomerSQL;
import com.michaelpirlis.appointmentscheduler.dao.DivisionSQL;
import com.michaelpirlis.appointmentscheduler.model.Country;
import com.michaelpirlis.appointmentscheduler.model.Customer;
import com.michaelpirlis.appointmentscheduler.model.Division;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.michaelpirlis.appointmentscheduler.controller.MainMenuController.displayScene;
import static com.michaelpirlis.appointmentscheduler.controller.MainMenuController.updateCustomer;


public class CustomerUpdateController extends CustomerAddController implements Initializable {

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
     * Loads the 'customer-update.fxml' scene.
     */
    @Override
    public void start(Stage stage) throws IOException {
        displayScene("customer-update.fxml", stage);
    }

    /**
     * Initializes the scene by importing the selected customer then updates the Division and Country,
     * setting up the appointment filter and table/
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCustomerImport();

        try {
            divisionCountryImport();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setupCountryCombo();
        changeCountryFilter();
    }

    /**
     * Retrieves the division linked with the customer and stores it. Uses that to get the country. Then I am able to
     * populate the combo boxes with the IDs known. Technically I load the Country and Division and use 0 to get
     * the only item in the combo boxes. Then I load the full lists. I thought this was much easier finding 1 item then
     * loading everything first.
     *
     * @throws SQLException if there's an error during the database operation.
     */
    private void divisionCountryImport() throws SQLException {
        Division selectedDivision = DivisionSQL.getDivision(updateCustomer.getDivisionId()).get(0);
        int countryId = selectedDivision.getCountryID();

        ObservableList<Division> allDivisions = DivisionSQL.getDivisionByCountry(countryId);
        divisionComboBox.setItems(allDivisions);
        divisionComboBox.getSelectionModel().select(selectedDivision);

        ObservableList<Country> countries = CountrySQL.getCountry(countryId);
        countryComboBox.getSelectionModel().select(countries.get(0));
    }

    /**
     * This method imports the details of the updateCustomer and sets the corresponding fields in the scene.
     * created and used additional methods to populate the combo boxes.
     */
    private void updateCustomerImport() {
        if (updateCustomer != null) {
            customerIDTextField.setText(String.valueOf(updateCustomer.getCustomerId()));
            customerNameTextField.setText(updateCustomer.getCustomerName());
            addressTextField.setText(updateCustomer.getCustomerAddress());
            postalCodeTextField.setText(updateCustomer.getPostalCode());
            PhoneNumberTextField.setText(updateCustomer.getPhoneNumber());
        }
    }

    /**
     * Updates the customer to the database if there are no errors.
     * Initializes the customer form and returns back to the previous scene. Customer ID used in this case to perform
     * the update in the database.
     *
     * @param event The action event.
     * @throws IOException If the previous scene file is not found.
     */
    @FXML
    private void updateCustomerButton(ActionEvent event) throws IOException {
        customerErrorHandling();

        if (errorCheck) {

            Customer customer = new Customer(
                    updateCustomer.getCustomerId(),
                    customerNameTextField.getText(),
                    addressTextField.getText(),
                    postalCodeTextField.getText(),
                    PhoneNumberTextField.getText(),
                    divisionComboBox.getValue().getDivisionId()
            );

            CustomerSQL.updateCustomer(customer);
            initializeCustomerForm();
            errorCheck = false;
            backButton(event);
        }
    }

}
