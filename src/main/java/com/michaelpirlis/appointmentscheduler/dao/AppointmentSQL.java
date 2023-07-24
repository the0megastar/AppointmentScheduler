package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
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

    public static ObservableList<Appointment> contactAppointments(int contactID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String query = "SELECT * FROM Appointments WHERE Contact_ID = " + contactID;

        try {
            appointments = getAppointments(appointments, query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return appointments;
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

    public static String appointmentOverlap(int apptID, ZonedDateTime start, ZonedDateTime end) {
        String query = "SELECT * FROM appointments WHERE ((Start BETWEEN ? AND ?) OR (End BETWEEN ? AND ?) "
                + "OR (? BETWEEN Start AND End) OR (? BETWEEN Start AND End)) AND Appointment_ID != ?";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, Timestamp.from(start.toInstant()));
            preparedStatement.setTimestamp(2, Timestamp.from(end.toInstant()));
            preparedStatement.setTimestamp(3, Timestamp.from(start.toInstant()));
            preparedStatement.setTimestamp(4, Timestamp.from(end.toInstant()));
            preparedStatement.setTimestamp(5, Timestamp.from(start.toInstant()));
            preparedStatement.setTimestamp(6, Timestamp.from(end.toInstant()));
            preparedStatement.setInt(7, apptID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return "Appointment times overlap with an existing appointment.";
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
            preparedStatement.setTimestamp(7, Timestamp.valueOf(appointment.getLastUpdate().toLocalDateTime()));
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

    public static Appointment upcomingAppointment() {
        String query = "SELECT * FROM appointments WHERE Start BETWEEN NOW() AND NOW() + INTERVAL 15 MINUTE;";
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int apptID = resultSet.getInt("Appointment_ID");
                String apptTitle = resultSet.getString("Title");
                String apptDescription = resultSet.getString("Description");
                String apptLocation = resultSet.getString("Location");
                String apptType = resultSet.getString("Type");
                ZonedDateTime apptStart = resultSet.getTimestamp("Start").toInstant().atZone(ZoneId.systemDefault());
                ZonedDateTime apptEnd = resultSet.getTimestamp("End").toInstant().atZone(ZoneId.systemDefault());
                ZonedDateTime createDate = resultSet.getTimestamp("Create_Date").toInstant().atZone(ZoneId.systemDefault());
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");

                return new Appointment(apptID, apptTitle, apptDescription, apptLocation, apptType,
                        apptStart, apptEnd, createDate, createdBy, lastUpdate,
                        lastUpdatedBy, customerID, userID, contactID);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static int appointmentMonthType(String type, String month) {
        String query = "SELECT COUNT(*) FROM appointments WHERE Type = ? AND MONTHNAME(Start) = ?;";
        int count = 0;

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, month);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }



}
