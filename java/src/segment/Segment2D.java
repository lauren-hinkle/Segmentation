package segment;

import java.util.*;
import java.awt.*;
import java.awt.image.*;

class Segment2D
{
    Graph2D gRed;
    Graph2D gGreen;
    Graph2D gBlue;

    public Segment2D(BufferedImage bi)
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

        double k = 150;
        gRed = new Graph2D(red, k);
        gGreen = new Graph2D(green, k);
        gBlue = new Graph2D(blue, k);

    }

    /// XXXX -- NOT DONE YET!
    public void intersectRGBSegments(BufferedImage bi)
    {
        HashMap<Component2D, Component2D> rSegment = gRed.segment();
        HashMap<Component2D, Component2D> gSegment = gGreen.segment();
        HashMap<Component2D, Component2D> bSegment = gBlue.segment();

        HashMap<Component2D, Component2D> intersection = new HashMap<Component2D, Component2D>();
        for(int x=0; x<bi.getWidth(); x++){
            for(int y=0; y<bi.getHeight(); y++){
                Node n = new Node(x,y,bi.getColor());
                Component c = new Component(


         for(int x=0; x<bi.getWidth(); x++){
            for(int y=0; y<bi.getHeight(); y++){
               // Consider its neighbors
                for(int x1=x-1; x1<x+2; x1++){
                    for(int y1=y-1; y1<y+2; y1++){

                for(Component2D cRed : rSegment.keySet()){
                    if (cRed.containsNode(x,y) && cRed.containsNode(x1,y1)
    }

    public static void main(String[] args)
    {
        System.out.println("It compiled...");

    }
}