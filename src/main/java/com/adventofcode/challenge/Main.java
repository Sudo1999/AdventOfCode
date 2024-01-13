package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* Day 14 Part One
Tilt the platform so that the rounded rocks all roll north.
Afterward, what is the total load on the north support beams?
* */

public class Main {

    public record Point(int column, int heaviness, char nature) {}

    public record Platform(List<List<Point>> initialPlatform) {
        public static List<List<Point>> rollsUp(List<List<Point>> initialPlatform, int rowIndex) {
            if (rowIndex > 0) {
                List<List<Point>> movingPlatform = new ArrayList<>(initialPlatform);
                List<Point> roundedRocks = new ArrayList<>();
                boolean endLoop = true;
                for (Point point : initialPlatform.get(rowIndex)) {
                    if (point.nature == 'O') {
                        roundedRocks.add(point);
                    }
                }
                for (Point point : roundedRocks) {
                    char aboveChar = initialPlatform.get(rowIndex - 1).get(point.column).nature;
                    if (aboveChar == '.') {
                        endLoop = false;

                        Point roundedPoint = new Point(point.column, point.heaviness + 1, 'O');
                        List<Point> aboveRow = movingPlatform.get(rowIndex - 1);
                        aboveRow.set(point.column, roundedPoint);
                        movingPlatform.set(rowIndex - 1, aboveRow);

                        Point emptyPoint = new Point(point.column, point.heaviness, '.');
                        List<Point> presentRow = movingPlatform.get(rowIndex);
                        presentRow.set(point.column, emptyPoint);
                        movingPlatform.set(rowIndex, presentRow);
                    }
                }
                if (!endLoop) {
                    return rollsUp(movingPlatform, rowIndex - 1);
                }
            }
            return initialPlatform;
        }
    }

    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-14-reflector.txt";   // input-14-reflector.txt
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            long load = 0;
            List<String> input = new ArrayList<>();
            List<List<Point>> initialPlatform = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                input.add(line);
            }

            int heaviness = input.size();
            for (String line : input) {
                List<Point> row = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    Point point = new Point(i, heaviness, line.charAt(i));
                    row.add(point);
                }
                heaviness--;
                initialPlatform.add(row);
            }

            List<List<Point>> tiltedPlatform = new ArrayList<>(initialPlatform);
            for (int i = 1; i < initialPlatform.size(); i++) {
                List<List<Point>> movingPlatform = Platform.rollsUp(tiltedPlatform, i);
                tiltedPlatform = movingPlatform;
            }

            for (List<Point> row : tiltedPlatform) {
                for (Point point : row) {
                    if (point.nature == 'O') {
                        load += point.heaviness;
                    }
                }
            }

            System.out.println();
            System.out.println("After tilting, the load on the north support beams is " + load);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}