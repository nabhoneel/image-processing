import java.io.*;
import java.util.*;
import java.awt.Color;
import java.awt.image.*;
import javax.imageio.*;

class Nearest_Neighbour {
  String meta = "";
  int width, height;
  int image[][];

  public Nearest_Neighbour(String imgLoc) {

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
    int px, py;

    int newWidth = (int) (width * x);
    int newHeight = (int) (height * y);

    int newImage[][] = new int[newHeight][newWidth];

    for (int i = 0; i < newHeight; i++)
      for (int j = 0; j < newWidth; j++) {
        newImage[i][j] = image[(int) Math.floor(i * 1 / y)][(int) Math.floor(j * 1 / x)];
      }

    image = newImage;
    width = newWidth;
    height = newHeight;
  }

  public void output() {
    try {
      PrintWriter printer = new PrintWriter(new FileWriter("./img/output-zoom-nearest-neighbour.pgm"));
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
    Nearest_Neighbour nr = new Nearest_Neighbour("./img/cycle.pgm");

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