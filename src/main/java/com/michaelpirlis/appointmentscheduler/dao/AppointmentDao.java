package com.michaelpirlis.appointmentscheduler.dao;

import com.michaelpirlis.appointmentscheduler.helper.JDBC ;
import com.michaelpirlis.appointmentscheduler.helper.TimeConversions;
import com.michaelpirlis.appointmentscheduler.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.ZonedDateTime;


public class AppointmentDao {


    public static ObservableList<Appointment> allAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String query = "SELECT appointments.*, contacts.Contact_Name FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID;";

//        String query = "SELECT * FROM appointments;";
        TimeConversions timeConversions = new TimeConversions();

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ZonedDateTime startZonedDate = timeConversions.convertToSystemTime(resultSet.getTimestamp("Start"));
                ZonedDateTime endZonedDate = timeConversions.convertToSystemTime(resultSet.getTimestamp("End"));

                Appointment appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        startZonedDate,
                        endZonedDate,
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID")
                );
                allAppointments.add(appointment);
            }
            return allAppointments;
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
