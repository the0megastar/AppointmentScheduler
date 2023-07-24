package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

import static com.michaelpirlis.appointmentscheduler.helper.TimeConversions.convertTime;


public class UserSQL {

    /**
     * Retrieves a list of all users from the database.
     *
     * @return An ObservableList containing all users.
     */
    public static ObservableList<User> allUsers() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        String query = "SELECT * FROM users";

        return getUsers(allUsers, query);
    }

    /**
     * An SQL query to retrieve a list of users and adds them to the ObservableList.
     *
     * @param getUsers The ObservableList adds the Users.
     * @param query    The SQL query to execute.
     * @return An ObservableList with the retrieved users.
     */
    private static ObservableList<User> getUsers(ObservableList<User> getUsers, String query) {
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Timestamp createDateTimestamp = resultSet.getTimestamp("Create_Date");
                Timestamp lastUpdateTimestamp = resultSet.getTimestamp("Last_Update");
                ZonedDateTime createDate = convertTime(createDateTimestamp);

                User user = new User(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password"),
                        createDate,
                        resultSet.getString("Created_By"),
                        lastUpdateTimestamp,
                        resultSet.getString("Last_Updated_By")
                );
                getUsers.add(user);
            }
            return getUsers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the string identifier for the User.
     *
     * @param username The username to retrieve.
     * @return @return The User's name or null.
     */
    public static User getUser(String username) {
        String query = "SELECT * FROM users WHERE User_Name = ?";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Timestamp createDateTimestamp = resultSet.getTimestamp("Create_Date");
                ZonedDateTime createDate = convertTime(createDateTimestamp);
                Timestamp lastUpdateTimestamp = resultSet.getTimestamp("Last_Update");

                return new User(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password"),
                        createDate,
                        resultSet.getString("Created_By"),
                        lastUpdateTimestamp,
                        resultSet.getString("Last_Updated_By")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Checks if a user with the username and password is located in the database.
     *
     * @param username The username to check.
     * @param password The password to check.
     * @return True if a user is located, false if not.
     */
    public static boolean loginCheck(String username, String password) {
        String query = "SELECT * FROM users WHERE User_Name=? AND Password=?";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
