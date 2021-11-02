package EMSTLib;


import java.util.Arrays;

public class Point {
    static int globalIndex = 0;
    private int index;
    private double[] coordinates;
    public Point (double[] coordinates)
    {
        this.coordinates = coordinates;
        index = globalIndex;
        globalIndex++;
    }

    public Point (Point p)
    {
        this.index = -1;
        coordinates = new double[p.size()];
        if (p.size() >= 0) System.arraycopy(p.coordinates, 0, coordinates, 0, p.size());
    }

    public Point() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Arrays.equals(coordinates, point.coordinates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }

    public double get(int position) throws Exception {
        if (position >= coordinates.length)
        {
            throw new Exception("ERROR! Position greater than max length!");
        }
        return coordinates[position];
    }

    public void set(int position, double value) throws Exception {
        if (position >= coordinates.length)
        {
            throw new Exception("ERROR! Position greater than max length!");
        }
        coordinates[position] = value;
    }

    public int getIndex()
    {
        return index;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public int size()
    {
        return coordinates.length;
    }

    public void printCoordinates()
    {
        for (double dimension : coordinates)
        {
            System.out.println(dimension);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(coordinates);
    }

    public static double distance(Point p1, Point p2) throws Exception {
        if (p1.size() != p2.size())
        {
            throw new Exception("Points are not the same dimension.");
        }

        double distance = 0;
        for (int i = 0; i < p1.size(); i++)
        {
            double aux = p1.get(i) - p2.get(i) ;
            distance += aux*aux;
        }
        return Math.sqrt(distance);
    }
}
