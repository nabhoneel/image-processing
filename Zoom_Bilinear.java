import java.io.*;
import java.awt.Color;
import java.awt.image.*;
import javax.imageio.*;

class Bilinear {
  String meta = "";
  int width, height;
  int image[][];

  public Bilinear(String imgLoc) {

    try {
      BufferedReader br = new BufferedReader(new FileReader(imgLoc));
      meta += br.readLine() + "\n";
      meta += br.readLine() + "\n";

      String rc = br.readLine();
      width = Integer.parseInt(rc.split(" ")[0]);
      System.out.println("Width = " + width);
      height = Integer.parseInt(rc.split(" ")[1]);
      System.out.println("Height = " + height);

      image = new int[height][width];

      for (int i = 0; i < height; i++)
        for (int j = 0; j < width; j++)
          image[i][j] = Integer.parseInt(br.readLine());

      br.close();
    } catch (Exception e) {
      System.out.println(e);
    }

  }

  public void zoomByFactor(int x, int y) {
    int newWidth = width * x;
    int newHeight = height * y;

    int newImage[][] = new int[newHeight][newWidth];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newImage[i * y][j * x] = image[i][j];
      }
    }

    int x1, x2, y1, y2, q11, q12, q21, q22, X, Y, pixelValue;
    double fraction, intermediate;

    for (int i = 0; i < newHeight - y; i++) {
      for (int j = 0; j < newWidth - x; j++) {
        if (i % y == 0 && j % x == 0)
          continue;
        else {
          try {
            x1 = j - j % x;
            x2 = x1 + x;
            y1 = i - i % y;
            y2 = y1 + y;

            q11 = newImage[y1][x1];
            q12 = newImage[y1][x2];
            q21 = newImage[y2][x1];
            q22 = newImage[y2][x2];

            X = j;
            Y = i;

            fraction = (double) 1 / ((x2 - x1) * (y2 - y1));

            intermediate = fraction * (double) (q11 * (x2 - X) * (y2 - Y) + q21 * (X - x1) * (y2 - Y) + q12 * (x2 - X) * (Y - y1) + q22 * (X - x1) * (Y - y1));

            pixelValue = (int) Math.ceil(intermediate);

            newImage[i][j] = pixelValue;
          } catch (Exception e) {
            System.out.println(e);
            System.out.println("(" + i + ", " + j + ")");
            continue;
          }
        }
      }
    }

    width = newWidth;
    height = newHeight;
    image = newImage;
  }

  public void output() {
    try {
      PrintWriter printer = new PrintWriter(new FileWriter("./img/output-zoom-bilinear.pgm"));
      printer.println(meta.split("\n")[0]);
      printer.println(meta.split("\n")[1]);
      printer.println(width + " " + height);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          printer.println(image[i][j]);
        }
      }
      printer.close();
      System.out.println("Image has been written to file");
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void displayMatrix(int arr[][]) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        System.out.print(arr[i][j] + "\t");
      }
      System.out.println();
    }
  }

  public static void main(String args[]) {
    Bilinear nr = new Bilinear("./img/cycle.pgm");
    nr.zoomByFactor(3, 4);//width, height zoom factors
    nr.output();
  }
}