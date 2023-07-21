package com.michaelpirlis.appointmentscheduler.model;

public class Division {
    private final int divisionID;
    private final String divisionName;
    private final int countryID;

    /**
     * Constructor with all required attributes.
     *
     * @param divisionID   ID
     * @param divisionName Name
     * @param countryID    Country ID
     */
    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    public int getDivisionId() {
        return divisionID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public int getCountryId() {
        return countryID;
    }

    @Override
    public String toString() {
        return this.divisionName;
    }
}
