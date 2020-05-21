package com.university.scan.SQL;

import java.util.HashSet;

//Record for RecyclerView
public class Record {
    private String companyName;
    private String contactName;
    private String phoneNumber;
    private String emails;
    private String website;
    private String officeAddress;

    public Record(String companyName, String contactName, String phoneNumber, String emails, String website, String officeAddress) {
        this.companyName = companyName;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.emails = emails;
        this.website = website;
        this.officeAddress = officeAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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
