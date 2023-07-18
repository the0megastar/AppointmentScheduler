package com.michaelpirlis.appointmentscheduler.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {

    private final int countryId;
    private final String countryName;
    private final ObservableList<Division> division = FXCollections.observableArrayList();

    /**
     * Constructor with all required attributes.
     *
     * @param countryId   ID
     * @param countryName Name
     */
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    /**
     * Get the Country's first level divisions.
     *
     * @return division
     */
    public ObservableList<Division> division() {
        return division;
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

