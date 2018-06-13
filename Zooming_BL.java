import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFrame;
public class Zooming_BL
{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Zooming_BL() throws IOException
    {
        JFrame frame = new JFrame();
        ImgInp obj = new ImgInp();
        frame.setBounds(10,10,600,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Bilinear Interpolation");
        frame.add(obj);
        
        System.out.println("Enter The scale factor along x-axis");
        double sx = Double.valueOf(br.readLine());
        
        System.out.println("Enter The scale factor along x-axis");
        double sy = Double.valueOf(br.readLine());
        
        Graphics gr = frame.getGraphics();
        obj.zoom(gr,sx,sy);
    }        
    public static void main(String[] args) throws IOException
    {
        Zooming_BL zm = new Zooming_BL();
    }
}