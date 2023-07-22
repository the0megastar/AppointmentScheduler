package com.michaelpirlis.appointmentscheduler.helper;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.sql.Timestamp;
import java.time.*;

public class TimeConversions {

    // As for the convertTime method,
    // it's useful if you want to convert a UTC timestamp from your database back to the system's time zone.
    // IntelliJ suggested the return line. Thank goodness. grabs appointments and shows it in the system time
    public static ZonedDateTime convertTime(Timestamp timestamp) {
        ZonedDateTime utcZonedDateTime = timestamp.toInstant().atZone(ZoneId.of("UTC"));
        return utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
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

//    public static Timestamp createTimestamp() {
//        return Timestamp.valueOf(TimeConversions.currentZoned().toLocalDateTime());
//    }

//    public static Timestamp convertToTimestamp(ZonedDateTime zonedDateTime) {
//        return Timestamp.valueOf(zonedDateTime.toLocalDateTime());
//    }

    public static boolean businessHours(ZonedDateTime start, ZonedDateTime end) {
        // Convert appointment times to Eastern Time
        ZonedDateTime startEastern = start.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime endEastern = end.withZoneSameInstant(ZoneId.of("America/New_York"));

        // Define business hours
        LocalTime businessStart = LocalTime.of(8, 0);
        LocalTime businessEnd = LocalTime.of(22, 0);

        // Check if appointment times are within business hours
        return !startEastern.toLocalTime().isBefore(businessStart) && !endEastern.toLocalTime().isAfter(businessEnd);
    }


}




