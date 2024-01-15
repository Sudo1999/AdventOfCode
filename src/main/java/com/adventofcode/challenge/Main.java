package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/* Day 15 Part One
Run the HASH algorithm on each step in the initialization sequence. What is the sum of the results?
* */

public class Main {
    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-15-lenslibrary.txt";     // input-15-lenslibrary.txt
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            long hashSum = 0;
            List<String> steps = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                steps = Arrays.stream(line.split(",")).toList();
            }

            Set<Character> allChars = new HashSet<>();
            for (String step : steps) {
                int value = 0;
                for (int i = 0; i < step.length(); i++) {
                    char sign = step.charAt(i);
                    int ascii = (int)sign;
                    value += ascii;
                    value *= 17;
                    value = value % 256;
                }
                hashSum += value;
            }
            //System.out.println(allChars);
            // Caractères de l'input => [b, c, d, f, g, h, j, k, l, -, m, n, p, q, 1, r, 2, s, 3, t, 4, 5, 6, v, 7, x, 8, 9, z, =]
            // => Des valeurs de 45, 48 à 57, 61, et 97 à 122.

            System.out.println();
            System.out.println(hashSum);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}