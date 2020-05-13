package Parsers;

public class Recognize {
  private final static String PHONE_REGEX = "((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}";
  private final static String EMAIL_REGEX = "([a-zA-z0-9\\_\\-]+\\.)*[a-zA-Z0-9\\_\\-]+.@[a-zA-Z0-9\\_\\-]+(\\.[a-z0-9\\_\\-]+)*\\.[a-z]{2,6}";
  private final static String WEB_SITE_REGEX = "([a-z0-9\\-\\.]+)?[a-z0-9\\-]+(!?\\.[a-z]{2,4})";
  private final static String ADDRESS_REGEX = "(((((ул\\.|улица|проспект|пр\\.|пр\\-кт\\.)[\\s]?)([а-яА-Я-]{3,20}\\,?))\\s?|(([а-яА-Я]{3,20}\\,?)\\s?((ул\\.|улица|проспект|пр\\.|пр\\-кт\\.)[\\s,]?)))." +
      "(((дом\\s?|д\\.\\s?)?[0-9]{1,3})\\,?)\\s?" +
      "(((корп\\.\\s?|корпус\\s)[0-9]{1,3})?)|((литера\\s?|лит.\\s?)[А-Я]([0-9]{1,2})?)\\,?)";

  private final Card visitCard = new Card();
  private final Parsable phoneParser = new PhoneParser(visitCard, PHONE_REGEX); //father
  private final Parsable addressParser = new AddressParser(visitCard, ADDRESS_REGEX);
  private final Parsable webSiteParser = new WebSiteParser(visitCard, WEB_SITE_REGEX);
  private final Parsable emailParser = new EmailParser(visitCard, EMAIL_REGEX);
  private final Parsable typeOrganizationParser = new TypeOrganizationParser(visitCard, "");
  private final Parsable nameParser = new NameParser(visitCard, "");

  private final StringBuilder fullString = new StringBuilder();

  public void start(String[] in) {
    for (String str : in) {
      phoneParser.parse(str);
      webSiteParser.parse(str);
      emailParser.parse(str);
      typeOrganizationParser.parse(str);

      fullString.append(str);
      fullString.append(" ");
    }
    System.out.println("Full - " + fullString);
    nameParser.parse(fullString.toString());
    addressParser.parse(fullString.toString());
    visitCard.showCardInfo();
  }

}
