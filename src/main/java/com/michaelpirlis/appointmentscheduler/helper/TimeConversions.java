package com.michaelpirlis.appointmentscheduler.helper;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.sql.Timestamp;
import java.time.*;

public class TimeConversions {

    /**
     * Converts a UTC timestamp from the database to the system's time zone. IntelliJ suggested the return line.
     *
     * @param timestamp The UTC timestamp.
     * @return The timestamp converted to the system's time zone.
     */
    public static ZonedDateTime convertTime(Timestamp timestamp) {
        ZonedDateTime utcZonedDateTime = timestamp.toInstant().atZone(ZoneId.of("UTC"));
        return utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
    }

    /**
     * Constructs a ZonedDateTime in UTC from the hour, minute, and date, which can be saved to the database.
     * I created combo boxes to select each element. This also joins them to prepare for saving.
     *
     * @param apptHour   The hour of the appointment.
     * @param apptMinute The minute of the appointment.
     * @param apptDate   The date of the appointment.
     * @return The constructed ZonedDateTime in UTC.
     */
    public static ZonedDateTime timeCollection(ComboBox<String> apptHour,
                                               ComboBox<String> apptMinute,
                                               DatePicker apptDate) {
        int hour = Integer.parseInt(apptHour.getValue());
        int minute = Integer.parseInt(apptMinute.getValue());
        LocalDate apptDateTime = apptDate.getValue();

        LocalDateTime startDateTime = LocalDateTime.of(apptDateTime, LocalTime.of(hour, minute));

        return ZonedDateTime.of(startDateTime, ZoneId.systemDefault());
    }

    /**
     * Retrieves the current time in UTC. Zone ID of the system, then same instance of that time in the UTC zone.
     *
     * @return The current ZonedDateTime in UTC.
     */
    public static ZonedDateTime currentZoned() {
        return ZonedDateTime.now(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
    }

    /**
     * Checks if the start and end times are inside business hours (8:00 to 22:00) in Eastern Time.
     *
     * @param start The start time of the appointment.
     * @param end   The end time of the appointment.
     * @return Boolean. True if the appointment is within business hours, false if not.
     */
    public static boolean businessHours(ZonedDateTime start, ZonedDateTime end) {
        ZonedDateTime startEastern = start.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime endEastern = end.withZoneSameInstant(ZoneId.of("America/New_York"));

        LocalTime businessStart = LocalTime.of(8, 0);
        LocalTime businessEnd = LocalTime.of(22, 0);

        return !startEastern.toLocalTime().isBefore(businessStart) && !endEastern.toLocalTime().isAfter(businessEnd);
    }

}




