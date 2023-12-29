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
        int x, y, id;
        Galaxy(Point point, int id) {
            this.x = point.x;
            this.y = point.y;
            this.id = id;
        }
    }

    public static void main(String[] args) {

        // Somme de Gauss : Due to the great Karl Friedrich Gauss, one of the most important mathematicians of all times,
        // we have a neat, simple formula which gives us the sum of the first n integers:
        //      Sum = n * (n+1)/ 2
        // (pour obtenir le nombre de paires de galaxies possibles)

        String nom_fichier = "src/main/resources/input-11-expansion.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int  lineId = 0, galaxyId = 0;
            int numberOfGalaxies = 0;
            int numberOfPairs;
            List<Galaxy> galaxies = new ArrayList<>();
            List<Integer> columnList = new ArrayList<>();
            List<Integer> lineList = new ArrayList<>();
            List<String> puzzle = new ArrayList<>();
            List<String> initialPuzzle = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                numberOfGalaxies += line.chars().filter(c -> c == '#').count();
                if (line.chars().filter(c -> c == '#').count() == 0) {
                    puzzle.add(line);
                    lineList.add(lineId);
                }
                initialPuzzle.add(line);
                puzzle.add(line);
                lineId++;
            }
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
            for (String line : puzzle) {
                StringBuilder newLine = new StringBuilder();
                int previous = 0;
                for (int colIndex : columnList) {
                    newLine.append(line, previous, colIndex + 1).append(line.charAt(colIndex));
                    previous = colIndex+1;
                }
                if (previous <= line.length()) {
                    newLine.append(line.substring(previous));
                }
                expandedPuzzle.add(newLine.toString());
                //System.out.println(newLine);    // => Le puzzle après la première expansion
            }

            lineId = 0;
            for (String line : expandedPuzzle) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '#') {
                        Point point = new Point(i, lineId);
                        Galaxy galaxy = new Galaxy(point, galaxyId);
                        galaxies.add(galaxy);
                        galaxyId++;
                    }
                }
                lineId++;
            }

            numberOfPairs = numberOfGalaxies * (numberOfGalaxies-1)/ 2;
            System.out.println("The number of galaxies is " + numberOfGalaxies);  // => The number of galaxies is 445
            System.out.println("The number of pairs is " + numberOfPairs);  // => The number of pairs is 98790
            System.out.print("The expanded universe is " + expandedPuzzle.get(0).length() + " wide and " + expandedPuzzle.size() + " long. ");
            System.out.println("It contains " + expandedPuzzle.get(0).length()*expandedPuzzle.size() + " points.");

            int smallSomme = 0;
            for (Galaxy galaxy : galaxies) {
                int id = galaxy.id;
                for (int i = id+1; i < galaxies.size(); i++) {
                    int xGap = galaxies.get(i).x - galaxy.x;
                    int yGap = galaxies.get(i).y - galaxy.y;
                    int shortWay = Math.abs(xGap) + Math.abs(yGap);
                    smallSomme += shortWay;
                }
            }
            System.out.println("La somme de tous les plus courts chemins est égale à " + smallSomme);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

            // Part Two : Instead of the expansion you did before, make each empty row or column one million times larger.
            // Then find the length of the shortest path between every pair of galaxies. What is the sum of these lengths?

            // Dans le puzzle l'expansion touche 6 colonnes [20, 24, 37, 79, 83, 129] et 5 lignes [47, 75, 85, 89, 121].

            // Retour aux coordonnées du puzzle initial
            lineId = 0;
            galaxyId = 0;
            galaxies = new ArrayList<>();
            for (String line : initialPuzzle) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '#') {
                        Point point = new Point(i, lineId);
                        Galaxy galaxy = new Galaxy(point, galaxyId);
                        galaxies.add(galaxy);
                        galaxyId++;
                    }
                }
                lineId++;
            }

            long bigSomme = 0;
            System.out.println(columnList);     // => Coordonnées avant expansion des colonnes concernées
            System.out.println(lineList);       // => Coordonnées avant expansion des lignes concernées
            for (Galaxy galaxy : galaxies) {
                int id = galaxy.id;
                for (int i = id+1; i < galaxies.size(); i++) {
                    List<Integer> gaps;

                    // Colonnes
                    long expandedCols;
                    long xGap = galaxies.get(i).x - galaxy.x;
                    gaps = new ArrayList<>();
                    if (xGap > 0) {
                        for (int col = galaxy.x + 1; col < galaxies.get(i).x; col++) {
                            gaps.add(col);
                        }
                    }
                    if (xGap < 0) {
                        for (int col = galaxies.get(i).x + 1; col < galaxy.x; col++) {
                            gaps.add(col);
                        }
                    }
                    expandedCols = gaps.stream().filter(columnList::contains).count();
                    xGap = Math.abs(xGap);
                    xGap += (expandedCols*1000000)-expandedCols;

                    // Lignes
                    long expandedLines;
                    long yGap = galaxies.get(i).y - galaxy.y;
                    gaps = new ArrayList<>();
                    if (yGap > 0) {
                        for (int line = galaxy.y + 1; line < galaxies.get(i).y; line++) {
                            gaps.add(line);
                        }
                    }
//                    if (yGap < 0) {     // En fait ce cas n'arrive jamais
//                        for (int line = galaxies.get(i).y + 1; line < galaxy.y; line++) {
//                            gaps.add(line);
//                        }
//                    }
                    expandedLines = gaps.stream().filter(lineList::contains).count();
                    yGap = Math.abs(yGap);
                    yGap += (expandedLines*1000000)-expandedLines;

                    // Somme
                    long wayLength = xGap + yGap;
                    bigSomme += wayLength;
                }
            }
            System.out.println("La somme après la seconde expansion est de " + bigSomme);

            // => That's the right answer! You are one gold star closer to restoring snow operations.

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}