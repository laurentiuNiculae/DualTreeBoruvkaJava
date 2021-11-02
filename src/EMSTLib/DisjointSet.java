package EMSTLib;

import java.util.ArrayList;

public class DisjointSet {
    int[] parent;
    int[] size;

    public DisjointSet(ArrayList<Point> points)
    {
        parent = new int[points.size()];
        size = new int[points.size()];

        resetSets();
    }
    public void resetSets()
    {
        for (int i = 0; i < parent.length; i++)
        {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(Point p)
    {
        int index = p.getIndex();
        while (index != parent[index])
        {
            index = parent[index];
        }
        return index;
    }

    public void union(Point p1, Point p2)
    {
        int index1 = find(p1);
        int index2 = find(p2);

        if (index1 == index2)
        {
            return;
        }

        if (size[index1] < size[index2])
        {
            int temp = index1;
            index1 = index2;
            index2 = temp;
        }
        parent[index2] = index1;
        size[index1] = size[index1] + size[index2];

    }

    public boolean isSameSet(Point p1, Point p2)
    {
        return find(p1) == find(p2);
    }
}
