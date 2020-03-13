import java.util.ArrayList;
import java.util.HashSet;

public class Card {
  private String companyName = "";
  private HashSet<String> phoneNumber = new HashSet<>(); //HashSet тк эл-ты не могут повторяться
  private HashSet<String> emails = new HashSet<>();
  private HashSet<String> website = new HashSet<>();
  private HashSet<String> officeAddress = new HashSet<>();

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
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
    System.out.println("\nCompany name: " + companyName);
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
