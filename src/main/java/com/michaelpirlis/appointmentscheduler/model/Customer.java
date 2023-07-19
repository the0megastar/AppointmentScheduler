package com.michaelpirlis.appointmentscheduler.model;

public class Customer {
    private final String customerName;
    private final String customerAddress;
    private final String postalCode;
    private final String phoneNumber;
    private final int divisionId;
    private int customerId;
    private String division;
    private String country;
    private int countryId;


    public Customer(int customerId, String customerName, String customerAddress, String postalCode,
                    String phoneNumber, int divisionId, String division, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.division = division;
        this.country = country;
    }

    public Customer(int customerId, String customerName, String customerAddress, String postalCode,
                    String phoneNumber, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    public int getCustomerId() {
        return customerId;
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

    public int getCountryId() { return countryId; }
}


