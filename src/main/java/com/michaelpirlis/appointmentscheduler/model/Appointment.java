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


    /**
     * Constructs a new Appointment object used to create a new appointment in the database.
     * I recreated the constructor to include all database columns causing me to rewrite/refactor a majority of project
     * code, which led to me overloading the Customer constructors in the future.
     *
     * @param apptID
     * @param apptTitle
     * @param apptDescription
     * @param apptLocation
     * @param apptType
     * @param apptStart
     * @param apptEnd
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     */
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

    /**
     * Retrieves a list of all appointments. This is useful for displaying all appointments in the user interface.
     *
     * @return An ObservableList containing all appointments.
     */

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /**
     * Prints the Appointment object, I used it for debugging saving and updating customers before saving,
     * to locate time discrepancies, invalid values, null values, and test errorChecking methods.
     * Similar to the Customer print methods.
     */
    public void printAppointment() {
        System.out.println("Appointment ID: " + this.apptID);
        System.out.println("Title: " + this.apptTitle);
        System.out.println("Description: " + this.apptDescription);
        System.out.println("Location: " + this.apptLocation);
        System.out.println("Type: " + this.apptType);
        System.out.println("Start: " + this.apptStart);
        System.out.println("End: " + this.apptEnd);
        System.out.println("Created Date: " + this.createDate);
        System.out.println("Created By: " + this.createdBy);
        System.out.println("Last Update: " + this.lastUpdate);
        System.out.println("Last Updated By: " + this.lastUpdatedBy);
        System.out.println("Customer ID: " + this.customerID);
        System.out.println("User ID: " + this.userID);
        System.out.println("Contact ID: " + this.contactID);
    }

    /**
     * Retrieves the numerical identifier for the appointment.
     *
     * @return The Appointment's ID.
     */
    public int getApptID() {
        return apptID;
    }

    /**
     * Retrieves the string title for the appointment.
     *
     * @return The Appointment's Title.
     */
    public String getApptTitle() {
        return apptTitle;
    }

    /**
     * Retrieves the string description of the appointment.
     *
     * @return The Appointment's Description.
     */
    public String getApptDescription() {
        return apptDescription;
    }

    /**
     * Retrieves the string location of the appointment.
     *
     * @return The Appointment's Location.
     */
    public String getApptLocation() {
        return apptLocation;
    }

    /**
     * Retrieves the string type of the appointment.
     *
     * @return The Appointment's Type.
     */
    public String getApptType() {
        return apptType;
    }

    /**
     * Retrieves the Zoned Date Time of the appointment.
     *
     * @return The Appointment's Start Time.
     */
    public ZonedDateTime getApptStart() {
        return apptStart;
    }

    /**
     * Retrieves the Zoned Date Time of the appointment.
     *
     * @return The Appointment's End Time.
     */
    public ZonedDateTime getApptEnd() {
        return apptEnd;
    }

    /**
     * Retrieves the Zoned Date Creation Time of the appointment.
     *
     * @return The Appointment's Creation Time.
     */
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Retrieves the string of the User who created the appointment.
     *
     * @return The Appointment's Creator.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Retrieves the Timestamp of the last update for the appointment.
     *
     * @return The Appointment's Last Update Timestamp.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Retrieves the string of the User who last updated the appointment.
     *
     * @return The Appointment's Last Updated User.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Retrieves the numerical identifier for the Customer.
     *
     * @return The Customer's ID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Retrieves the numerical identifier for the User.
     *
     * @return The Customer's User ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Retrieves the numerical identifier for the Contact.
     *
     * @return The Customer's Contact ID.
     */
    public int getContactID() {
        return contactID;
    }

}