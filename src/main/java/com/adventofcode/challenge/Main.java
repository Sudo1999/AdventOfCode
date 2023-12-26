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
    public static void main(String[] args) {

        // Somme de Gauss : Due to the great Karl Friedrich Gauss, one of the most important mathematicians of all times,
        // we have a neat, simple formula which gives us the sum of the first n integers:
        //      Sum = n * (n+1)/ 2
        // (pour obtenir le nombre de paires de galaxies possibles)

        String nom_fichier = "src/main/resources/input-test.txt";   // input-11-expansion.txt
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int numberOfGalaxies = 0;
            List<String> puzzle = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                numberOfGalaxies += line.chars().filter(c -> c == '#').count();
                if (line.chars().filter(c -> c == '#').count() == 0) {
                    puzzle.add(line);
                }
                puzzle.add(line);
            }

            System.out.println("Hello Advent of code !");

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}