package Parsers;

public class TypeOrganizationParser extends PhoneParser {

  public TypeOrganizationParser(Card visitCard, String REGEX) {
    super(visitCard, REGEX);
  }

  @Override
  public void parse(String str) {
    parseTypeOfOrganization(str);
  }

  private void parseTypeOfOrganization(String str) {
    String[] tmp = str.split(" ");
    for (String item : tmp) {
     // System.out.println("WORDS UTILS: " + item);
      if (Utils.typeOfOrganization.contains(item.toLowerCase())) {
        super.visitCard.setTypeOfOrganization(item.toLowerCase());
      }
    }
    /*
     * TODO add method findTypeOrganizationFromWeb
     *  */
  }
}
