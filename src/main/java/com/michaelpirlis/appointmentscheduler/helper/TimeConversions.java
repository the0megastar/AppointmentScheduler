package com.michaelpirlis.appointmentscheduler.helper;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConversions {

    public static String convertTime(Timestamp timestamp) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        ZonedDateTime utcZonedDateTime = timestamp.toInstant().atZone(ZoneId.of("UTC"));
        ZonedDateTime systemZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return systemZonedDateTime.format(formatter);
    }

}




