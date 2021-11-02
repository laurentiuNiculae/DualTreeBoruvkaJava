package com.company;

import EMSTLib.*;

import java.io.Console;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        //ArrayList<Point> points = DataReader.readPointsAsSSV("C:\\Users\\theme\\IdeaProjects\\LicentaJavaVersiton\\src\\com\\company\\Test.txt");
        //for (Point point :
        //        points) {
        //    System.out.println(point.toString());
        //}
        //System.out.println(Point.distance(points.get(0), points.get(1)));

        ArrayList<Point> points = UniformDataGenerator.generate(2, 10_000, -1000, 1000);

        //for (Point point : points) {
        //    System.out.println(point);
        //}
        long start = System.currentTimeMillis();
        DualTreeBoruvka dtb = new DualTreeBoruvka(points);

        System.out.println("Conected case: "+DualTreeBoruvka.wow);
        System.out.println("Distance case: "+DualTreeBoruvka.wow2);
        long elapsedTimeMillis = System.currentTimeMillis() - start;

        System.out.println(elapsedTimeMillis);
        //dtb.printSolution();

    }
}
