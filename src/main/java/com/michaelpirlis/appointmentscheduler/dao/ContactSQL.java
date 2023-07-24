package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactSQL {

    /**
     * An SQL query to retrieve contacts and add them to the ObservableList.
     *
     * @param getContacts The ObservableList to add the contacts.
     * @param query       The SQL query to execute.
     * @return An ObservableList containing the contacts.
     */
    private static ObservableList<Contact> getContacts(ObservableList<Contact> getContacts, String query) {
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Contact contact = new Contact(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );
                getContacts.add(contact);
            }
            return getContacts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a list of all contacts from the database.
     *
     * @return An ObservableList with all contacts.
     */
    public static ObservableList<Contact> allContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        String query = "SELECT * FROM contacts;";

        return getContacts(allContacts, query);
    }

    /**
     * Retrieves the string identifier for the Contact.
     *
     * @param contactID The Contact ID used to find the Contact String.
     * @return The Contact's Name.
     */
    public static String getContactName(int contactID) {
        String getContactName = null;

        String query = "SELECT * FROM contacts WHERE Contact_ID = ?;";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setInt(1, contactID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("Contact_Name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
