package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerSQL {

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

    public static ObservableList<Customer> allCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        String query = "SELECT customers.*, first_level_divisions.Division, countries.Country "
                + "FROM customers "
                + "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID "
                + "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";

        return getCustomers(allCustomers, query);
    }

//    public static void createCustomer(Customer customer, String currentUser) {
//        String query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Create_Date, Created_By, Last_Updated_By) values (?,?,?,?,?,?,?,?);";
//        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
//
//        try {
//            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
//            preparedStatement.setString(1, customer.getCustomerName());
//            preparedStatement.setString(2, customer.getCustomerAddress());
//            preparedStatement.setString(3, customer.getPostalCode());
//            preparedStatement.setString(4, customer.getPhoneNumber());
//            preparedStatement.setInt(5, customer.getDivisionId());
//            preparedStatement.setTimestamp(6, currentTimestamp);  // Current date and time for Create_Date
//            preparedStatement.setString(7, loggedInUser);  // User who created the record
//            preparedStatement.setTimestamp(8, currentTimestamp);  // Current date and time for Last_Update
//            preparedStatement.setString(9, loggedInUser);  // User who last updated the record
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public static void createCustomer(Customer customer) {

        String query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) values (?,?,?,?,?);";

        try {
            PreparedStatement preparedStatement = prepareCustomer(customer, query);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static PreparedStatement prepareCustomer(Customer customer, String query) throws SQLException {
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
        preparedStatement.setString(1, customer.getCustomerName());
        preparedStatement.setString(2, customer.getCustomerAddress());
        preparedStatement.setString(3, customer.getPostalCode());
        preparedStatement.setString(4, customer.getPhoneNumber());
        preparedStatement.setInt(5, customer.getDivisionId());
        return preparedStatement;
    }

    public static void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?;";

        try {
            PreparedStatement preparedStatement = prepareCustomer(customer, query);
            preparedStatement.setInt(6, customer.getCustomerId());
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

//    public static Customer getCustomer(int customerId) {
//        String query = "SELECT * FROM customers WHERE Customer_ID = ?";
//
//        try {
//            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
//            preparedStatement.setInt(1, customerId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                return new Customer(
//                        resultSet.getInt("Customer_ID"),
//                        resultSet.getString("Customer_Name"),
//                        resultSet.getString("Address"),
//                        resultSet.getString("Postal_Code"),
//                        resultSet.getString("Phone"),
//                        resultSet.getInt("Division_ID"),
//                        resultSet.getString("Division"),
//                        resultSet.getString("Country")
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }


}