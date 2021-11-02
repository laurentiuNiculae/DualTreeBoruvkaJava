package EMSTLib;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {
    private ArrayList<Point> points;
    private ArrayList<KdNode> kdNodes;

    public KdTree(ArrayList<Point> points, int maximumNodeCapacity) throws Exception {
        int initialCapacity;
        initialCapacity = (int) Math.pow(2.0, Math.ceil(log2((double) points.size()/maximumNodeCapacity)) + 1);
        this.points = new ArrayList<>(points);
        this.kdNodes = new ArrayList<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            kdNodes.add(new KdNode());
        }

        buildTree(1, 0, points.size(), maximumNodeCapacity);
    }

    private void buildTree(int nodeIndex, int start, int end, int maximumNodeCapacity) throws Exception {
        kdNodes.get(nodeIndex).AABB = new BoundingBox(points, start, end);
        kdNodes.get(nodeIndex).size = end - start;
        kdNodes.get(nodeIndex).start = start;
        kdNodes.get(nodeIndex).end = end;

        if (end - start <= maximumNodeCapacity)
        {
            kdNodes.get(nodeIndex).isLeaf = true;
            return;
        }
        int biggestDimension = kdNodes.get(nodeIndex).AABB.getLargestDimension();

        Comparator<Point> byDimension = (p1, p2) -> {
            try {
                return Double.compare(p1.get(biggestDimension), p2.get(biggestDimension));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        };

        points.subList(start, end).sort(byDimension);

        buildTree(nodeIndex * 2, start, (start + end)/2, maximumNodeCapacity);
        buildTree(nodeIndex * 2 + 1, (start + end)/2, end, maximumNodeCapacity);
    }

    public static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    public int getRoot()
    {
        return 1;
    }

    public int getLeft(int nodeIndex)
    {
        return 2 * nodeIndex;
    }

    public int getRight(int nodeIndex)
    {
        return 2 * nodeIndex + 1;
    }

    public List<Point> getKdNodePoints(int kdNodeIndex)
    {
        KdNode kd = kdNodes.get(kdNodeIndex);
        return points.subList(kd.start, kd.end);
    }

    public KdNode getKdNode(int index) {
        return kdNodes.get(index);
    }

    public Point getKdNodeFirstPoint(int kdNodeIndex)
    {
        return points.get(kdNodes.get(kdNodeIndex).start);
    }



}

class KdNode {
    public BoundingBox AABB;
    public int start;
    public int end;
    public int size;
    boolean isLeaf;
    boolean isFullyConnected;

    public KdNode()
    {
        start = 0;
        end = 0;
        size = 0;
        isLeaf = false;
        isFullyConnected = false;
    }
}
