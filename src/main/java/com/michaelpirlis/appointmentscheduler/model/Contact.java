package com.michaelpirlis.appointmentscheduler.model;

public class Contact {

    private final int contactID;
    private final String contactName;
    private final String contactEmail;


    /**
     * Constructs a new Contact object in the database.
     *
     * @param contactID
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Retrieves the numerical identifier for the Contact.
     *
     * @return The Contact's ID.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Retrieves the string identifier for the Contact.
     *
     * @return The Contact's Name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Retrieves the string identifier for the Contact's Email.
     *
     * @return The Contact's email.
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Retrieves the string identifier for the Contact.
     *
     * @return The Contact's name.
     */
    @Override
    public String toString() {
        return contactName;
    }
}
