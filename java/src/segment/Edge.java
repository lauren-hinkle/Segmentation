package segment;

class Edge
{
    Node2D node1;
    Node2D node2;
    double weight;

    public Edge(Node2D n1, Node2D n2)
    {
        node1 = n1;
        node2 = n2;
        weight = Math.abs(node1.color - node2.color);
    }

    public boolean containsNode(Node2D n)
    {
        if (n.nodeEqual(node1) || n.nodeEqual(node2))
            return true;

        return false;
    }

    public Node2D getNeighbor(Node2D n)
    {
        if (n.nodeEqual(node1))
            return node2;
        else if (n.nodeEqual(node2))
            return node1;
        else return null;
    }
}