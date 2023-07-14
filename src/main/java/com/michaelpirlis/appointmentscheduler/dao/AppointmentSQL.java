package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import com.michaelpirlis.appointmentscheduler.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static com.michaelpirlis.appointmentscheduler.helper.TimeConversions.convertTime;


public class AppointmentSQL {

    public static ObservableList<Appointment> allAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String query = "SELECT appointments.*, contacts.Contact_Name "
                + "FROM appointments JOIN contacts "
                + "ON appointments.Contact_ID = contacts.Contact_ID;";

        return getAppointments(allAppointments, query);
    }

    public static ObservableList<Appointment> weeklyAppointments() {
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();

        String query = "SELECT appointments.*, contacts.Contact_Name "
                + "FROM appointments JOIN contacts "
                + "ON appointments.Contact_ID = contacts.Contact_ID "
                + "WHERE appointments.Start >= NOW() AND appointments.Start < DATE_ADD(NOW(), INTERVAL 1 WEEK);";

        return getAppointments(weeklyAppointments, query);
    }

    public static ObservableList<Appointment> monthlyAppointments() {
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

        String query = "SELECT appointments.*, contacts.Contact_Name "
            + "FROM appointments JOIN contacts "
            + "ON appointments.Contact_ID = contacts.Contact_ID "
            + "WHERE appointments.Start >= NOW() AND appointments.Start < DATE_ADD(NOW(), INTERVAL 1 MONTH);";

        return getAppointments(monthlyAppointments, query);
    }

    private static ObservableList<Appointment> getAppointments(ObservableList<Appointment> weeklyAppointments, String query) {
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Timestamp startTimestamp = resultSet.getTimestamp("Start");
                Timestamp endTimestamp = resultSet.getTimestamp("End");

                String startDateTime = convertTime(startTimestamp);
                String endDateTime = convertTime(endTimestamp);

                Appointment appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        startDateTime,
                        endDateTime,
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID")
                );
                weeklyAppointments.add(appointment);
            }
            return weeklyAppointments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//                allAppointments.add(createAppointment(resultSet));
//            }
//            return allAppointments;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public Appointment createAppointment(int apptId) {
//        String query = "SELECT * FROM appointments WHERE Appointment_ID = " + apptId + ";";
//        try {
//            ResultSet resultSet = JDBC.openConnection().createStatement().executeQuery(query);
//            resultSet.next();
//            return createAppointment(resultSet);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//        private Appointment createAppointment (ResultSet resultSet) throws SQLException {
//            int id = resultSet.getInt("Appointment_ID");
//            String title = resultSet.getString("Title");
//            String description = resultSet.getString("Description");
//            String location = resultSet.getString("Location");
//            int contact = resultSet.getInt("Contact");
//            String type = resultSet.getString("Type");
//            String start = resultSet.getString("Start");
//            String end = resultSet.getString("End");
//            int customerId = resultSet.getInt("Customer_ID");
//            int userId = resultSet.getInt("User_ID");
//            ZonedDateTime startUTC = DateTimeConv.strToDateUTC(start);
//            ZonedDateTime endUTC = DateTimeConv.strToDateUTC(end);
//            Appointment appointment = new Appointment(id, title, description, location, contact, type, start, end, customerId, userId);
//            return appointment;
//        }
//
//    }
}
