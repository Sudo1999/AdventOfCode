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
        boolean border;
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
        public boolean isBorder() {
            return border;
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
        List<String> input;
        Point startPoint;
        Puzzle(List<String> input) {
            this.input = input;
            this.size = input.size();
            this.length = input.get(0).length();
        }
        public String getLine(int y) {
            return input.get(y);
        }
        public char getPipe(int x, int y) {
            return input.get(y).charAt(x);
        }
        public void setPipe(int x, int y, char pipe) {
            input.get(y).replace(input.get(y).charAt(x), pipe);
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
            Point nowPoint;
            Point previousPoint;
            Point nextPoint;
            List<Point> borderPoints = new ArrayList<>();
            forBreak:
            for (int y = 0; y < puzzle.size; y++) {         // puzzle.size = 142
                for (int x = 0; x < puzzle.length; x++) {   // puzzle.length = 142
                    if (puzzle.getPipe(x, y) == 'S') {
                        startPoint = new Point(x, y, 'S');
                        puzzle.startPoint = startPoint;
                        break forBreak;
                    }
                }
            }
            nowPoint = startPoint;
            nextPoint = puzzle.connectedPoints(nowPoint).get(0);
            borderPoints.add(nextPoint);
            numberOfSteps++;

            // Parcours du labyrinthe
            while (!nextPoint.equals(startPoint)) {
                previousPoint = nowPoint;
                nowPoint = nextPoint;
                nextPoint = puzzle.moveFrom(previousPoint, nowPoint);
                borderPoints.add(nextPoint);
                numberOfSteps++;
            }

            System.out.println();
            System.out.println("Total number of steps for an entire loop = " + numberOfSteps);  // 13402
            System.out.println("Number of steps to be found = " + numberOfSteps/ 2);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

            // Part Two : How many tiles are enclosed by the loop?
            // Le puzzle = 20164 points et la boucle = 13402 points => restent 6762 points en question, moins 564 rangées ajoutées (142x2 + 140x2),
            // ne restent plus que 6198 points en question.

            // Remplacement de S par le signe auquel il se substituait
            char startPipe = '0';
            List<Point> startConnexions = puzzle.connectedPoints(startPoint);
            Point connexion1 = startConnexions.get(0);
            Point connexion2 = startConnexions.get(1);
            int xWay1 = connexion1.x - startPoint.x;
            int yWay1 = connexion1.y - startPoint.y;
            int xWay2 = connexion2.x - startPoint.x;
            int yWay2 = connexion2.y - startPoint.y;

            switch(xWay1) {
                case -1 -> {    // Le point connexion1 est avant sur la ligne => S égale -, ou J, ou 7
                    if (yWay2 == -1) {
                        startPipe = 'J';
                    } else if ( yWay2 == 0) {
                        startPipe = '-';
                    } else if ( yWay2 == 1) {
                        startPipe = '7';
                    }
                }
                case 0 -> {
                    if (yWay1 == -1) {    // Le point connexion1 est en-dessous => S égale |, ou L, ou J
                        if (xWay2 == -1) {
                            startPipe = 'J';
                        } else if ( xWay2 == 0) {
                            startPipe = '|';
                        } else if ( xWay2 == 1) {
                            startPipe = 'L';
                        }
                    }
                    if (yWay1 == 1) {    // Le point connexion1 est en-dessus => S égale |, ou F, ou 7
                        if (xWay2 == -1) {
                            startPipe = '7';
                        } else if ( xWay2 == 0) {
                            startPipe = '|';
                        } else if ( xWay2 == 1) {
                            startPipe = 'F';
                        }
                    }
                }
                case 1 -> {    // Le point connexion1 est après sur la ligne => S égale -, ou L, ou F
                    if (yWay2 == -1) {
                        startPipe = 'L';
                    } else if ( yWay2 == 0) {
                        startPipe = '-';
                    } else if ( yWay2 == 1) {
                        startPipe = 'F';
                    }
                }
            };
            //System.out.println("Le signe que remplace S est " + startPipe);     // S = |

            // Nettoyage du puzzle
            List<String> cleanInput = new ArrayList<>();
            for (int y = 0; y < input.size(); y++) {
                String line = emptyLine;
                for (int x = 0; x < line.length(); x++) {
                    for (Point point : borderPoints) {
                        if (point.x == x && point.y == y) {
                            if (point.pipe == 'S') {
                                point.pipe = startPipe;
                            }
                            line = line.substring(0, x) + point.pipe + line.substring(x+1);
                            break;
                        }
                    }
                }
                cleanInput.add(line);
                //System.out.println(line);   // => La boucle nettoyée
            }

            // Détermination du nombre d'espaces enfermés dans le labyrinthe
            // (A l'aide d'une des idées de solution proposées dans Reddit)

            /* Les idées les plus populaires :
            - I added "filler" rows and columns between every single pair of rows/columns, obviously adjusting the tile according to the loop path
            (adding vertical and horizontal pipes as necessary, dummy tiles otherwise). This made sure that every "outside tile" that was somewhere
            inside the maze had a connection to all other outside tiles. Then it was easy to count up all those adjacent outside tiles.
            - This was my second solution, first was floodfill after straching the map, third was counting horizontally with inside/out
            (with some exception cases) and fourth similar to third but passing diagonally and ignoring L and 7 switches.
            * */

            int numberOfSpaces = 0;
            for (int y = 0; y < cleanInput.size(); y++) {
                String line = cleanInput.get(y);
                boolean inside = false;
                char corner = '0';
                for (int x = 0; x < line.length(); x++) {
                    char symbol = line.charAt(x);
                    switch (symbol) {
                        case '.' -> {
                            if (inside) {
                                numberOfSpaces++;
                            }
                        }
                        case '-' -> {
                        }
                        case '|' -> {
                            inside = !inside;
                        }
                        case 'F' -> {
                            corner = 'F';
                        }
                        case 'L' -> {
                            corner = 'L';
                        }
                        case '7' -> {
                            if (corner == 'F') {    // F7 forme un U qui ne modifie pas le caractère interne ou externe des points de la ligne
                                corner = '0';
                            } else if (corner == 'L') {     // L7 est une part de la frontière verticale (de haut en bas)
                                inside = !inside;
                                corner = '0';
                            }
                        }
                        case 'J' -> {
                            if (corner == 'L') {    // LJ forme un U qui ne modifie pas le caractère interne ou externe des points de la ligne
                                corner = '0';
                            } else  if (corner == 'F') {    // FJ est une part de la frontière verticale (de bas en haut)
                                inside = !inside;
                                corner = '0';
                            }
                        }
                    };
                }
            }
            System.out.println("Le nombre d'espaces enfermés dans la boucle est de " + numberOfSpaces);

            // => That's the right answer! You are one gold star closer to restoring snow operations.

        } catch (FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}
