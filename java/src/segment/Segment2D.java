package segment;

import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import april.util.*;


class Segment2D
{
    static Graph2D gRed;
    static Graph2D gGreen;
    static Graph2D gBlue;

    public static void segment2D(BufferedImage bi)
    {
        int height = bi.getHeight();
        int width = bi.getWidth();

        int[][] red = new int[width][height];
        int[][] green = new int[width][height];
        int[][] blue = new int[width][height];

       // Fill nodes in each of the color's graphs
        for (int x=0; x<width; x++){
            for (int y=0; y<height; y++){
                Color c = new Color(bi.getRGB(x,y));
                red[x][y] = c.getRed();
                green[x][y] = c.getGreen();
                blue[x][y] = c.getBlue();
            }
        }

        double k = 15;
        gRed = new Graph2D(red, k);
        System.out.print("Red graph, ");
        gGreen = new Graph2D(green, k);
        System.out.print("Green graph, ");
        gBlue = new Graph2D(blue, k);
        System.out.print("Blue graph Completed. \n");

    }

    /// XXXX -- NOT DONE YET!
    public static void intersectRGBSegments(BufferedImage bi)
    {
        //HashMap<Component2D, Component2D> rSegment = gRed.segment();
        //HashMap<Component2D, Component2D> gSegment = gGreen.segment();
        //HashMap<Component2D, Component2D> bSegment = gBlue.segment();

        ArrayList<Component2D> rSeg = gRed.segment();
        System.out.print("Segmented red, ");
        ArrayList<Component2D> gSeg = gGreen.segment();
        System.out.print("Segmented green, ");
        ArrayList<Component2D> bSeg = gBlue.segment();
        System.out.print("Segmented blue \n");

        ArrayList<Component2D> rgSeg = gRed.intersectSegmentations(rSeg, gSeg);
        System.out.println("Combined red and green segmentations");
        ArrayList<Component2D> rgbSeg = gRed.intersectSegmentations(rgSeg, bSeg);
        System.out.println("Combined red, green, and blue segmentations");

        // Color each segment a random color
        for(Component2D c : rgbSeg){
            int rgb = randomRGB();
            for(Node2D n : c.nodes){
                int[] loc = n.location;
                bi.setRGB(loc[0], loc[1], rgb);
            }
        }

        try {
            File outputfile = new File("segmented.png");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static int randomRGB()
    {
        Random r = new Random();
        Color rand = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
        int rgb = rand.getRGB();
        return rgb;
    }

    public static void main(String[] args)
    {
        if(args.length > 0){
            System.out.println("It compiled..."+args[0]);
            File f = new File(args[0]);
            try{
                BufferedImage bi = ImageIO.read(f);
                segment2D(bi);
                intersectRGBSegments(bi);
            }
            catch(Exception e){
                System.out.println("First argument should be file of image to segment.");
                e.printStackTrace();
            }
        }
        else
            System.out.println("First argument should be file of image to segment.");
    }
}