package com.michaelpirlis.appointmentscheduler.model;

public class Division {
    private final int divisionID;
    private final String divisionName;
    private final int countryID;


    /**
     * Constructs a new Division object with the columns I required from the database.
     *
     * @param divisionID
     * @param divisionName
     * @param countryID
     */
    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * Retrieves the numerical identifier for the Division.
     *
     * @return The Division's ID.
     */
    public int getDivisionId() {
        return divisionID;
    }

    /**
     * Retrieves the name of the Division.
     *
     * @return The Division's name.
     */
    public String getDivisionName() {
        return divisionName;
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
     * Retrieves the string of the Division.
     *
     * @return The Division's name.
     */
    @Override
    public String toString() {
        return this.divisionName;
    }
}
