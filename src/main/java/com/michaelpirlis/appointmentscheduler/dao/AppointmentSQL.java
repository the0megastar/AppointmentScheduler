package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

import static com.michaelpirlis.appointmentscheduler.helper.TimeConversions.convertTime;


public class AppointmentSQL {

    private static ObservableList<Appointment> getAppointments(ObservableList<Appointment> appointments, String query) {
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Timestamp startTimestamp = resultSet.getTimestamp("Start");
                Timestamp endTimestamp = resultSet.getTimestamp("End");
                Timestamp createDateTimestamp = resultSet.getTimestamp("Create_Date");
                Timestamp lastUpdateTimestamp = resultSet.getTimestamp("Last_Update");

                ZonedDateTime startDateTime = convertTime(startTimestamp);
                ZonedDateTime endDateTime = convertTime(endTimestamp);
                ZonedDateTime createDate = convertTime(createDateTimestamp);
//                ZonedDateTime startDateTime = (startTimestamp != null) ? convertTime(startTimestamp) : null;
//                ZonedDateTime endDateTime = (endTimestamp != null) ? convertTime(endTimestamp) : null;
//                ZonedDateTime createDate = (createDateTimestamp != null) ? convertTime(createDateTimestamp) : null;

                Appointment appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        startDateTime,
                        endDateTime,
                        createDate,
                        resultSet.getString("Created_By"),
                        lastUpdateTimestamp,
                        resultSet.getString("Last_Updated_By"),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID")
                );
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<Appointment> allAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String query = "SELECT * FROM appointments;";

        return getAppointments(allAppointments, query);
    }

    public static ObservableList<Appointment> weeklyAppointments() {
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();

        String query = "SELECT * FROM appointments "
                + "WHERE appointments.Start BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY);";

        return getAppointments(weeklyAppointments, query);
    }

    public static ObservableList<Appointment> monthlyAppointments() {
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

        String query = "SELECT * FROM appointments "
                + "WHERE MONTH(appointments.Start) = MONTH(NOW()) "
                + "AND YEAR(appointments.Start) = YEAR(NOW());";

        return getAppointments(monthlyAppointments, query);
    }

    public static void deleteAppointments(int appointmentID) {
        String query = "DELETE FROM appointments WHERE Appointment_ID = ?;";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setInt(1, appointmentID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteCustomerAppointments(int customerID) {
        String query = "DELETE FROM appointments WHERE Customer_ID = ?;";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String appointmentOverlap(int customerID, ZonedDateTime start, ZonedDateTime end) {
        String query = "SELECT * FROM appointments WHERE Customer_ID = ? "
                + "AND ((Start <= ? AND End > ?) OR (End > ? AND Start < ?) OR (Start < ? AND End >= ?))";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            preparedStatement.setTimestamp(2, Timestamp.from(start.toInstant()));
            preparedStatement.setTimestamp(3, Timestamp.from(end.toInstant()));
            preparedStatement.setTimestamp(4, Timestamp.from(start.toInstant()));
            preparedStatement.setTimestamp(5, Timestamp.from(end.toInstant()));
            preparedStatement.setTimestamp(6, Timestamp.from(start.toInstant()));
            preparedStatement.setTimestamp(7, Timestamp.from(end.toInstant()));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return "Appointment times overlap with an existing appointment for this customer.";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * PreparedStatement.setTimestamp() expects a Timestamp, not a ZonedDateTime.
     * To convert a ZonedDateTime to a Timestamp, you first need to convert it to a LocalDateTime,
     * which also represents a point on the timeline but without any time zone information.
     * So, when you call toLocalDateTime() on a ZonedDateTime, it essentially strips off the time zone information,
     * leaving you with a LocalDateTime. You can then convert that LocalDateTime to a Timestamp using Timestamp.valueOf().
     */
    public static void createAppointment(Appointment appointment) {
        String query = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, "
                + "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, appointment.getApptTitle());
            preparedStatement.setString(2, appointment.getApptDescription());
            preparedStatement.setString(3, appointment.getApptLocation());
            preparedStatement.setString(4, appointment.getApptType());
            preparedStatement.setTimestamp(5, Timestamp.from(appointment.getApptStart().toInstant()));
            preparedStatement.setTimestamp(6, Timestamp.from(appointment.getApptEnd().toInstant()));
            preparedStatement.setTimestamp(7, Timestamp.from(appointment.getCreateDate().toInstant()));
            preparedStatement.setString(8, appointment.getCreatedBy());
            preparedStatement.setTimestamp(9, Timestamp.valueOf(appointment.getLastUpdate().toLocalDateTime()));
            preparedStatement.setString(10, appointment.getLastUpdatedBy());
            preparedStatement.setInt(11, appointment.getCustomerID());
            preparedStatement.setInt(12, appointment.getUserID());
            preparedStatement.setInt(13, appointment.getContactID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateAppointment(Appointment appointment) {
        String query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, "
                + "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? "
                + "WHERE Appointment_ID = ?;";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, appointment.getApptTitle());
            preparedStatement.setString(2, appointment.getApptDescription());
            preparedStatement.setString(3, appointment.getApptLocation());
            preparedStatement.setString(4, appointment.getApptType());
            preparedStatement.setTimestamp(5, Timestamp.from(appointment.getApptStart().toInstant()));
            preparedStatement.setTimestamp(6, Timestamp.from(appointment.getApptEnd().toInstant()));
            preparedStatement.setTimestamp(7, Timestamp.from(appointment.getCreateDate().toInstant()));
            preparedStatement.setString(8, appointment.getLastUpdatedBy());
            preparedStatement.setInt(9, appointment.getCustomerID());
            preparedStatement.setInt(10, appointment.getUserID());
            preparedStatement.setInt(11, appointment.getContactID());
            preparedStatement.setInt(12, appointment.getApptID());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
