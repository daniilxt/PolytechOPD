package Parsers;

import java.util.HashSet;

public class Card {
  private long id = -1;
  private String companyName = "";
  private String firstName = "";
  private String lastName = "";
  private String fatherName = "";
  private String typeOfOrganization = "";

  private final HashSet<String> phoneNumber = new HashSet<>(); //HashSet тк эл-ты не могут повторяться
  private final HashSet<String> emails = new HashSet<>();
  private final HashSet<String> website = new HashSet<>();
  private final HashSet<String> officeAddress = new HashSet<>();

  public Card() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Card(String companyName, String firstName, String lastName, String fatherName, String typeOfOrganization) {
    this.companyName = companyName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.fatherName = fatherName;
    this.typeOfOrganization = typeOfOrganization;
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

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setTypeOfOrganization(String typeOfOrganization) {
    this.typeOfOrganization = typeOfOrganization;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getTypeOfOrganization() {
    return typeOfOrganization;
  }

  public HashSet<String> getPhoneNumbers() {
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
    System.out.println("\nContact name: \n" + firstName);
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

