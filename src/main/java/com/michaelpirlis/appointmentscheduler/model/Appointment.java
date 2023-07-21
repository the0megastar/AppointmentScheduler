package com.michaelpirlis.appointmentscheduler.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.ZonedDateTime;


public class Appointment {
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private final int apptID;
    private final String apptTitle;
    private final String apptDescription;
    private final String apptLocation;
    private final String apptType;
    private final ZonedDateTime apptStart;
    private final ZonedDateTime apptEnd;
    private final ZonedDateTime createDate;
    private final String createdBy;
    private final Timestamp lastUpdate;
    private final String lastUpdatedBy;
    private final int customerID;
    private final int userID;
    private final int contactID;


    public Appointment(int apptID, String apptTitle, String apptDescription, String apptLocation, String apptType,
                       ZonedDateTime apptStart, ZonedDateTime apptEnd, ZonedDateTime createDate, String createdBy,
                       Timestamp lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public int getApptID() {
        return apptID;
    }

    public String getApptTitle() {
        return apptTitle;
    }

    public String getApptDescription() {
        return apptDescription;
    }

    public String getApptLocation() {
        return apptLocation;
    }

    public String getApptType() {
        return apptType;
    }

    public ZonedDateTime getApptStart() {
        return apptStart;
    }

    public ZonedDateTime getApptEnd() {
        return apptEnd;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public int getContactID() {
        return contactID;
    }

}