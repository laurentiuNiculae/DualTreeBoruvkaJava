package EMSTLib;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class DataReader {
    public static ArrayList<Point> readPointsAsSSV(String filePath) throws FileNotFoundException {
        ArrayList<Point> points = new ArrayList<>();

        for (String line : new LineReader(filePath)) {
            double[] coordinates = Arrays.stream(line.split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            points.add(new Point(coordinates));
        }
        return points;
    }
}

class LineReader implements Iterable<String> {

    public BufferedReader bufferedReader;

    public LineReader(String filePath) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(filePath));
    }
    @Override
    public Iterator iterator() {
        return new LineIterator(this);
    }
}

class LineIterator implements Iterator<String> {
    private String nextLine;
    private LineReader lineReader;
    public LineIterator(LineReader lineReader) {
        this.lineReader = lineReader;
    }

    @Override
    public boolean hasNext() {
        try {
            nextLine = lineReader.bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextLine != null;
    }

    @Override
    public String next() {
        return nextLine;
    }
}