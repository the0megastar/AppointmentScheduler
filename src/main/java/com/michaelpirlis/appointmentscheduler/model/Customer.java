package com.michaelpirlis.appointmentscheduler.model;

public class Customer {
    private final String customerName;
    private final String customerAddress;
    private final String postalCode;
    private final String phoneNumber;
    private final int divisionId;
    private final int customerID;
    private String division;
    private String country;


    /**
     * Overloaded constructor when I identified I needed addition parameters for updating Customers in the database,
     * to save myself from having to rewrite a majority of th codebase for customers.
     *
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param postalCode
     * @param phoneNumber
     * @param divisionId
     * @param division
     * @param country
     */
    public Customer(int customerID, String customerName, String customerAddress, String postalCode,
                    String phoneNumber, int divisionId, String division, String country) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.division = division;
        this.country = country;
    }

    /**
     * Constructs a new Customer object used to create a new customer in the database.
     *
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param postalCode
     * @param phoneNumber
     * @param divisionId
     */
    public Customer(int customerID, String customerName, String customerAddress, String postalCode,
                    String phoneNumber, int divisionId) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    /**
     * Prints the customer object, I used it for debugging saving and updating customers before saving,
     * to locate time discrepancies, invalid values, null values, and test errorChecking methods.
     */
    public void printCustomer() {
        System.out.println("Customer ID: " + this.customerID);
        System.out.println("Customer Name: " + this.customerName);
        System.out.println("Customer Address: " + this.customerAddress);
        System.out.println("Postal Code: " + this.postalCode);
        System.out.println("Phone Number: " + this.phoneNumber);
        System.out.println("Division ID: " + this.divisionId);
    }

    /**
     * Retrieves the numerical identifier for the Customer.
     *
     * @return The Customer's ID.
     */
    public int getCustomerId() {
        return customerID;
    }

    /**
     * Retrieves the string identifier for the Customer.
     *
     * @return The Customer's name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Retrieves the string Address for the Customer.
     *
     * @return The Customer's address.
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Retrieves the string Postal Code for the Customer.
     *
     * @return The Customer's postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Retrieves the string Phone Number for the Customer.
     *
     * @return The Customer's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Retrieves the numerical identifier for the Customer division.
     *
     * @return The Customer's Division ID.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Retrieves the string identifier for the Customer's division.
     *
     * @return The Customer's Division Name.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Retrieves the string Country for the Customer.
     *
     * @return The Customer's country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Retrieves the string identifier for the Customer.
     *
     * @return The Customer's's name.
     */
    @Override
    public String toString() {
        return customerName;
    }
}


