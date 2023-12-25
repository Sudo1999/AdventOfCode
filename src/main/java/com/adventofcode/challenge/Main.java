package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* Day 10 Part One :
How many steps along the loop does it take to get from the starting position to the point farthest from the starting position?
* */

public class Main {

    public static class Point {
        int x, y;
        char pipe;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        Point(int x, int y, char pipe) {
            this.x = x;
            this.y = y;
            this.pipe = pipe;
        }
        public List<Point> neighbors() {
            return List.of(new Point(x, y - 1), new Point(x, y + 1), new Point(x - 1, y), new Point(x + 1, y));
        }
        public boolean equals(Point point) {
            return (this.x == point.x && this.y == point.y && this.pipe == point.pipe);
        }
        public void print() {
            System.out.println("this.x = " + this.x + " && this.y = " + this.y + " && this.pipe = " + this.pipe);
        }
    }

    public static class Puzzle {
        int size, length;
        Point startPoint;
        List<String> input;
        Puzzle(List<String> input) {
            this.input = input;
            this.size = input.size();
            this.length = input.get(0).length();
        }
        public char getPipe(int x, int y) {
            return input.get(y).charAt(x);
        }
        public Point moveFrom(Point previousPoint, Point nowPoint) {
            char pipe = getPipe(nowPoint.x, nowPoint.y);
            Point nextPoint = switch(pipe) {
                case '|' -> {
                    if (previousPoint.y < nowPoint.y) {
                        yield new Point(nowPoint.x, nowPoint.y+1);
                    } else {
                        yield new Point(nowPoint.x, nowPoint.y-1);
                    }
                }
                case '-' -> {
                    if (previousPoint.x < nowPoint.x) {
                        yield new Point(nowPoint.x+1, nowPoint.y);
                    } else {
                        yield new Point(nowPoint.x-1, nowPoint.y);
                    }
                }
                case 'L' -> {
                    if (previousPoint.y < nowPoint.y) {
                        yield new Point(nowPoint.x+1, nowPoint.y);
                    } else {
                        yield new Point(nowPoint.x, nowPoint.y-1);
                    }
                }
                case 'J' -> {
                    if (previousPoint.y < nowPoint.y) {
                        yield new Point(nowPoint.x-1, nowPoint.y);
                    } else {
                        yield new Point(nowPoint.x, nowPoint.y-1);
                    }
                }
                case '7' -> {
                    if (previousPoint.y > nowPoint.y) {
                        yield new Point(nowPoint.x-1, nowPoint.y);
                    } else {
                        yield new Point(nowPoint.x, nowPoint.y+1);
                    }
                }
                case 'F' -> {
                    if (previousPoint.y > nowPoint.y) {
                        yield new Point(nowPoint.x+1, nowPoint.y);
                    } else {
                        yield new Point(nowPoint.x, nowPoint.y+1);
                    }
                }
                default -> {
                    yield null;
                }
            };
            char nextPipe = nextPoint != null ? getPipe(nextPoint.x, nextPoint.y) : '.';
            return new Point(nextPoint.x, nextPoint.y, nextPipe);
        }
        public List<Point> connectedPoints(Point point) {
            List<Point> connectedPoints = new ArrayList<>();
            List<Point> neighbors = point.neighbors();
            for (Point neighbor : neighbors) {
                neighbor.pipe = getPipe(neighbor.x, neighbor.y);
                if (neighbor.x == point.x-1 && neighbor.y == point.y) {
                    if (neighbor.pipe == '-' || neighbor.pipe == 'L' || neighbor.pipe == 'F') {
                        connectedPoints.add(neighbor);
                    }
                }
                if (neighbor.x == point.x+1 && neighbor.y == point.y) {
                    if (neighbor.pipe == '-' || neighbor.pipe == 'J' || neighbor.pipe == '7') {
                        connectedPoints.add(neighbor);
                    }
                }
                if (neighbor.x == point.x && neighbor.y == point.y-1) {
                    if (neighbor.pipe == '|' || neighbor.pipe == 'F' || neighbor.pipe == '7') {
                        connectedPoints.add(neighbor);
                    }
                }
                if (neighbor.x == point.x && neighbor.y == point.y+1) {
                    if (neighbor.pipe == '|' || neighbor.pipe == 'L' || neighbor.pipe == 'J') {
                        connectedPoints.add(neighbor);
                    }
                }
            }
            return connectedPoints;
        }
    }

    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-10-pipemaze.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            // Puzzle
            int numberOfSteps = 0;
            List<String> input = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                line = "." + line + ".";
                input.add(line);
            }
            String emptyLine = "";
            for (int i = 0; i < input.get(0).length(); i++) {
                emptyLine += ".";
            }
            input.add(0, emptyLine);
            input.add(input.size(), emptyLine);
            Puzzle puzzle = new Puzzle(input);

            // Point d'entrée
            Point startPoint = null;
            Point nowPoint = null;
            Point previousPoint = null;
            Point nextPoint = null;
            forBreak:
            for (int y = 0; y < puzzle.size; y++) {
                for (int x = 0; x < puzzle.length; x++) {
                    if (puzzle.getPipe(x, y) == 'S') {
                        startPoint = new Point(x, y, 'S');
                        puzzle.startPoint = startPoint;
                        break forBreak;
                    }
                }
            }
            nowPoint = startPoint;
            nextPoint = puzzle.connectedPoints(nowPoint).get(0);
            numberOfSteps++;

            // Parcours du labyrinthe
            while (!nextPoint.equals(startPoint)) {
                previousPoint = nowPoint;
                nowPoint = nextPoint;
                nextPoint = puzzle.moveFrom(previousPoint, nowPoint);
                numberOfSteps++;
            }

            System.out.println();
            System.out.println("Total number of steps for an entire loop = " + numberOfSteps);
            System.out.println("Number of steps to be found = " + numberOfSteps/ 2);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

        } catch (FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}
