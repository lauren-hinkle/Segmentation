package segment;

class Node2D
{
    int color;
    int[] location;
    Component2D component;

    public Node2D(int x, int y, int color)
    {
        this.color = color;
        location = new int[]{x,y};
    }

    public boolean nodeEqual(Node2D n)
    {
        boolean equal = (n.color == color);
        equal = equal && (n.location[0] == location[0]);
        equal = equal && (n.location[1] == location[1]);
        return equal;
    }

    public void setComponent(Component2D c)
    {
        component = c;
    }
}