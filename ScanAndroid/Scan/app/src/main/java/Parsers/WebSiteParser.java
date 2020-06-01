package Parsers;

import java.util.HashSet;

public class WebSiteParser extends PhoneParser {
    public WebSiteParser(Card visitCard, String REGEX) {
        super(visitCard, REGEX);
    }

    private HashSet<String> emailsArray = new HashSet<>(10);
    private HashSet<String> webCiteArray = new HashSet<>(10);

    @Override
    public void parse(String str) {
        setWebSite(super.toParse(str));
        editEmails();
    }

    private void setWebSite(HashSet<String> data) {
        webCiteArray.addAll(data);
      super.visitCard.setWebsiteArray(webCiteArray);
      validateWebCite();
    }

    private void editEmails() {
        for (String str : super.visitCard.getEmails()) {
            int dogIndex = str.indexOf('@');
            str = str.substring(dogIndex+1);
            emailsArray.add(str);
        }
    }

    private void validateWebCite() {
        for (String item : webCiteArray) {
            if (notWebSite(item)) {
                super.visitCard.removeFromWebCiteArray(item);
            }
        }
    }

    private boolean notWebSite(String str) {
        for (String item : emailsArray) {
          System.out.println("str: " +str + " ITEEEE " + item);

          if (str.equals(item)) {
                return true;
            }
        }
        return false;
    }
}