import java.io.*;
import java.util.*;
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

  public void zoomByFactor(double x, double y) {
    int newWidth = (int) ((double) width * x);
    int newHeight = (int) ((double) height * y);

    int newImage[][] = new int[newHeight][newWidth];

    int q11, q12, q21, q22, pixelValue;
    double x_top, x_bottom, X, Y;

    for (int i = 0; i < newHeight; i++) {
      for (int j = 0; j < newWidth; j++) {
        if (i % y == 0 && j % x == 0) {
          newImage[i][j] = image[(int) (i / y)][(int) (j / x)];
        } else {
          try {
            //getting the 4 known points:
            q11 = image[(int) (i / y)][(int) (j / x)];//top left
            q12 = image[(int) (i / y)][(int) (j / x + 1)];//top right
            q21 = image[(int) (i / y + 1)][(int) (j / x)];//bottom left
            q22 = image[(int) (i / y + 1)][(int) (j / x + 1)];//bottom right

            //getting distance from nearest points
            X = (double) ((j%x) / x);
            Y = (double) ((i%y) / y);

            //interpolation:
            x_top = q11 * X + q12 * (1 - X);
            x_bottom = q21 * X + q22 * (1 - X);
            
            pixelValue = (int) (x_top * Y + x_bottom * (1 - Y));

            newImage[(int) i][(int) j] = pixelValue;
          } catch (Exception e) {
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
      PrintWriter printer = new PrintWriter(new FileWriter("./img/output-bilinear.pgm"));
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

    Scanner sc = new Scanner(System.in);
    System.out.print("Enter width factor: ");
    double x = sc.nextDouble();
    System.out.print("Enter height factor: ");
    double y = sc.nextDouble();

    sc.close();

    nr.zoomByFactor(x, y);// width, height zoom factors
    nr.output();
  }
}