package com.michaelpirlis.appointmentscheduler.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Appointment {
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private final int apptId;
    private final String apptTitle;
    private final String apptDescription;
    private final String apptLocation;
    private final String apptType;
    private final String apptStart;
    private final String apptEnd;
    private final int customerId;
    private Customer customer;
    private final int userId;
    private User user;
    private int contactId;
    private final String contactName;

    public Appointment(int apptId, String apptTitle, String apptDescription, String apptLocation, String contactName,
                       String apptType, String apptStart, String apptEnd, int customerId, int userId) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.contactName = contactName;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.customerId = customerId;
        this.userId = userId;
    }

    /**
     * Collects all Appointments from the Observable List
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public int getApptId() {
        return apptId;
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

    public String getContactName() {
        return contactName;
    }

    public String getApptType() {
        return apptType;
    }

    public String getApptStart() {
        return apptStart;
    }

    public String getApptEnd() {
        return apptEnd;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public int getContactId() {
        return contactId;
    }

}