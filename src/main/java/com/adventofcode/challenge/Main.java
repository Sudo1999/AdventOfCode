package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* Day 11 Part One :
After expanding the universe, find the length of the shortest path between every pair of galaxies.
What is the sum of these lengths?
* */

public class Main {

    public static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Galaxy {
        static int id;
        int x, y;
        Point point = new Point(x, y);
        Galaxy(int x, int y) {
            this.x = x;
            this.y = y;
            this.id = id++;
        }
        Galaxy(Point point) {
            this.x = point.x;
            this.y = point.y;
            this.id = id++;
        }
    }

    public static void main(String[] args) {

        // Somme de Gauss : Due to the great Karl Friedrich Gauss, one of the most important mathematicians of all times,
        // we have a neat, simple formula which gives us the sum of the first n integers:
        //      Sum = n * (n+1)/ 2
        // (pour obtenir le nombre de paires de galaxies possibles)

        String nom_fichier = "src/main/resources/input-test.txt";   // input-11-expansion.txt
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int idLine = 0;
            int numberOfGalaxies = 0;
            int numberOfPairs = 0;
            List<Galaxy> galaxies = new ArrayList<>();
            List<Point> universe = new ArrayList<>();
            List<String> puzzle = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                numberOfGalaxies += line.chars().filter(c -> c == '#').count();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '#') {
                        Point point = new Point(i, idLine);
                        Galaxy galaxy = new Galaxy(point);
                        galaxies.add(galaxy);
                    }
                }
                if (line.chars().filter(c -> c == '#').count() == 0) {
                    puzzle.add(line);
                }
                puzzle.add(line);
                idLine++;
            }
            List<Integer> columnList = new ArrayList<>();
            for (int i = 0; i < puzzle.get(0).length(); i++) {
                columnList.add(i);
            }
            for (String line : puzzle) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '#') {
                        columnList.set(i, -1);
                    }
                }
            }
            List<String> expandedPuzzle = new ArrayList<>();
            columnList = columnList.stream().filter(c -> !c.equals(-1)).toList();
            //System.out.println(columnList);
            for (String line : puzzle) {
                String newLine = "";
                int previous = 0;
                for (int colIndex : columnList) {
                    newLine += line.substring(previous, colIndex+1) + line.charAt(colIndex);
                    previous = colIndex+1;
                }
                if (previous <= line.length()) {
                    newLine += line.substring(previous, line.length());
                }
                expandedPuzzle.add(newLine);
                //System.out.println(newLine);
            }
            numberOfPairs = numberOfGalaxies * (numberOfGalaxies-1)/ 2;
            System.out.println("The number of galaxies is " + numberOfGalaxies);  // => The number of galaxies is 445
            System.out.println("The number of pairs is " + numberOfPairs);  // => The number of pairs is 98790
            System.out.print("The expanded universe is " + expandedPuzzle.get(0).length() + " wide and " + expandedPuzzle.size() + " long. ");
            System.out.println("It contains " + expandedPuzzle.get(0).length()*expandedPuzzle.size() + " points.");
            // => The expanded universe is 146 wide and 145 long. It contains 21170 points.

            System.out.println(galaxies);   // Voir si l'id s'incrémente bien.



            //System.out.println("Hello Advent of code !");

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}