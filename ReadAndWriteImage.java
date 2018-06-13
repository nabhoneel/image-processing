import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.*;

public class ReadAndWriteImage {
  public static void main(String[] args) {
    File f = null;

    BufferedImage image = null;

    try {
      f = new File("./img/3.gif");
    } catch(Exception e) {
      System.out.println(e);
    }

    try {
      image = ImageIO.read(f);
      ImageIO.write(image, "pnm", new File("./output.pnm"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("done");
  }
}