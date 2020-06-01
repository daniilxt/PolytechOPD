package Parsers;

import java.util.ArrayList;

public class Recognize {
  private final static String PHONE_REGEX = "((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}";
  private final static String EMAIL_REGEX = "([a-zA-z0-9\\_\\-]+\\.)*[a-zA-Z0-9\\_\\-]+.@[a-zA-Z0-9\\_\\-]+(\\.[a-z0-9\\_\\-]+)*\\.[a-z]{2,6}";
  private final static String WEB_SITE_REGEX = "([a-z0-9\\-\\.]+)?[a-z0-9\\-]+(!?\\.[a-z]{2,4})";
  private final static String ADDRESS_REGEX = "(((((ул\\.|улица|проспект|пр\\.|пр\\-кт\\.)[\\s]?)([а-яА-Я-]{3,20}\\,?))\\s?|(([а-яА-Я]{3,20}\\,?)\\s?((ул\\.|улица|проспект|пр\\.|пр\\-кт\\.)[\\s,]?)))." +
          "(((дом\\s?|д\\.\\s?)?[0-9]{1,3})\\,?)\\s?" +
          "(((корп\\.\\s?|корпус\\s)[0-9]{1,3})?)|((литера\\s?|лит.\\s?)[А-Я]([0-9]{1,2})?)\\,?)";

  private Card visitCard;
  private Parsable phoneParser;
  private AddressParser addressParser;
  private Parsable webSiteParser;
  private Parsable emailParser;
  private Parsable typeOrganizationParser;
  private NameParser nameParser;
  private StringBuilder fullString;
  private ArrayList<Card> visitCardArray = new ArrayList<>();

  public void start(String[] in) {
    init();
    for (String str : in) {
      phoneParser.parse(str);
      emailParser.parse(str);
      webSiteParser.parse(str);
      typeOrganizationParser.parse(str);

      fullString.append(str);
      fullString.append(" ");
    }
    System.out.println("Full - " + fullString);
    nameParser.parse(fullString.toString());
    addressParser.setNamePerson(visitCard.getContactName());
    addressParser.parse(fullString.toString());
    visitCard.showCardInfo();
    visitCardArray.add(visitCard);
  }

  private void init() {
    visitCard = new Card();
    phoneParser = new PhoneParser(visitCard, PHONE_REGEX); //father
    addressParser = new AddressParser(visitCard, ADDRESS_REGEX);
    webSiteParser = new WebSiteParser(visitCard, WEB_SITE_REGEX);
    emailParser = new EmailParser(visitCard, EMAIL_REGEX);
    typeOrganizationParser = new TypeOrganizationParser(visitCard, "");
    nameParser = new NameParser(visitCard, "");
    fullString = new StringBuilder();

  }

  public ArrayList<Card> getVisitCardsArray() {
    return visitCardArray;
  }

}
