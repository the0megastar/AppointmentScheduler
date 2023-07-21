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
//    private int countryId;


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

    public Customer(int customerID, String customerName, String customerAddress, String postalCode,
                    String phoneNumber, int divisionId) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    public int getCustomerId() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getDivision() {
        return division;
    }

    public String getCountry() {
        return country;
    }

//    public int getCountryId() { return countryId; }

    @Override
    public String toString() {
        return customerName;
    }
}


