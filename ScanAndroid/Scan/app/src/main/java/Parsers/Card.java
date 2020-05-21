package Parsers;

import java.util.HashSet;

public class Card {
  private String companyName = "";
  private String contactName = "";
  private String typeOfOrganization = "";
  private final HashSet<String> phoneNumber = new HashSet<>(); //HashSet тк эл-ты не могут повторяться
  private final HashSet<String> emails = new HashSet<>();
  private final HashSet<String> website = new HashSet<>();
  private final HashSet<String> officeAddress = new HashSet<>();

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setContactName(String companyName) {
    this.contactName = companyName;
  }

  public void setTypeOfOrganization(String typeOfOrganization) {
    this.typeOfOrganization = typeOfOrganization;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getContactName() {
    return contactName;
  }

  public String getTypeOfOrganization() {
    return typeOfOrganization;
  }

  public HashSet<String> getPhoneNumber() {
    return phoneNumber;
  }

  public HashSet<String> getEmails() {
    return emails;
  }

  public HashSet<String> getWebsite() {
    return website;
  }

  public HashSet<String> getOfficeAddress() {
    return officeAddress;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber.add(phoneNumber);
  }

  public void setEmails(String emails) {
    this.emails.add(emails);
  }

  public void setWebsite(String website) {
    this.website.add(website);
  }

  public void setOfficeAddress(String officeAddress) {
    this.officeAddress.add(officeAddress);
  }

  public void showCardInfo() {
    System.out.println("\nCompany name: \n" + companyName);
    System.out.println("\nContact name: \n" + contactName);
    System.out.println("\nCompany type: \n" + typeOfOrganization);
    System.out.println("\nCompany address: ");
    printContainer(officeAddress);
    System.out.println("\nCompany phones: ");
    printContainer(phoneNumber);
    System.out.println("\nCompany emails: ");
    printContainer(emails);
    System.out.println("\nCompany website: ");
    printContainer(website);
  }

  public void printContainer(HashSet<String> container) {
    for (String item : container) {
      System.out.println(item);
    }
  }
}

