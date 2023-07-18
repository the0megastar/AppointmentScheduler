package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CountrySQL {

    public static ObservableList<Country> allCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        String query = "SELECT * FROM countries;";

        return getCountries(allCountries, query);
    }

//    public static ObservableList<Country> getCountry(int countryId) {
//        ObservableList<Country> getCountry = FXCollections.observableArrayList();
//
//        String query = "SELECT * FROM countries WHERE Country_ID = ?;";
//
//        return getCountries(getCountry, query);
//    }

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