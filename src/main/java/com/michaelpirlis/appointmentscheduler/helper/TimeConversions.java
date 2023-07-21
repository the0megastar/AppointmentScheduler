package com.michaelpirlis.appointmentscheduler.helper;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.sql.Timestamp;
import java.time.*;

public class TimeConversions {

    // As for the convertTime method,
    // it's useful if you want to convert a UTC timestamp from your database back to the system's time zone.
    public static ZonedDateTime convertTime(Timestamp timestamp) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        ZonedDateTime utcZonedDateTime = timestamp.toInstant().atZone(ZoneId.of("UTC"));
        ZonedDateTime systemZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
//        return systemZonedDateTime.format(formatter);
        return systemZonedDateTime;
    }

    // this method will return a ZonedDateTime in UTC, which you can save to your database.
    public static ZonedDateTime timeCollection(ComboBox<String> apptHour,
                                               ComboBox<String> apptMinute,
                                               DatePicker apptDate) {
        int hour = Integer.parseInt(apptHour.getValue());
        int minute = Integer.parseInt(apptMinute.getValue());
        LocalDate apptDateTime = apptDate.getValue();

        LocalDateTime startDateTime = LocalDateTime.of(apptDateTime, LocalTime.of(hour, minute));
        ZonedDateTime zonedApptTime = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());

        return zonedApptTime.withZoneSameInstant(ZoneId.of("UTC"));
    }

    public static ZonedDateTime currentZoned() {
        return ZonedDateTime.now(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
    }

    public static Timestamp createTimestamp() {
        return Timestamp.valueOf(TimeConversions.currentZoned().toLocalDateTime());
    }



}




