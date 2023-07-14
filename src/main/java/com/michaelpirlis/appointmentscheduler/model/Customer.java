package com.michaelpirlis.appointmentscheduler.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String postalCode;
    private String phoneNumber;
    private int cityId;
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public Customer(int customerId, String customerName, String customerAddress, String postalCode, String phoneNumber, int cityId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.cityId = cityId;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress(String address) {
        this.customerAddress = customerAddress;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

}


