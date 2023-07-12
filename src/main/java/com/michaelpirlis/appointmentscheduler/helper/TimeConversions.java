package com.michaelpirlis.appointmentscheduler.helper;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeConversions {

    public ZonedDateTime convertToSystemTime(Timestamp timestamp) {
        return timestamp.toInstant().atZone(ZoneId.systemDefault());
    }

    public Timestamp convertToUTC(ZonedDateTime zonedDateTime) {
        return Timestamp.from(zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toInstant());
    }
}
