import java.io.*;
import java.awt.Color;
import java.awt.image.*;
import javax.imageio.*;

class Histogram {
  String meta = "";
  int length, width;
  int image[][];

  public Histogram(String imgLoc) {

    try {
      BufferedReader br = new BufferedReader(new FileReader(imgLoc));
      meta += br.readLine() + "\n";
      meta += br.readLine() + "\n";

      String rc = br.readLine();
      length = Integer.parseInt(rc.split(" ")[0]);
      System.out.println("Length = " + length);
      width = Integer.parseInt(rc.split(" ")[1]);
      System.out.println("Width = " + width);

      image = new int[length][width];

      for (int i = 0; i < length; i++)
        for (int j = 0; j < width; j++)
          image[i][j] = Integer.parseInt(br.readLine());

      br.close();
    } catch (Exception e) {
      System.out.println(e);
    }

  }

  public void createHistogram() {
    int levels[] = new int[256];
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < width; j++) {
        levels[image[i][j]]++;
      }
    }
  }

  public void output() {
    try {
      PrintWriter printer = new PrintWriter(new FileWriter("./img/output.pgm"));
      printer.println(meta.split("\n")[0]);
      printer.println(meta.split("\n")[1]);
      printer.println(length + " " + width);

      for (int i = 0; i < length; i++) {
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
    NoiseReduce nr = new NoiseReduce("./img/noisy.pgm");
    nr.createHistogram();
    nr.output();
  }
}