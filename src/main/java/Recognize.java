import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recognize {
  private ArrayList<String> container = new ArrayList<>();
  private ArrayList<String> text = new ArrayList<>();
  private ArrayList<Card> visitCardsArray = new ArrayList<>();

  public void textParse(String[] in) {
    Card visitCard = new Card();
    for (String str : in) {
      parsePhoneNumbers(visitCard, str);
      parseWebsite(visitCard, str);
      parseEmail(visitCard, str);
    }

    visitCardsArray.add(visitCard);
    System.out.println("\n\n\n\n");
    printCard();
    System.out.println(visitCardsArray.size());
  }

  private void parsePhoneNumbers(Card visitCard, String str) {
    String REGEX = "((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}";
    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(str);
    if (matcher.find()) {
      visitCard.setPhoneNumber(toPhoneFormat(matcher.group(0)));
      while (matcher.find()) {
        visitCard.setPhoneNumber(toPhoneFormat(str.substring(matcher.start(), matcher.end())));
      }
    }
  }

  private void parseWebsite(Card visitCard, String str) {
    String REGEX = "(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?";
    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(str);
    if (matcher.find()) {
      visitCard.setWebsite(matcher.group(0));
      while (matcher.find()) {
        visitCard.setWebsite(str.substring(matcher.start(), matcher.end()));
      }
    }
  }

  private void parseEmail(Card visitCard, String str) {
    String REGEX = "([a-z0-9\\_\\-]+\\.)*[a-z0-9\\_\\-]+@[a-z0-9\\_\\-]+(\\.[a-z0-9\\_\\-]+)*\\.[a-z]{2,6}";
    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(str);
    if (matcher.find()) {
      visitCard.setEmails(matcher.group(0));
      while (matcher.find()) {
        visitCard.setEmails(str.substring(matcher.start(), matcher.end()));
      }
    }
  }

  private String toPhoneFormat(String str) {
    String tmp = str.replace('-', ' ');
    tmp = tmp.replaceAll("\\s", "");
    String telephoneNumber;
    if (tmp.length() > 8) {
      if (tmp.charAt(0) == '+') {
        telephoneNumber = tmp.substring(0, 2) + "(" + tmp.substring(2, 5) + ")" + tmp.substring(5, 8) + "-" + tmp.substring(8, 10) + "-" + tmp.substring(10);
        System.out.println(telephoneNumber);
        return telephoneNumber;
      }
    }
    return "";
  }

  private void printCard() {
    for (Card card : visitCardsArray) {
      card.showCardInfo();
    }
  }
}

