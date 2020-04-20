package Parsers;

import java.util.HashSet;

public class EmailParser extends PhoneParser {
  public EmailParser(Card visitCard, String REGEX) {
    super(visitCard, REGEX);
  }
  public void parse(String str) {
    setEmail(super.toParse(str));
  }

  private void setEmail(HashSet<String> data) {
    for (String item : data) {
      super.visitCard.setEmails(item);
    }
  }
}
