package com.michaelpirlis.appointmentscheduler.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {
    private final int countryID;
    private final String countryName;
    private final ObservableList<Division> division = FXCollections.observableArrayList();


    /**
     * Constructs a new Country object with the columns I required from the database,
     * as I did not need to add countries, no usage for other database columns.
     *
     * @param countryID
     * @param countryName
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * Retrieves the numerical identifier for the Country.
     *
     * @return The Country's ID.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Retrieves the string identifier for the Country.
     *
     * @return The Country's Name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Method to convert Object to String for ComboBoxes.
     *
     * @return Country Name
     */
    @Override
    public String toString() {
        return countryName;
    }

}

