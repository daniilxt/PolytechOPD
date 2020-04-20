package old;

import java.awt.Graphics2D;
import net.sourceforge.tess4j.*;
import java.awt.Image;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;


public class CorrectImage {

  public static void processImg(BufferedImage ipimage, float scaleFactor, float offset)

    throws IOException, TesseractException {

    // Создание пустого буфера изображения

    // сохранить изображение позже

    // ipimage - буфер изображения

    // входного изображения

    BufferedImage opimage = new BufferedImage(1050, 1024, ipimage.getType());


    // создание 2D-платформы

    // на буфер изображения

    // для рисования нового изображения

    Graphics2D graphic = opimage.createGraphics();

    // рисуем новое изображение, начиная с 0 0

    // размером 1050 x 1024 (увеличенные изображения)

    // null - это объект класса ImageObserver

    graphic.drawImage(ipimage, 0, 0, 1050, 1024, null);
    graphic.dispose();

    // изменить масштаб OP-объекта

    // для серого изображения

    RescaleOp rescale = new RescaleOp(scaleFactor, offset, null);

    // выполняем масштабирование

    // и запись в файл .png

    BufferedImage fopimage = rescale.filter(opimage, null);

    ImageIO.write(fopimage, "jpg", new File("/home/daniilxt/exam/github/JavaSPBSTU/OCRPtoject/recognize/rus/vis4.jpg"));

    // Создание класса Тессеракта

    // который используется для выполнения OCR

    Tesseract it = new Tesseract();
    it.setLanguage("rus");
   // it.setDatapath("/home/daniilxt/exam/github/JavaSPBSTU/OCRPtoject/recognize/rus/vis4.jpg");
    it.setDatapath("/usr/local/share/tessdata");


    // делаем OCR на изображении

    // и сохраняем результат в строке str

    String str = it.doOCR(fopimage);

    System.out.println(str);
    it.setLanguage("eng");
    it.doOCR(fopimage);
    System.out.println(str);

  }

  public static void main(String[] args) throws Exception {
    File f = new File("/home/daniilxt/exam/github/JavaSPBSTU/OCRPtoject/recognize/rus/vis14.jpg");

    BufferedImage ipimage = ImageIO.read(f);

    // получение содержимого RGB всего файла изображения

    double d = ipimage.getRGB(ipimage.getTileWidth() / 2, ipimage.getTileHeight() / 2);

    // сравниваем значения

    // и установка новых значений масштабирования

    // которые позже используются RescaleOP

    if (d >= -1.4211511E7 && d < -7254228) {

      processImg(ipimage, 3f, -10f);

    } else if (d >= -7254228 && d < -2171170) {

      processImg(ipimage, 1.455f, -47f);

    } else if (d >= -2171170 && d < -1907998) {

      processImg(ipimage, 1.35f, -10f);

    } else if (d >= -1907998 && d < -257) {

      processImg(ipimage, 1.19f, 0.5f);

    } else if (d >= -257 && d < -1) {

      processImg(ipimage, 1f, 0.5f);

    } else if (d >= -1 && d < 2) {

      processImg(ipimage, 1f, 0.35f);

    }

  }

}