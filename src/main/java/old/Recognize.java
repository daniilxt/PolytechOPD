package old;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recognize {
  private ArrayList<String> container = new ArrayList<>();
  private ArrayList<String> text = new ArrayList<>();
  private ArrayList<Card> visitCardsArray = new ArrayList<>();
  private StringBuilder fullString = new StringBuilder();

  public void textParse(String[] in) {
    Card visitCard = new Card();
    for (String str : in) {
      parsePhoneNumbers(visitCard, str.replaceAll(" ", "")); // это ненормально, это не честно
      parseWebsite(visitCard, str);
      parseEmail(visitCard, str);
      parseTypeOfOrganization(visitCard, str);
      text.add(clearString(str));

      fullString.append(str);
      fullString.append(" ");
    }

    String cityName = "";
    StringBuilder cityStrBuilder = new StringBuilder();
    for (String item : text) {
      cityStrBuilder.append(item);
      cityStrBuilder.append(" ");
    }
    System.out.println("SSBUILDER: -" + cityStrBuilder);
    System.out.println("\n");
    for (String item : text) {
      System.out.print(item + " ");
    }
    System.out.println("\n");
    cityName = validateCity(cityStrBuilder.toString());
    System.out.println("Full str is: " + fullString);

    parseName(visitCard, text);
    parseAddress(visitCard, fullString.toString(), cityName);
    visitCardsArray.add(visitCard);
    System.out.println("\n\n\n\n");
    printCard();
    //System.out.println(visitCardsArray.size());
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
    // String REGEX = "(?:(?:https?|ftp|telnet)://(?:[a-z0-9_-]{1,32}(?::[a-z0-9_-]{1,32})?@)?)?(?:(?:[a-z0-9-]{1,128}\\.)+(?:ru|su|com|net|org|mil|edu|arpa|gov|biz|info|aero|inc|name|[a-z]{2})|(?!0)(?:(?!0[^.]|255)[0-9]{1,3}\\.){3}(?!0|255)[0-9]{1,3})(?:/[a-z0-9.,_@%&?+=~/-]*)?(?:#[^ '\"&]*)?";
    String REGEX = "([a-z0-9\\-\\.]+)?[a-z0-9\\-]+(!?\\.[a-z]{2,4})";
    /*
     * TODO  add method formatWeb
     *
     * */
    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(str);
    if (matcher.find()) {
      visitCard.setWebsite(matcher.group(0));
      while (matcher.find()) {
        visitCard.setWebsite(str.substring(matcher.start(), matcher.end()));
      }
    }
    /*
    * TODO fix when email domain is valid web site
    *  */
  }

  private void parseEmail(Card visitCard, String str) {
    String REGEX = "([a-zA-z0-9\\_\\-]+\\.)*[a-zA-Z0-9\\_\\-]+.@[a-zA-Z0-9\\_\\-]+(\\.[a-z0-9\\_\\-]+)*\\.[a-z]{2,6}";
    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(str);
    if (matcher.find()) {
      visitCard.setEmails(matcher.group(0).replaceAll("\\s", ""));
      while (matcher.find()) {
        visitCard.setEmails(str.substring(matcher.start(), matcher.end()).replaceAll("\\s", ""));
      }
    }
  }

  private void parseAddress(Card visitCard, String str, String city) {
    String REGEX = "(((((ул\\.|улица|проспект|пр\\.|пр\\-кт\\.)[\\s]?)([а-яА-Я-]{3,20}\\,?))\\s?|(([а-яА-Я]{3,20}\\,?)\\s?((ул\\.|улица|проспект|пр\\.|пр\\-кт\\.)[\\s,]?)))." +
        "(((дом\\s?|д\\.\\s?)?[0-9]{1,3})\\,?)\\s?" +
        "(((корп\\.\\s?|корпус\\s)[0-9]{1,3})?)|((литера\\s?|лит.\\s?)[А-Я]([0-9]{1,2})?)\\,?)";
/*    String REGEX = "(((г\\.\\s?|город\\s?|гор.\\s?)?(Санкт\\-Петербург\\s?|СПб\\s?|Москва\\s?|Пенза\\s?|Пермь\\s?)\\,?)?" +
        "((((ул\\.|улица|проспект|пр\\.|пр\\-кт\\.)[\\s]?)([а-яА-Я-]{3,20}\\,?))\\s?|(([а-яА-Я]{3,20}\\,?)\\s?((ул\\.|улица|проспект|пр\\.|пр\\-кт\\.)[\\s,]?)))." +
        "(((дом\\s?|д\\.\\s?)?[0-9]{1,3})\\,?)\\s?" +
        "(((корп\\.\\s?|корпус\\s)[0-9]{1,3})?)|((литера\\s?|лит.\\s?)[А-Я]([0-9]{1,2})?)\\,?)";*/

    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(str);
    if (matcher.find()) {
      StringBuilder address = new StringBuilder();
      if (!city.isEmpty()) {
        address.append(city);
        address.append(',');
      }
      address.append(matcher.group(0));
      while (matcher.find()) {
        address.append(str, matcher.start(), matcher.end());
      }
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
    /*
    * TODO add method findTypeOrganizationFromWeb
    *  */
  }

  private void parseName(Card visitCard, ArrayList<String> text) {
    for (String str : text) {
      if (isRealName(str)) {
        visitCard.setContactName(str);
        break;
      }
      /*
      *
      *TODO add method parse name, if suffix is missing
      *
      * */

    }
  }

  private void printCard() {
    for (Card card : visitCardsArray) {
      card.showCardInfo();
    }
  }

  private String toPhoneFormat(String str) {
    String tmp = str.replaceAll("[\\s\\-\\(\\)]", "");
    String telephoneNumber;
    if (tmp.length() > 8) {
      if (tmp.charAt(0) == '+') {
        telephoneNumber = tmp.substring(0, 2) + "(" + tmp.substring(2, 5) + ")" + tmp.substring(5, 8) + "-" + tmp.substring(8, 10) + "-" + tmp.substring(10);
        return telephoneNumber;
      } else if ((tmp.substring(0, 3).equals("812")) || tmp.charAt(0) == '9') {
        return telephoneNumber = "+7(" + tmp.substring(0, 3) + ")" + tmp.substring(3, 6) + "-" + tmp.substring(6, 8) + "-" + tmp.substring(8);
      } else if (tmp.substring(0, 2).equals("88")) {
        return telephoneNumber = "8(" + tmp.substring(1, 4) + ")" + tmp.substring(4, 7) + "-" + tmp.substring(7, 9) + "-" + tmp.substring(9);
      }
      return telephoneNumber = "+7(" + tmp.substring(1, 4) + ")" + tmp.substring(4, 7) + "-" + tmp.substring(7, 9) + "-" + tmp.substring(9);
    } else if (tmp.length() == 6) {
      return telephoneNumber = tmp.substring(0, 2) + "-" + tmp.substring(2, 4) + "-" + tmp.substring(4);
    } else if (tmp.length() > 6) {
      return telephoneNumber = tmp.substring(0, 3) + "-" + tmp.substring(3, 5) + "-" + tmp.substring(5);

    }
    return "";
  }

  private String formatAddress(String str) {
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

  private String clearString(String str) {
    final int MAX_NAME_LENGTH = 3;
    String tmpString = str.replaceAll("['\'a-zA-Z0-9,.@!=()*'<>;^)#|~\"\\©\\]\\[:]", "");
    ArrayList<String> strArray = new ArrayList<>();
    StringBuilder tmpStringBuilder = new StringBuilder();
    for (int i = 0; i < tmpString.length() - 1; i++) {
      if (isBigLetter(tmpString.charAt(i)) && isValidStartName(tmpString.charAt(i)) && !isBigLetter(tmpString.charAt(i + 1)) && tmpString.charAt(i + 1) != ' ') {
        tmpStringBuilder.append(tmpString.charAt(i));
        while ((tmpString.charAt(i) != '$' && tmpString.charAt(i) != ' ') && i < tmpString.length() - 1) {
          if (isBigLetter(tmpString.charAt(i + 1))) {
            break;
          }
          tmpStringBuilder.append(tmpString.charAt(i + 1));
          i++;
        }
        if (tmpStringBuilder.length() > MAX_NAME_LENGTH) {
          strArray.add(tmpStringBuilder.toString());
        }
        tmpStringBuilder.delete(0, tmpStringBuilder.length());
      }
    }
    for (String item : strArray) {
      tmpStringBuilder.append(item);
    }
    return tmpStringBuilder.toString().trim();
  }

  private String validateCity(String str) {
    String[] arrayOfPossibleCityName = str.split(" ");
    for (String item : arrayOfPossibleCityName) {
      if (!item.isEmpty()) {
        //System.out.println(item);
        if (Utils.cityList.contains(item)) {
          return item;
        }
      }
    }
    return "";
  }

  private boolean isValidStartName(char ch) {
    return ch != 'Ы' && ch != 'Ь' && ch != 'Ъ' && ch != 'Й';
  }

  private boolean isBigLetter(char ch) {
    return ch == 'А' || ch == 'Б' || ch == 'В' || ch == 'Г' || ch == 'Д' || ch == 'Е' || ch == 'Ё' || ch == 'Ж' || ch == 'З' || ch == 'И' || ch == 'Й' ||
        ch == 'К' || ch == 'Л' || ch == 'М' || ch == 'Н' || ch == 'О' || ch == 'П' || ch == 'Р' || ch == 'С' || ch == 'Т' || ch == 'У' || ch == 'Ф' ||
        ch == 'Х' || ch == 'Ц' || ch == 'Ч' || ch == 'Ш' || ch == 'Щ' || ch == 'Ь' || ch == 'Ы' || ch == 'Ъ' || ch == 'Э' || ch == 'Ю' || ch == 'Я';
  }

  private boolean isRealName(String str) {
    if (str != null && str.length() > 3) {
      String suffix = str.substring(str.length() - 3);
      return suffix.equals("вна") || suffix.equals("вич");
    }
    return false;
  }

}


