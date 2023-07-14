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
    private String customerContact;

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /** Collects all Appointments from the Observable Array List */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public Appointment(int apptId, String apptTitle, String apptDescription, String apptLocation, String customerContact,
                       String apptType, String apptStart, String apptEnd, int customerId, int userId) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.customerContact = customerContact;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.customerId = customerId;
        this.userId = userId;
    }

    public String getCustomerContact() {return customerContact;}

    public void setCustomerContact(String customerContact) {this.customerContact = customerContact;}

    public int getApptId() {return apptId;}

    public void setApptId(int apptId) {this.apptId = apptId;}

    public String getApptTitle() {return apptTitle;}

    public void setApptTitle(String apptTitle) {this.apptTitle = apptTitle;}

    public String getApptDescription() {return apptDescription;}

    public void setApptDescription(String apptDescription) {this.apptDescription = apptDescription;}

    public String getApptLocation() {return apptLocation;}

    public void setApptLocation(String apptLocation) {this.apptLocation = apptLocation;}

    public String getApptType() {return apptType;}

    public void setApptType(String apptType) {this.apptType = apptType;}

    public String getApptStart() {return apptStart;}

    public void setApptStart(String start) {this.apptStart = apptStart;}

    public String getApptEnd() {return apptEnd;}

    public void setApptEnd(String end) {this.apptEnd = apptEnd;}

    public int getCustomerId() {return customerId;}

    public void setCustomerId(int customerId) {this.customerId = customerId;}

    public Customer getCustomer() {return customer;}

    public void setCustomer(Customer customer) {this.customer = customer;}

    public int getUserId() {return userId;}

    public void setUserId(int userId) {this.userId = userId;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public int getContactId() {return contactId;}

    public void setContactId(int contactId) {this.contactId = contactId;}

}