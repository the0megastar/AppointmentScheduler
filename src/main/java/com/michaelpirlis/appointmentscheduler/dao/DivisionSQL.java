package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionSQL {

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

    public static ObservableList<Division> getDivision(int divisionId) throws SQLException {
        ObservableList<Division> getDivision = FXCollections.observableArrayList();

        String query = "SELECT * FROM first_level_divisions WHERE Division_ID = ?;";

        return getDivisions(divisionId, getDivision, query);
    }

    public static ObservableList<Division> getDivisionByCountry(int countryId) throws SQLException {
        ObservableList<Division> getDivisionByCountry = FXCollections.observableArrayList();

        String query = "SELECT * FROM first_level_divisions WHERE Country_ID = ?;";

        return getDivisions(countryId, getDivisionByCountry, query);
    }

    private static ObservableList<Division> getDivisions(int countryId, ObservableList<Division> getDivisionByCountry, String query) {
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