import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

class ImgInp extends JPanel {
  BufferedImage image;

  ImgInp() throws IOException {
    image = ImageIO.read(new File("img/input.png"));
  }

  public void paint(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, 600, 600);
    g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), null);
    System.out.println(image.getWidth() + "    " + image.getHeight());
  }

  public void zoom(Graphics gr, double sx, double sy) {
    int width1;
    int height1;

    width1 = (int) Math.ceil(sx * image.getWidth());
    height1 = (int) Math.ceil(sy * image.getHeight());

    BufferedImage img2 = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_ARGB);

    int i, j;
    int x1, x2, y1, y2, q11, q12, q21, q22, X, Y, pixelValue;
    double fraction, intermediate;

    // The width is the number of columns and height is the number of rows
    for (i = 0; i < image.getHeight(); i++) {
      for (j = 0; j < image.getWidth(); j++) {
        img2.setRGB((int) (i * sx), (int) (j * sy), image.getRGB(i, j));
      }
    }

    for (i = 0; i < (height1 - sy); i++) {
      for (j = 0; j < (width1 - sx); j++) {
        if ((i % sy == 0) && (j % sx == 0)) {
          continue;
        } else {
          try {
            X = j;
            Y = i;

            // 4 distinct co-ordinates are chosen
            x1 = (int) (j - (j % sx));
            y1 = (int) (i - (i % sy));
            x2 = (int) (x1 + sx);
            y2 = (int) (y1 + sy);
            System.out.println("i:" + i + " j:" + j + " x1:" + x1 + " y1:" + y1 + " x2:" + x2 + " y2:" + y2);
            // coefficients are calculated
            q11 = img2.getRGB(y1, x1);
            q12 = img2.getRGB(y1, x2);
            q21 = img2.getRGB(y2, x1);
            q22 = img2.getRGB(y2, x2);

            /*
             * q11 = image.getRGB((int)(y1/sy), (int)(x1/sx)); q12 =
             * image.getRGB((int)(y1/sy), (int)(x2/sx)); q21 = image.getRGB((int)(y2/sy),
             * (int)(x1/sx)); q22 = image.getRGB((int)(y2/sy), (int)(x2/sx));
             */

            fraction = (double) (1 / ((x2 - x1) * (y2 - y1)));

            // normal formula
            intermediate = fraction * (double) (q11 * (x2 - X) * (y2 - Y) + q21 * (X - x1) * (y2 - Y)
                + q12 * (x2 - X) * (Y - y1) + q22 * (X - x1) * (Y - y1));

            img2.setRGB(i, j, (int) Math.ceil(intermediate));
          } catch (Exception e) {
            System.out.println(e);
            continue;
          }
        }
      }
    }

    gr.setColor(Color.black);
    gr.fillRect(0, 0, 600, 600);
    gr.drawImage(img2, 10, 10, width1, height1, null);
  }
}

class Zooming_BL {
  Scanner sc = new Scanner(System.in);

  Zooming_BL() throws IOException {
    JFrame frame = new JFrame();
    ImgInp obj = new ImgInp();
    frame.setBounds(10, 10, 600, 600);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Bilinear Interpolation");
    frame.add(obj);

    System.out.println("Enter The scale factor along x-axis");
    double sx = sc.nextDouble();

    System.out.println("Enter The scale factor along x-axis");
    double sy = sc.nextDouble();

    Graphics gr = frame.getGraphics();
    obj.zoom(gr, sx, sy);
  }

  public static void main(String[] args) throws IOException {
    Zooming_BL zm = new Zooming_BL();
  }
}