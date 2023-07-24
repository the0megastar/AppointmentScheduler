package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionSQL {

    /**
     * An SQL query to retrieve all divisions and add them to an ObservableList.
     *
     * @return An ObservableList with all divisions.
     */
    private static ObservableList<Division> allDivisions() {
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();

        String query = "SELECT * FROM first_level_divisions WHERE Division_ID = ?;";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Division division = new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Country_ID")
                );
                allDivisions.add(division);
            }
            return allDivisions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a list of divisions from the provided division ID.
     *
     * @param divisionId The ID of the division to retrieve.
     * @return An ObservableList with the division matching the ID.
     */
    public static ObservableList<Division> getDivision(int divisionId) throws SQLException {
        ObservableList<Division> getDivision = FXCollections.observableArrayList();

        String query = "SELECT * FROM first_level_divisions WHERE Division_ID = ?;";

        return getDivisions(divisionId, getDivision, query);
    }

    /**
     * Retrieves divisions from the database based on the country ID.
     *
     * @param countryId The ID of the country to find all the divisions.
     * @return An ObservableList with the divisions located in the country.
     */
    public static ObservableList<Division> getDivisionByCountry(int countryId) throws SQLException {
        ObservableList<Division> getDivisionByCountry = FXCollections.observableArrayList();

        String query = "SELECT * FROM first_level_divisions WHERE Country_ID = ?;";

        return getDivisions(countryId, getDivisionByCountry, query);
    }

    /**
     * Retrieves a list of divisions from the database based on the country ID.
     *
     * @param countryId The ID of the country to find the divisions.
     * @param getDivisionByCountry The ObservableList to store the divisions.
     * @param query The SQL query to execute.
     * @return An ObservableList with the divisions located in the country.
     */
    private static ObservableList<Division> getDivisions(int countryId,
                                                         ObservableList<Division> getDivisionByCountry,
                                                         String query) {
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setInt(1, countryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Division division = new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Country_ID")
                );
                getDivisionByCountry.add(division);
            }
            return getDivisionByCountry;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}