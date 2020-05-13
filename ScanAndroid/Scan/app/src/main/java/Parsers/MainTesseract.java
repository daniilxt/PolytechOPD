package Parsers;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class MainTesseract {
  public static void main(String[] args) {
    System.out.println(((int) ((0 + Math.random() * 12345) / 6)));
    Tesseract tesseract = new Tesseract();
    tesseract.setLanguage("rus");
    System.out.println("PLEASE, WAIT...");
    try {
      int visNumber = 5;
      for (int i = 0; i < 1; i++) {
        String PATH = "/home/daniilxt/exam/github/JavaSPBSTU/OCRPtoject/recognize/rus/ramz/vis" + visNumber + ".jpg";
        String text = tesseract.doOCR(new File(PATH));
        tesseract.setLanguage("eng");
        String text1 = tesseract.doOCR(new File(PATH));
        visNumber ++;

        Recognize recognizer = new Recognize();
        String res = text + text1;
        String[] words1 = res.split("\n");
        for (String item : words1) {
          System.out.println("-----" + item);
        }
        recognizer.start(words1);
      }
    } catch (TesseractException e) {
      e.printStackTrace();
    }
  }
}