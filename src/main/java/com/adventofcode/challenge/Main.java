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

    public record Platform() {

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
                        List<Point> aboveRow = initialPlatform.get(rowIndex - 1);
                        aboveRow.set(point.column, roundedPoint);
                        movingPlatform.set(rowIndex - 1, aboveRow);

                        Point emptyPoint = new Point(point.column, point.heaviness, '.');
                        List<Point> presentRow = initialPlatform.get(rowIndex);
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

        public static List<List<Point>> rollsNorth(List<List<Point>> initialPlatform) {
            List<List<Point>> tiltedPlatform = new ArrayList<>(initialPlatform);
            for (int i = 1; i < tiltedPlatform.size(); i++) {
                tiltedPlatform = Platform.rollsUp(tiltedPlatform, i);
            }
            return tiltedPlatform;
        }

        public static List<List<Point>> rollsLeft(List<List<Point>> initialPlatform, int rowIndex) {
            List<List<Point>> movingPlatform = new ArrayList<>(initialPlatform);
            for (int i = 1; i < initialPlatform.get(rowIndex).size(); i++) {
                Point point = initialPlatform.get(rowIndex).get(i);
                if (point.nature == 'O') {
                    Point leftPoint = initialPlatform.get(rowIndex).get(i - 1);
                    if (leftPoint.nature == '.') {
                        Point roundedPoint = new Point(i - 1, point.heaviness, 'O');
                        Point emptyPoint = new Point(i, point.heaviness, '.');
                        movingPlatform.get(rowIndex).set(i - 1, roundedPoint);
                        movingPlatform.get(rowIndex).set(i, emptyPoint);
                        return rollsLeft(movingPlatform, rowIndex);
                    }
                }
            }
            return initialPlatform;
        }

        public static List<List<Point>> rollsWest(List<List<Point>> initialPlatform) {
            List<List<Point>> tiltedPlatform = new ArrayList<>(initialPlatform);
            for (int i = 0; i < tiltedPlatform.size(); i++) {
                tiltedPlatform = Platform.rollsLeft(tiltedPlatform, i);
            }
            return tiltedPlatform;
        }

        public static List<List<Point>> rollsDown(List<List<Point>> initialPlatform, int rowIndex) {
            if (rowIndex < initialPlatform.size() - 1) {
                List<List<Point>> movingPlatform = new ArrayList<>(initialPlatform);
                List<Point> roundedRocks = new ArrayList<>();
                boolean endLoop = true;
                for (Point point : initialPlatform.get(rowIndex)) {
                    if (point.nature == 'O') {
                        roundedRocks.add(point);
                    }
                }
                for (Point point : roundedRocks) {
                    char belowChar = initialPlatform.get(rowIndex + 1).get(point.column).nature;
                    if (belowChar == '.') {
                        endLoop = false;

                        Point roundedPoint = new Point(point.column, point.heaviness - 1, 'O');
                        List<Point> belowRow = initialPlatform.get(rowIndex + 1);
                        belowRow.set(point.column, roundedPoint);
                        movingPlatform.set(rowIndex + 1, belowRow);

                        Point emptyPoint = new Point(point.column, point.heaviness, '.');
                        List<Point> presentRow = initialPlatform.get(rowIndex);
                        presentRow.set(point.column, emptyPoint);
                        movingPlatform.set(rowIndex, presentRow);
                    }
                }
                if (!endLoop) {
                    return rollsDown(movingPlatform, rowIndex + 1);
                }
            }
            return initialPlatform;
        }

        public static List<List<Point>> rollsSouth(List<List<Point>> initialPlatform) {
            List<List<Point>> tiltedPlatform = new ArrayList<>(initialPlatform);
            for (int i = tiltedPlatform.size() - 2; i >= 0 ; i--) {
                tiltedPlatform = Platform.rollsDown(tiltedPlatform, i);
            }
            return tiltedPlatform;
        }

        public static List<List<Point>> rollsRight(List<List<Point>> initialPlatform, int rowIndex) {
            List<List<Point>> movingPlatform = new ArrayList<>(initialPlatform);
            for (int i = initialPlatform.get(rowIndex).size() - 2; i >= 0; i--) {
                Point point = initialPlatform.get(rowIndex).get(i);
                if (point.nature == 'O') {
                    Point rightPoint = initialPlatform.get(rowIndex).get(i + 1);
                    if (rightPoint.nature == '.') {
                        Point roundedPoint = new Point(i + 1, point.heaviness, 'O');
                        Point emptyPoint = new Point(i, point.heaviness, '.');
                        movingPlatform.get(rowIndex).set(i + 1, roundedPoint);
                        movingPlatform.get(rowIndex).set(i, emptyPoint);
                        return rollsRight(movingPlatform, rowIndex);
                    }
                }
            }
            return initialPlatform;
        }

        public static List<List<Point>> rollsEast(List<List<Point>> initialPlatform) {
            List<List<Point>> tiltedPlatform = new ArrayList<>(initialPlatform);
            for (int i = 0; i < tiltedPlatform.size(); i++) {
                tiltedPlatform = Platform.rollsRight(tiltedPlatform, i);
            }
            return tiltedPlatform;
        }

        public static List<List<Point>> cycle(List<List<Point>> initialPlatform) {
            List<List<Point>> cyclePlatform = new ArrayList<>(initialPlatform);
            cyclePlatform = rollsNorth(cyclePlatform);
            cyclePlatform = rollsWest(cyclePlatform);
            cyclePlatform = rollsSouth(cyclePlatform);
            cyclePlatform = rollsEast(cyclePlatform);
            return cyclePlatform;
        }

        public static List<Point> memorization(List<List<Point>> tiltedPlatform) {
            List<Point> cycleResult = new ArrayList<>();
            for (List<Point> row : tiltedPlatform) {
                for (Point point : row) {
                    if (point.nature == 'O') {
                        cycleResult.add(point);
                    }
                }
            }
            return cycleResult;
        }
    }

    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-14-reflector.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            long load = 0;
            long cycleLoad = 0;
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
            System.out.println();

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

            // Part Two :
            // Run the spin cycle for 1.000.000.000 cycles. Afterward, what is the total load on the north support beams?

            // La plateforme est déjà orientée au nord mais ça n'y change rien puisque le cycle commence par le nord
            int first = 0, next = 0;
            List<Point> firstReturn = new ArrayList<>();
            List<List<Point>> cycleResults = new ArrayList<>();
            forBreak:
            for (int i = 0; i < 1000000000; i++) {
                cycleResults.add(Platform.memorization(tiltedPlatform));
                tiltedPlatform = Platform.cycle(tiltedPlatform);
                if (firstReturn.equals(Platform.memorization(tiltedPlatform))) {
                    next = i;
                    break forBreak;
                }
                for (List<Point> cycleResult : cycleResults) {
                    if (cycleResult.equals(Platform.memorization(tiltedPlatform)) && first == 0) {
                        firstReturn.addAll(cycleResult);
                        first = i;
                    }
                }
            }

            int rest = (1000000000 - next) % (next - first);
            for (int i = 1; i < rest; i++) {
                tiltedPlatform = Platform.cycle(tiltedPlatform);
            }
            for (List<Point> row : tiltedPlatform) {
                for (Point point : row) {
                    if (point.nature == 'O') {
                        cycleLoad += point.heaviness;
                    }
                }
            }

//            // Impression
//            for (List<Point> row : tiltedPlatform) {
//                for (Point point : row) {
//                    System.out.print(point.nature);
//                }
//                System.out.println();
//            }
//            System.out.println();

            System.out.println("After one billion (un milliard de) cycles, the load on the north support beams is " + cycleLoad);

            // That's the right answer! You are one gold star closer to restoring snow operations.

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}