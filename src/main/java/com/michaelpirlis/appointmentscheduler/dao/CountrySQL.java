package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CountrySQL {

    /**
     * Retrieves a list of all countries from the database.
     *
     * @return An ObservableList with all countries.
     */
    public static ObservableList<Country> allCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        String query = "SELECT * FROM countries;";

        return getCountries(allCountries, query);
    }

    /**
     * Retrieves a country by the provided Country ID.
     *
     * @param countryId The Country ID to locate.
     * @return An ObservableList with the country.
     */
    public static ObservableList<Country> getCountry(int countryId) {
        ObservableList<Country> getCountry = FXCollections.observableArrayList();

        String query = "SELECT * FROM countries WHERE Country_ID = ?;";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setInt(1, countryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Country country = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );
                getCountry.add(country);
            }
            return getCountry;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * An SQL query to retrieve a list of countries and add them to the ObservableList.
     *
     * @param countries The ObservableList to add the countries.
     * @param query     The SQL query to execute.
     * @return An ObservableList with the countries retrieved from the database.
     */
    private static ObservableList<Country> getCountries(ObservableList<Country> countries, String query) {
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Country country = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );
                countries.add(country);
            }
            return countries;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}