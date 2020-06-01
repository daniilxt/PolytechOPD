package com.university.scan.SQL;

import java.util.HashSet;

//Record for RecyclerView
public class Record {
    private long id;
    private String companyName;
    private String firstName;
    private String lastName = "";
    private String fatherName = "";
    private String phoneNumber;
    private String emails;
    private String website;
    private String officeAddress;
    private String image;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public Record(long id, String companyName, String firstName, String lastName,
                  String fatherName, String phoneNumber, String emails, String website,
                  String officeAddress, String image) {
        this.id = id;
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.phoneNumber = phoneNumber;
        this.emails = emails;
        this.website = website;
        this.officeAddress = officeAddress;
        this.image = image;
    }

    public Record(long id, String companyName, String firstName, String lastName,
                  String fatherName, String phoneNumber, String emails, String website, String officeAddress) {
        this.companyName = companyName;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.phoneNumber = phoneNumber;
        this.emails = emails;
        this.website = website;
        this.officeAddress = officeAddress;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }
}
