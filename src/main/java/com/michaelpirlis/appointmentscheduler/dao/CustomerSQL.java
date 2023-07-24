package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static com.michaelpirlis.appointmentscheduler.controller.LoginController.currentUsername;


public class CustomerSQL {

    public static ObservableList<Customer> allCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        String query = "SELECT customers.*, first_level_divisions.Division, countries.Country "
                + "FROM customers "
                + "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID "
                + "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";

        return getCustomers(allCustomers, query);
    }

    private static ObservableList<Customer> getCustomers(ObservableList<Customer> getCustomers, String query) {
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Customer customer = new Customer(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getString("Country")
                );
                getCustomers.add(customer);
            }
            return getCustomers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createCustomer(Customer customer) {
        String query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Create_Date,"
                + "Created_By, Last_Update, Last_Updated_By) values (?,?,?,?,?,?,?,?,?);";

        Timestamp lastUpdate = Timestamp.from(Instant.now());

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getCustomerAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhoneNumber());
            preparedStatement.setInt(5, customer.getDivisionId());
            preparedStatement.setTimestamp(6, lastUpdate);
            preparedStatement.setString(7, currentUsername);
            preparedStatement.setTimestamp(8, lastUpdate);
            preparedStatement.setString(9, currentUsername);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?,"
                + "Division_ID = ?, Last_Update = ?, Last_Updated_By = ? WHERE Customer_ID = ?;";

        Timestamp lastUpdate = Timestamp.from(Instant.now());

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getCustomerAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhoneNumber());
            preparedStatement.setInt(5, customer.getDivisionId());
            preparedStatement.setTimestamp(6, lastUpdate);
            preparedStatement.setString(7, currentUsername);
            preparedStatement.setInt(8, customer.getCustomerId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteCustomer(int customerId) {
        String query = "DELETE FROM customers WHERE Customer_ID = ?;";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getCustomerCountry(String country) {
        String query = "SELECT COUNT(*) FROM customers "
                + "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID "
                + "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID "
                + "WHERE countries.Country = ?;";
        int count = 0;

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, country);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }


}