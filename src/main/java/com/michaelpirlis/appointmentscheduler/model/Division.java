package com.michaelpirlis.appointmentscheduler.model;

public class Division {
    private final int divisionID;
    private final String divisionName;
    private final int countryId;

    /**
     * Constructor with all required attributes.
     *
     * @param divisionID   ID
     * @param divisionName Name
     * @param countryId    Country ID
     */
    public Division(int divisionID, String divisionName, int countryId) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    public int getDivisionId() {
        return divisionID;
    }
    public String getDivisionName() {
        return divisionName;
    }
    public int getCountryId() {
        return countryId;
    }

    @Override
    public String toString() {
        return this.divisionName;
    }
}
