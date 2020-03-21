import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recognize {
  private ArrayList<String> container = new ArrayList<>();
  private ArrayList<String> text = new ArrayList<>();
  private ArrayList<Card> visitCardsArray = new ArrayList<>();
  private String addres;

  public void textParse(String[] in) {
    Card visitCard = new Card();
    for (String str : in) {
      parsePhoneNumbers(visitCard, str);
      parseWebsite(visitCard, str);
      parseEmail(visitCard, str);
      parseTypeOfOrganization(visitCard, str);
    }
    for (String str : in) {
      addres += str;
    }
    System.out.println(addres);
    parseAddress(visitCard, addres);

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
    //String REGEX = "(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?";
    String REGEX = "(?:(?:https?|ftp|telnet)://(?:[a-z0-9_-]{1,32}(?::[a-z0-9_-]{1,32})?@)?)?(?:(?:[a-z0-9-]{1,128}\\.)+(?:ru|su|com|net|org|mil|edu|arpa|gov|biz|info|aero|inc|name|[a-z]{2})|(?!0)(?:(?!0[^.]|255)[0-9]{1,3}\\.){3}(?!0|255)[0-9]{1,3})(?:/[a-z0-9.,_@%&?+=~/-]*)?(?:#[^ '\"&]*)?";
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
    String REGEX = "([a-z0-9\\_\\-]+\\.)*[a-z0-9\\_\\-]+.@[a-z0-9\\_\\-]+(\\.[a-z0-9\\_\\-]+)*\\.[a-z]{2,6}";
    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(str);
    if (matcher.find()) {
      visitCard.setEmails(matcher.group(0).replaceAll("\\s", ""));
      while (matcher.find()) {
        visitCard.setEmails(str.substring(matcher.start(), matcher.end()).replaceAll("\\s", ""));
      }
    }
  }

  private void parseAddress(Card visitCard, String str) {
    // не работает для зеркального адреса
    //String REGEX = "((((ул\\.|улица|проспект|пр\\-кт\\.)[\\s]?)([а-яА-Я]{3,20}\\,?))|((([а-яА-Я]{3,20}\\,?))\\s?((ул\\.|улица|проспект|пр\\-кт\\.)[\\s,]?))).(((дом|д\\.)?[0-9]{1,3})\\,?)\\s?((корп\\.|корпус)[0-9]{1,3})?";
    String REGEX = "(((((ул\\.|улица|проспект|пр\\-кт\\.)[\\s]?)([а-яА-Я]{3,20}\\,?))\\s?|(([а-яА-Я]{3,20}\\,?)\\s?((ул\\.|улица|проспект|пр\\-кт\\.)[\\s,]?)))." +
        "(((дом\\s?|д\\.)?[0-9]{1,3})\\,?)\\s?" +
        "(((корп\\.\\s?|корпус\\s)[0-9]{1,3})?)|((литера\\s?|лит.\\s?)[А-Я]([0-9]{1,2})?)\\,?)";
    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(str);
    if (matcher.find()) {
      StringBuilder address = new StringBuilder();
      System.out.println(matcher.group(0));
      address.append(matcher.group(0));
      while (matcher.find()) {
        address.append(str, matcher.start(), matcher.end());
      }
      System.out.println(address);
      visitCard.setOfficeAddress(formatAddress(address.toString()));
    }
  }

  private void parseTypeOfOrganization(Card visitCard, String str) {
    String[] tmp = str.split(" ");
    for (String item : tmp) {
      if (Utils.typeOfOrganization.contains(item.toLowerCase())) {
        visitCard.setTypeOfOrganization(item.toLowerCase());
      }
    }
  }

  private String toPhoneFormat(String str) {
    String tmp = str.replaceAll("[\\s\\-\\(\\)]", "");
    String telephoneNumber;
    if (tmp.length() > 8) {
      if (tmp.charAt(0) == '+') {
        telephoneNumber = tmp.substring(0, 2) + "(" + tmp.substring(2, 5) + ")" + tmp.substring(5, 8) + "-" + tmp.substring(8, 10) + "-" + tmp.substring(10);
        System.out.println(telephoneNumber);
        return telephoneNumber;
      }
      return telephoneNumber = "+7(" + tmp.substring(1, 4) + ")" + tmp.substring(4, 7) + "-" + tmp.substring(7, 9) + "-" + tmp.substring(9);
    } else if (tmp.length() >= 6) {
      return telephoneNumber = tmp.substring(0, 3) + "-" + tmp.substring(3, 5) + "-" + tmp.substring(5);

    }
    return "";
  }

  private static String formatAddress(String str) {
    String tmpStr = str.replaceAll(" ", "");
    StringBuilder formattedStr = new StringBuilder();
    for (char i : tmpStr.toCharArray()) {
      if (i == '.' | i == ',') {
        formattedStr.append(i);
        formattedStr.append(" ");
        continue;
      }
      formattedStr.append(i);
    }
    return formattedStr.toString();
  }

  private void printCard() {
    for (Card card : visitCardsArray) {
      card.showCardInfo();
    }
  }
}

