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

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return userName;
    }

}
