package com.michaelpirlis.appointmentscheduler.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Appointment {
    private int apptId;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    private String apptType;
    private String apptStart;
    private String apptEnd;
    private int customerId;
    private Customer customer;
    private int userId;
    private User user;
    private int contactId;
    private String contactName;

    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /** Collects all Appointments from the Observable List */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

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

    public int getApptId() {return apptId;}
    public String getApptTitle() {return apptTitle;}
    public String getApptDescription() {return apptDescription;}
    public String getApptLocation() {return apptLocation;}
    public String getContactName() {return contactName;}
    public String getApptType() {return apptType;}
    public String getApptStart() {return apptStart;}
    public String getApptEnd() {return apptEnd;}
    public int getCustomerId() {return customerId;}
    public int getUserId() {return userId;}
    public int getContactId() {return contactId;}
    //    public Customer getCustomer() {return customer;}
    //    public User getUser() {return user;}
}