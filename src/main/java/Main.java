import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.tess4j.Tesseract;

import net.sourceforge.tess4j.TesseractException;

public class Main {
  public static void main(String[] args) {
    Tesseract tesseract = new Tesseract();
    tesseract.setLanguage("rus");
    System.out.println("PLEASE, WAIT...");
    try {
      String text = tesseract.doOCR(new File("/home/daniilxt/exam/github/JavaSPBSTU/OCRPtoject/recognize/rus/vis12.jpg"));
      tesseract.setLanguage("eng");
      String text1 = tesseract.doOCR(new File("/home/daniilxt/exam/github/JavaSPBSTU/OCRPtoject/recognize/rus/vis12.jpg"));

      Recognize recognizer = new Recognize();
      String res = text + text1;
      String[] words1 = res.split("\n");
      for (String item : words1) {
        System.out.println("-----" + item);
      }

      recognizer.textParse(words1);
    } catch (TesseractException e) {
      e.printStackTrace();
    }

    //String str = "nullРАГУНДИ ||| Сергей Владимировичофис: СПб, ул. Новолитовская, д.15РСЗа Ул) пе еЕ УЖ,тай: 9227881 @тай.гиПЕРОМ итДее нете -у-жа о (Це) ДДТ МТОТА ЦИ [Ва Те И -2-ЯAMAH{1 1 Cepren Bnagumuposuyogpuc: CI6, yn. HoBonutosckasn, A.15moo: (921)591-21-14mai: 9227881 @mail.ruweb: www.cottages-stroy.ru www.full-houses.ru";
/*    String str = "nullГ „В ! ПАРИКМАХЕРСКАЯ[, <УВ НАРОДНАЯВ. оао № мастер| с °8-981-806-98-96а” ул.Ушинского, д.33, корп.3у пёрз://мК.сот/подок!_йуа_пагодапн-пт: 10.00-21.00, сб-вс: 10.00-20.00be } NAPUKMAXEPCKAR0 HAPO/THASIei P MactepNWR 8-981-806-98-96£EV yn.YwuHckoro, 4.33, kopn.3i https:/ivk.com/nogotki_dlya_narodanu-nt: 10.00-21.00, c6-8¢: 10.00-20.00";
    //String str = "null© у„ [79К р) * яа р ср ул. Инструментальная,  О рОаНе Дарья Ветрова| Ва иЕУ ЛР) Се-й Управляющий партнеСоооАнОНО+7 (962) 684 91 11йу@ги5айу!се.сотммм. гивайдуйсе сот Инструментальная улица,дом 3, литера Б, офис 511,БЦ «Кантемировский» '+7 (812) 330 07 07|пГо@гиваду!се.сот8 24 LY9 J : NYa # pI RUSADVICE Dapbs Berposaf Bu JSADVICE | Ynpasnsioumin napTHeEe+7 (962) 684 91 11dv@rusadvice.comwww. rusadvice.com WHCTpyMeHTanbHan yamua,Aom 3, autepa b, apuc 511,BLL «KaHTemuposckuie+7 (812) 33007 07Info@rusadvice.com";

    // идеал  String REGEX = "((((ул\\.|улица|проспект|пр\\-кт\\.)[\\s]?)([а-яА-Я]{3,20}\\,?))|((([а-яА-Я]{3,20}\\,?))\\s?((ул\\.|улица|проспект|пр\\-кт\\.)[\\s,]?))).(((дом|д\\.)?[0-9]{1,3})\\,?)\\s?((корп\\.|корпус)[0-9]{1,3})?";
    //  String REGEX = "(((((ул\\.|улица|проспект|пр\\-кт\\.)[\\s]?)([а-яА-Я]{3,20}\\,?))|((([а-яА-Я]{3,20}\\,?))\\s?((ул\\.|улица|проспект|пр\\-кт\\.)[\\s,]?))).(((дом|д\\.)?[0-9]{1,3})\\,?)\\s?((корп\\.|корпус)[0-9]{1,3})?)";
    String REGEX = "(((((ул\\.|улица|проспект|пр\\-кт\\.)[\\s]?)([а-яА-Я]{3,20}\\,?))\\s?|(([а-яА-Я]{3,20}\\,?)\\s?((ул\\.|улица|проспект|пр\\-кт\\.)[\\s,]?)))." +
        "(((дом\\s?|д\\.)?[0-9]{1,3})\\,?)\\s?" +
        "(((корп\\.\\s?|корпус\\s)[0-9]{1,3})?)|((литера\\s?|лит.\\s?)[А-Я]([0-9]{1,2})?)\\,?)";
    Pattern pattern = Pattern.compile(REGEX);
    Matcher matcher = pattern.matcher(str);
    if (matcher.find()) {
      StringBuilder address = new StringBuilder();
      System.out.println(matcher.group(0));
      address.append(matcher.group(0));

      while (matcher.find()) {
        address.append(str, matcher.start(), matcher.end());
        System.out.println(str.substring(matcher.start(), matcher.end()));
      }
      System.out.println(address);
      //System.out.println(formatAddress(address.toString()));

    }*/
  }

/*    private static String formatAddress(String str) {
        String tmpStr = str.replaceAll(" ", "");
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
    }*/
}


