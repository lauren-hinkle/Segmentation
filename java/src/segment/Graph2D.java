package segment;

import java.util.*;

class Graph2D
{
    int width, height;
    ArrayList<Edge> edges;
    ArrayList<Node2D> nodes;
    HashMap<Component2D,Component2D> segments;

    public Graph2D(int[][] colors, double k)
    {
        edges = new ArrayList<Edge>();
        nodes = new ArrayList<Node2D>();
        segments = new HashMap<Component2D, Component2D>();


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
                segments.put(c,c);
            }
        }

        // Add edges to graph - up to eight edges for each node
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                int i = y*width + x;
                if(x != width-1)
                    edges.add(new Edge(nodes.get(i), nodes.get(i+1)));
                if(i+width-1 < width*height)
                    edges.add(new Edge(nodes.get(i), nodes.get(i+width-1)));
                if(i+width < width*height)
                    edges.add(new Edge(nodes.get(i), nodes.get(i+width)));
                if(i+width-1 < width*height && x !=width-1)
                    edges.add(new Edge(nodes.get(i), nodes.get(i+width+1)));
            }
        }
    }



    public ArrayList<Edge> allEdges(Node2D n)
    {
        ArrayList<Edge> all = new ArrayList<Edge>();

        for(Edge e : edges){
            if(e.containsNode(n))
                all.add(e);
        }

        return all;
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



    public HashMap<Component2D, Component2D> segment()
    {
        // Step 0 : Sort edges into non-decreasing edge weights (o_1 - o_m)
        ArrayList<Edge> sorted = nonDecreasingEdges();

        // Step 1 : segmentation S^0 where each vertex v is in its own component
        // This was done when the graph was initialized.
        //Step 2 : Repeat step 3 for q = 1, ..., m
        for(int q=1; q<sorted.size(); q++){
            //Step 3 : Construct S^q
            Edge eq = sorted.get(q);
            Component2D c1 = eq.node1.component;
            Component2D c2 = eq.node2.component;
            if(c1.disjointSet(c2) && c1.pairwiseComparison(c2)){
                mergeComponents(c1, c2);
            }
        }

        return segments;
    }


    public intersectSegmentations(HashMap<Component2D, Component2D> s1, HashMap<Component2D, Component2D> s2)
    {
        HashMap<Component2D, ComponentSD> intersection = new HashMap<Component2D, ComponentSD>();
        ArrayList<Node> finalNodes = new ArrayList<Node>();

        // Add nodes to the graph, create a component for each node
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                Component2D c = new Component2D(this, k);
                Node2D n = new Node2D(x, y, 0);
                n.setComponent(c);
                finalNodes.add(n);
                c.addNode(n);
                untersection.put(c,c);
            }
        }


        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){

                int i = y*width + x;
                int j = i+1;
                if(x != width-1){




                    edges.add(new Edge(nodes.get(i), nodes.get(i+1)));
                if(i+width-1 < width*height)
                    edges.add(new Edge(nodes.get(i), nodes.get(i+width-1)));
                if(i+width < width*height)
                    edges.add(new Edge(nodes.get(i), nodes.get(i+width)));
                if(i+width-1 < width*height && x !=width-1)
                    edges.add(new Edge(nodes.get(i), nodes.get(i+width+1)));


    }


                private void mergeIfApprop(HashMap<Component2D, Component2D> s1, HashMap<Component2D, Component2D> s2, int i, int j)
                {
                    boolean inS1 = false;
                    boolean inS2 = false;
                    for(Component2D s : s1){
                        if(s.containsNodes(s.nodes.get(i), s.nodes.get(j))){
                            inS1 = true;
                            break;
                        }
                    }
                    for(Component2D s : s2){
                        if(s.containsNodes(s.nodes.get(i), s.nodes.get(j))){
                            inS2 = true;
                            break;
                        }
                    }
                    if(inS1 && inS2){

                        mergeComponents(


                }


    private void mergeComponents(Component2D c1, Component2D c2)
    {
        for(Node2D n: c2.nodes){
            n.setComponent(c1);
        }

        segments.remove(c2);
    }


    private ArrayList<Edge> nonDecreasingEdges()
    {
        ArrayList<Edge> nonDecreasing = new ArrayList<Edge>();

        ArrayList<Edge> remainingEdges = new ArrayList<Edge>();
        for(Edge e : edges){
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
