package segment;

import java.util.*;

class Graph2D
{
    int width, height;
    ArrayList<Edge> edges;
    ArrayList<Node2D> nodes;
    //HashMap<Component2D,Component2D> segments;
    ArrayList<Component2D> segments;



    public Graph2D(int[][] colors, double k)
    {
        edges = new ArrayList<Edge>();
        nodes = new ArrayList<Node2D>();
        //segments = new HashMap<Component2D, Component2D>();
        segments = new ArrayList<Component2D>();


        width = colors.length;
        height = colors[0].length;

        // Add nodes to the graph, create a component for each node
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                Component2D c = new Component2D(this, k);
                Node2D n = new Node2D(x, y, colors[x][y]);
                n.setComponent(c);
                nodes.add(n);
                c.addNode(n);
                //segments.put(c,c);
                segments.add(c);
            }
        }

        // Add edges to graph - up to eight edges for each node
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                int i = y*width + x;
                if(x < width-1){
                    Edge e = new Edge(nodes.get(i), nodes.get(i+1));
                    edges.add(e);
                    nodes.get(i).edges.add(e);
                    nodes.get(i+1).edges.add(e);
                }
                if(x > 0 && y < height-1){
                    Edge e = new Edge(nodes.get(i), nodes.get(i+width-1));
                    edges.add(e);
                    nodes.get(i).edges.add(e);
                    nodes.get(i+width-1).edges.add(e);
                }
                if(y < height-1){
                    Edge e = new Edge(nodes.get(i), nodes.get(i+width));
                    edges.add(e);
                    nodes.get(i).edges.add(e);
                    nodes.get(i+width).edges.add(e);
                }
                if(x < width-1 && y<height-1){
                    Edge e = new Edge(nodes.get(i), nodes.get(i+width+1));
                    edges.add(e);
                    nodes.get(i).edges.add(e);
                    nodes.get(i+width+1).edges.add(e);
                }
            }
        }
    }



    public ArrayList<Edge> allEdges(Node2D n)
    {
        /*ArrayList<Edge> all = new ArrayList<Edge>();

        for(Edge e : edges){
            if(e.containsNode(n))
                all.add(e);
        }

        return all;*/
        return n.edges;
    }


    public ArrayList<Node2D> allNeighbors(Node2D n)
    {
        ArrayList<Node2D> neighbors = new ArrayList<Node2D>();

        for(Edge e : edges){
            if(e.containsNode(n))
                neighbors.add(e.getNeighbor(n));
        }

        return neighbors;
    }



    //public HashMap<Component2D, Component2D> segment()
    public ArrayList<Component2D> segment()
    {
        // Step 0 : Sort edges into non-decreasing edge weights (o_1 - o_m)
        ArrayList<Edge> sorted = nonDecreasingEdges(edges);
        System.out.print("sorted "+sorted.size()+"..");

        // Step 1 : segmentation S^0 where each vertex v is in its own component
        // This was done when the graph was initialized.
        //Step 2 : Repeat step 3 for q = 1, ..., m
        for(int q=0; q<sorted.size(); q++){
            if(q%100==0) System.out.print(q+"..");

            //Step 3 : Construct S^q
            Edge eq = sorted.get(q);
            Component2D c1 = eq.node1.component;
            Component2D c2 = eq.node2.component;
            if(c1.disjointSet(c2) && c1.pairwiseComparison(c2)){
                //System.out.print("..merging..");
                mergeComponents(c1, c2, segments);
                //System.out.print("..merged."+q+"..");
            }
        }

        return segments;
    }


    //public void  intersectSegmentations(HashMap<Component2D, Component2D> s1, HashMap<Component2D, Component2D> s2)
    public ArrayList<Component2D> intersectSegmentations(ArrayList<Component2D> s1, ArrayList<Component2D> s2)
    {
        //HashMap<Component2D, Component2D> intersection = new HashMap<Component2D, Component2D>();
        ArrayList<Component2D> intersection = new ArrayList<Component2D>();
        ArrayList<Node2D> finalNodes = new ArrayList<Node2D>();

        // Add nodes to the graph, create a component for each node
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                Component2D c = new Component2D(this, 0);
                Node2D n = new Node2D(x, y, 0);
                n.setComponent(c);
                finalNodes.add(n);
                c.addNode(n);
                //intersection.put(c,c);
                intersection.add(c);
            }
        }

        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){

                int i = y*width + x;
                if(x < width-1){
                    int j = i + 1;
                    mergeIfApprop(s1, s2, i, j, intersection, finalNodes);
                }
                if(x > 0 && y < height-1){
                    int j = i + width - 1;
                    mergeIfApprop(s1, s2, i, j, intersection, finalNodes);
                }
                if(y < height-1){
                    int j = i + width;
                    mergeIfApprop(s1, s2, i, j, intersection, finalNodes);
                }
                if(x < width-1 && y < height-1){
                    int j = i+width+1;
                    mergeIfApprop(s1, s2, i, j, intersection, finalNodes);
                }
            }
        }
        return intersection;
    }

    private void mergeIfApprop(ArrayList<Component2D> s1, ArrayList<Component2D> s2,
                               int i, int j, ArrayList<Component2D> intersection,
                               ArrayList<Node2D> finalNodes)
    {
        boolean found = false;
        for(Component2D c1 : s1){
            for(Component2D c2 : s2){
                if(c1.containsNodes(finalNodes.get(i), finalNodes.get(j)) &&
                   c2.containsNodes(finalNodes.get(i), finalNodes.get(j))){
                    found = true;
                    mergeComponents(finalNodes.get(i).component,
                                    finalNodes.get(j).component,
                                    intersection);
                }
                if (found) break;
            }
            if(found) break;
        }
    }

    //private Component2D mergeComponents(Component2D c1, Component2D c2, HashMap<Component2D, Component2D> s)
    private void mergeComponents(Component2D c1, Component2D c2, ArrayList<Component2D> s)
    {
        for(Edge e : c2.edges){
            c1.addEdge(e);
        }
        for(Node2D n: c2.nodes){
            n.setComponent(c1);
            c1.addNode(n);
        }

        s.remove(c2);
    }



    /** Find a non-decreasing ordering for the edges in a given set.**/
    private ArrayList<Edge> nonDecreasingEdges(ArrayList<Edge> toSort)
    {
        ArrayList<Edge> nonDecreasing = new ArrayList<Edge>();

        ArrayList<Edge> remainingEdges = new ArrayList<Edge>();
        for(Edge e : toSort){
            remainingEdges.add(e);
        }

        while(remainingEdges.size() > 0){
            int location = minimumEdge(remainingEdges);
            nonDecreasing.add(remainingEdges.remove(location));
        }

        return nonDecreasing;
    }


    /** Find the location of the smallest weight edge in a list of edges.**/
    public int minimumEdge(ArrayList<Edge> allEdges)
    {
        double minWeight = 10000;
        int location = -1;

        for(int i=0; i<allEdges.size(); i++){
            if(allEdges.get(i).weight < minWeight){
                minWeight = allEdges.get(i).weight;
                location = i;
            }
        }

        return location;
    }
}
