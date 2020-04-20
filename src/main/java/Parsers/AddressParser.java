package Parsers;

import java.util.ArrayList;
import java.util.HashSet;

public class AddressParser extends PhoneParser {
  private String fullString;

  public AddressParser(Card visitCard, String REGEX) {
    super(visitCard, REGEX);
  }

  @Override
  public void parse(String str) {
    fullString = str;
    setAddress(super.toParse(str));
  }

  private void setAddress(HashSet<String> data) {
    for (String item : data) {
      super.visitCard.setOfficeAddress(formatAddress(item, fullString));
    }
  }

  private String formatAddress(String str, String fullString) {
    String upperWords = upperCaseWords(fullString);
    String city = validateCity(upperWords);
    if (!city.isEmpty()) {
      city = city + ", ";
    }
    String tmpStr = city + str; // govnokod
    tmpStr = tmpStr.replaceAll(" ", "");
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

  protected String upperCaseWords(String fullString) {
    final int MAX_NAME_LENGTH = 3;
    String tmpString = fullString.replaceAll("['\'a-zA-Z0-9,.@!=()*'<>;^)#|~\"\\©\\]\\[:]", "");
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

  private boolean isValidStartName(char ch) {
    return ch != 'Ы' && ch != 'Ь' && ch != 'Ъ' && ch != 'Й';
  }

  private boolean isBigLetter(char ch) {
    return ch == 'А' || ch == 'Б' || ch == 'В' || ch == 'Г' || ch == 'Д' || ch == 'Е' || ch == 'Ё' || ch == 'Ж' || ch == 'З' || ch == 'И' || ch == 'Й' ||
        ch == 'К' || ch == 'Л' || ch == 'М' || ch == 'Н' || ch == 'О' || ch == 'П' || ch == 'Р' || ch == 'С' || ch == 'Т' || ch == 'У' || ch == 'Ф' ||
        ch == 'Х' || ch == 'Ц' || ch == 'Ч' || ch == 'Ш' || ch == 'Щ' || ch == 'Ь' || ch == 'Ы' || ch == 'Ъ' || ch == 'Э' || ch == 'Ю' || ch == 'Я';
  }
}