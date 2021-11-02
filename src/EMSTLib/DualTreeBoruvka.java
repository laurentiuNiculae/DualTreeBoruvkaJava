package EMSTLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DualTreeBoruvka {
    private KdTree kdTree;
    private ArrayList<Point> points;
    private DisjointSet disjointSet;
    private ArrayList<Double> dCq;
    private ArrayList<Edge> eCq;
    private ArrayList<Double> cQ;
    private ArrayList solution;
    static public int wow = 0;
    static public int wow2 = 0;


    public DualTreeBoruvka(ArrayList<Point> points) throws Exception {
        int maximumNodeCapacity = 50;
        //int initialCapacity = (int) Math.pow(2.0, Math.ceil(log2((double) points.size()/maximumNodeCapacity)) + 1);
        int initialCapacity = 4* points.size();
        this.points = points;
        kdTree = new KdTree(points, maximumNodeCapacity);
        disjointSet = new DisjointSet(points);
        dCq = new ArrayList<>(initialCapacity);
        eCq = new ArrayList<>(initialCapacity);
        cQ = new ArrayList<>( initialCapacity);

        for (int i = 0; i < initialCapacity; i++)
        {
            dCq.add(Double.MAX_VALUE);
            eCq.add(new Edge(points.get(0), points.get(0)));
            cQ.add(Double.MAX_VALUE);
        }

        solution = new ArrayList(points.size());

        findEMST();
    }

    public static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    public void printSolution()
    {
        solution.forEach(System.out::println);
    }

    private void findEMST() throws Exception {

        while (solution.size() < points.size() - 1) {

            reset();
            updateFullyConnectedStates(kdTree.getRoot());
            findComponentNeighbours(kdTree.getRoot(), kdTree.getRoot());

            for (int i = 0; i < points.size(); i++) {
                if (i == disjointSet.find(points.get(i))) {
                    Edge e = eCq.get(i);

                    if (!disjointSet.isSameSet(e.getP1(), e.getP2())) {
                        solution.add(e);
                    }
                    disjointSet.union(e.getP1(), e.getP2());
                }
            }
        }
    }

    private void findComponentNeighbours(int q, int r) throws Exception {
        if (kdTree.getKdNode(q).isFullyConnected && kdTree.getKdNode(r).isFullyConnected &&
                disjointSet.isSameSet(kdTree.getKdNodeFirstPoint(q), kdTree.getKdNodeFirstPoint(r)))
        {
            DualTreeBoruvka.wow += 1;
            return;
        }
        if (BoundingBox.bbDistance(kdTree.getKdNode(q).AABB, kdTree.getKdNode(r).AABB) > cQ.get(q))
        {
            DualTreeBoruvka.wow2 += 1;
            return;
        }
        if (kdTree.getKdNode(q).isLeaf && kdTree.getKdNode(r).isLeaf)
        {
            cQ.set(q, 0.0);

            List<Point> qPoints = kdTree.getKdNodePoints(q);
            List<Point> rPoints = kdTree.getKdNodePoints(r);

            for (Point p1 : qPoints)
            {
                for (Point p2 : rPoints)
                {
                    if (! disjointSet.isSameSet(p1, p2))
                    {
                        double distance = Point.distance(p1, p2);
                        if (distance < dCq.get(disjointSet.find(p1)) )
                        {
                            dCq.set(disjointSet.find(p1), distance);
                            eCq.set(disjointSet.find(p1), new Edge(p1, p2));
                        }
                    }
                }
                cQ.set(q, Math.max(cQ.get(q), dCq.get(p1.getIndex())));
            }
        }
        else
        {
            int qLeft = kdTree.getLeft(q);
            int qRight = kdTree.getRight(q);
            int rLeft = kdTree.getLeft(r);
            int rRight = kdTree.getRight(r);

            if (kdTree.getKdNode(q).isLeaf)
            {
                findComponentNeighbours(q, rLeft);
                findComponentNeighbours(q, rRight);
                return;
            }

            if (kdTree.getKdNode(r).isLeaf)
            {
                findComponentNeighbours(qLeft, r);
                findComponentNeighbours(qRight, r);
                cQ.set(q, Math.max(cQ.get(qRight), cQ.get(qLeft)));
                return;
            }

            findComponentNeighbours(qLeft, rLeft);
            findComponentNeighbours(qLeft, rRight);
            findComponentNeighbours(qRight, rRight);
            findComponentNeighbours(qRight, rLeft);
            cQ.set(q, Math.max(cQ.get(qRight), cQ.get(qLeft)));
        }
    }

    private void updateFullyConnectedStates(int nodeIndex)
    {
        if (kdTree.getKdNode(nodeIndex).isFullyConnected)
        {
            return;
        }
        if (kdTree.getKdNode(nodeIndex).isLeaf)
        {
            boolean isFullyConnected;
            Point firstPoint = kdTree.getKdNodePoints(nodeIndex).get(0);
            isFullyConnected = kdTree.getKdNodePoints(nodeIndex).stream()
                    .map(p -> disjointSet.isSameSet(p, firstPoint))
                    .reduce(true, (a, b) -> a && b);

            kdTree.getKdNode(nodeIndex).isFullyConnected = isFullyConnected;
        }
        else
        {
            int right = kdTree.getRight(nodeIndex);
            int left = kdTree.getLeft(nodeIndex);

            KdNode rightNode = kdTree.getKdNode(right);
            KdNode leftNode = kdTree.getKdNode(left);

            updateFullyConnectedStates(right);
            updateFullyConnectedStates(left);

            if (rightNode.isFullyConnected && leftNode.isFullyConnected &&
            disjointSet.isSameSet(kdTree.getKdNodeFirstPoint(right), kdTree.getKdNodeFirstPoint(left)))
            {
                kdTree.getKdNode(nodeIndex).isFullyConnected = true;
            }
        }
    }

    private void reset()
    {
        for (int i = 0; i < cQ.size(); i++)
        {
            cQ.set(i, Double.MAX_VALUE);
            eCq.set(i, new Edge(points.get(0), points.get(0)));
            dCq.set(i, Double.MAX_VALUE);
        }
    }
}
