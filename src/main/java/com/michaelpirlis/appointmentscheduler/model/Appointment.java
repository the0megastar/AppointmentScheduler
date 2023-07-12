package com.michaelpirlis.appointmentscheduler.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.ZonedDateTime;

public class Appointment {
    private int apptId;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    private String apptType;
    private ZonedDateTime apptStart;
    private ZonedDateTime apptEnd;
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
                       String apptType, ZonedDateTime apptStart, ZonedDateTime apptEnd, int customerId, int userId) {
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

    public ZonedDateTime getApptStart() {return apptStart;}

    public void setApptStart(ZonedDateTime start) {this.apptStart = apptStart;}

    public ZonedDateTime getApptEnd() {return apptEnd;}

    public void setApptEnd(ZonedDateTime end) {this.apptEnd = apptEnd;}

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

//    public Contact getContact() {return contact;}
//
//    public void setContact(Contact contact) {this.contact = contact;}
}