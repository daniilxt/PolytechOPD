package Parsers;


import java.util.ArrayList;

public class MainTesseract {
    private Recognize recognizer = new Recognize();

    public static void main(String[] args) {
    }

    public void startParser(String[] strings) {
        String res = strings[0] + strings[1];
        String[] words1 = res.split("\n");
        // отладка вывода
/*        for (String item : words1) {
            System.out.println("-----" + item);
        }*/
        recognizer.start(words1);
        System.out.println("COUNT REEECORDS" + recognizer.getVisitCardsArray().toString());
    }

    public ArrayList<Card> getCardsArray() {
        return recognizer.getVisitCardsArray();
    }
}