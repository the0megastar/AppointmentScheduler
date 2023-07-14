package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
}