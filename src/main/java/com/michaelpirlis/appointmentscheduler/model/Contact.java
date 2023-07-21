package com.michaelpirlis.appointmentscheduler.model;

public class Contact {

    private final int contactID;
    private final String contactName;
    private final String contactEmail;


    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    @Override
    public String toString() {
        return contactName;
    }
}
