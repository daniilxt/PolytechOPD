package Parsers;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneParser implements Parsable {
  protected final String REGEX;
  protected Card visitCard;
  protected HashSet<String> dataSet = new HashSet<>();

  public PhoneParser(Card visitCard, String REGEX) {
    this.REGEX = REGEX;
    this.visitCard = visitCard;
  }

  @Override
  public void parse(String str) {
    dataSet = toParse(str);
    if (!dataSet.isEmpty()){
      toPhoneFormat();
    }
  }

  protected HashSet<String> toParse(String str) {
    HashSet<String> dataSet = new HashSet<>();
    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(str);
    if (matcher.find()) {
      dataSet.add(matcher.group(0));
      while (matcher.find()) {
        dataSet.add(str.substring(matcher.start(), matcher.end()));
      }
    }
    return dataSet;
  }

  private void toPhoneFormat() {
    for (String item : dataSet) {
      visitCard.setPhoneNumber(toPhoneFormat(item));
    }
  }

  private String toPhoneFormat(String str) {
    String tmp = str.replaceAll("[\\s\\-\\(\\)]", "");
    String telephoneNumber;
   // System.out.println("PHONE: " + tmp);
    if (tmp.length() > 8) {
      if (tmp.charAt(0) == '+') {
        telephoneNumber = tmp.substring(0, 2) + "(" + tmp.substring(2, 5) + ")" + tmp.substring(5, 8) + "-" + tmp.substring(8, 10) + "-" + tmp.substring(10);
        return telephoneNumber;
      } else if (((tmp.startsWith("8")) || tmp.charAt(0) == '9') && tmp.length() == 10) {
        return "+7(" + tmp.substring(0, 3) + ")" + tmp.substring(3, 6) + "-" + tmp.substring(6, 8) + "-" + tmp.substring(8);
      } else if (tmp.startsWith("88")) {
        return "8(" + tmp.substring(1, 4) + ")" + tmp.substring(4, 7) + "-" + tmp.substring(7, 9) + "-" + tmp.substring(9);
      }
      return "+7(" + tmp.substring(1, 4) + ")" + tmp.substring(4, 7) + "-" + tmp.substring(7, 9) + "-" + tmp.substring(9);
    } else if (tmp.length() == 6) {
      return tmp.substring(0, 2) + "-" + tmp.substring(2, 4) + "-" + tmp.substring(4);
    } else if (tmp.length() > 6) {
      return tmp.substring(0, 3) + "-" + tmp.substring(3, 5) + "-" + tmp.substring(5);
    }
    return "";
  }
}
