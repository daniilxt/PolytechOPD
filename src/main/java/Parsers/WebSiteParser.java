package Parsers;

import java.util.HashSet;

public class WebSiteParser extends PhoneParser {
  public WebSiteParser(Card visitCard, String REGEX) {
    super(visitCard, REGEX);
  }

  @Override
  public void parse(String str) {
    setWebSite(super.toParse(str));
  }

  private void setWebSite(HashSet<String> data) {
    for (String item : data) {
      super.visitCard.setWebsite(item);
    }
  }
}
