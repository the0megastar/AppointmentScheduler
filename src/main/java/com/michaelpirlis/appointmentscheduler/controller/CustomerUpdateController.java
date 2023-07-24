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


    @Override
    public void start(Stage stage) throws IOException {
        displayScene("customer-update.fxml", stage);
    }

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
     * Fetches and sets the division and country details for the customer being updated.
     *
     * <p>This method does the following:
     * <ul>
     * <li>Fetches the division associated with the customer and stores the details.</li>
     * <li>Retrieves the country ID related to the division.</li>
     * <li>Gets all divisions within the country and sets these as items in the division ComboBox.</li>
     * <li>Selects the division for the customer in the division ComboBox.</li>
     * <li>Fetches and sets the country associated with the country ID in the country ComboBox.</li>
     * </ul>
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

    private void updateCustomerImport() {
        if (updateCustomer != null) {
            customerIDTextField.setText(String.valueOf(updateCustomer.getCustomerId()));
            customerNameTextField.setText(updateCustomer.getCustomerName());
            addressTextField.setText(updateCustomer.getCustomerAddress());
            postalCodeTextField.setText(updateCustomer.getPostalCode());
            PhoneNumberTextField.setText(updateCustomer.getPhoneNumber());
        }
    }

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
