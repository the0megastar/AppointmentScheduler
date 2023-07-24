package com.michaelpirlis.appointmentscheduler.model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public final class User {
    private final int userID;
    private final String userName;
    private final String password;
    private final ZonedDateTime createDate;
    private final String createdBy;
    private final Timestamp lastUpdate;
    private final String lastUpdatedBy;

    /**
     * Constructs a new User object with the columns from the database.
     *
     * @param userID
     * @param userName
     * @param password
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     */
    public User(int userID, String userName, String password, ZonedDateTime createDate, String createdBy,
                Timestamp lastUpdate, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Retrieves the numerical identifier for the User.
     *
     * @return The User's ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Retrieves the string identifier for the User.
     *
     * @return The User's name.
     */
    public String getUsername() {
        return userName;
    }

}
