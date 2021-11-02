package EMSTLib;

import java.util.ArrayList;
import java.util.Arrays;

public class BoundingBox {
    private final Point maximalPoint;
    private final Point minimalPoint;
    private final int nrDimensions;

    public BoundingBox(ArrayList<Point> points, int start, int end) throws Exception {
        nrDimensions = points.get(0).size();
        minimalPoint = new Point(points.get(start));
        maximalPoint = new Point(points.get(start));

        for (int i = start; i < end; i++)
        {
            for (int j = 0; j < nrDimensions; j++)
            {
                maximalPoint.set(j, Math.max(maximalPoint.get(j), points.get(i).get(j)));
                minimalPoint.set(j, Math.min(minimalPoint.get(j), points.get(i).get(j)));
            }
        }
    }

    private double dimensionSize(int dimension) throws Exception {
        return maximalPoint.get(dimension) - minimalPoint.get(dimension);
    }

    public int getLargestDimension() throws Exception {
        int largestDim = 0;
        for (int i = 0; i < minimalPoint.size(); i++)
        {
            if (dimensionSize(largestDim) < dimensionSize(i))
            {
                largestDim = i;
            }
        }
        return largestDim;
    }

    @Override
    public String toString() {
        return "AABB{" +
                "minimalPoint=" + Arrays.toString(minimalPoint.getCoordinates()) +
                ", maximalPoint=" + Arrays.toString(maximalPoint.getCoordinates()) +
                '}';
    }

    static public double bbDistance(BoundingBox b1, BoundingBox b2) throws Exception {
        double distance = 0;
        for (int i = 0; i < b1.nrDimensions; i++) {
            double diff = Math.max(b1.minimalPoint.get(i), b2.minimalPoint.get(i))
                        - Math.min(b1.maximalPoint.get(i), b2.maximalPoint.get(i));
            if (diff > 0) {
                distance += diff * diff;
            }
        }
        return Math.sqrt(distance);
    }

}
