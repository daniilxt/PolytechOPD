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
      String text = tesseract.doOCR(new File("/home/daniilxt/exam/github/JavaSPBSTU/OCRPtoject/recognize/rus/vis14.jpg"));
      tesseract.setLanguage("eng");
      String text1 = tesseract.doOCR(new File("/home/daniilxt/exam/github/JavaSPBSTU/OCRPtoject/recognize/rus/vis14.jpg"));

      //System.out.print(text);
      System.out.println("---------");
      //System.out.print(text1);

      Recognize recognizer = new Recognize();
      //ArrayList<String> words1 = new ArrayList<>(Arrays.asList(text.split("\n")));
      String res = text + text1;
      String[] words1 = res.split("\n");
      for (String item : words1) {
        System.out.println("-----" + item);
      }
      recognizer.textParse(words1);
    } catch (TesseractException e) {
      e.printStackTrace();
    }


  }
}





