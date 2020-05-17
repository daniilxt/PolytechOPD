package Parsers;

public class NameParser extends AddressParser {
  public NameParser(Card visitCard, String REGEX) {
    super(visitCard, REGEX);
  }

  @Override
  public void parse(String str) {
    setName(str);
  }

  private void setName(String fullString) {
    String[] upperWords = upperCaseWords(fullString).split(" ");
    int i = 0;
    for (String str : upperWords) {
      System.out.println("UPPER NAMES:" + str);
      i++;
      if (isRealName(str)) {
        String name = upperWords[i - 2] + " " + upperWords[i - 1];// fiiiiiix
        visitCard.setContactName(name);
        break;
      }
      /*
       *
       *TODO add method parse name, if suffix is missing
       *
       *
       */

    }
  }

  private boolean isRealName(String str) {
    if (str != null && str.length() > 3) {
      String suffix = str.substring(str.length() - 3);
      return suffix.equals("вна") || suffix.equals("вич");
    }
    return false;
  }
}