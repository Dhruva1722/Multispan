package com.example.multispan;

public class EmployeeInfo {
    private String employeeName;

    private String employeeContactNumber;


    private String employeeAddress;

    public String getEmployeeImageURL() {
        return employeeImageURL;
    }

    public void setEmployeeImageURL(String employeeImageURL) {
        this.employeeImageURL = employeeImageURL;
    }

    private String employeeImageURL;

    public EmployeeInfo() {

    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeContactNumber() {
        return employeeContactNumber;
    }

    public void setEmployeeContactNumber(String employeeContactNumber) {
        this.employeeContactNumber = employeeContactNumber;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }


}
