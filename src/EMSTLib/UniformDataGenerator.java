package EMSTLib;

import java.util.ArrayList;

public class UniformDataGenerator {

    public static ArrayList<Point> generate(int dimensions, int size, int low, int high)
    {
        ArrayList<Point> dataSet = new ArrayList<>(size);

        for (int i=0; i < size; i++)
        {
            double[] coordinates = new double[dimensions];
            for (int j = 0; j < dimensions; j++)
            {
                coordinates[j] = generateRandom(low, high);
            }
            dataSet.add(new Point(coordinates));
        }

        return dataSet;
    }

    private static int generateRandom(int low, int high)
    {
        return (int) ((Math.random() * (high - low)) + low);
    }
}
