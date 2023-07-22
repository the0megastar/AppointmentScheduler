package com.michaelpirlis.appointmentscheduler.model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public record User(int userID, String userName, String password, ZonedDateTime createDate, String createdBy,
                   Timestamp lastUpdate, String lastUpdatedBy) {

}
