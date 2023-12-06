package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/* Day 4 Part One
The first match makes the card worth one point and each match after the first doubles the point value of that card.
* */

public class Main {
    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-4-scratchcards.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int somme = 0;
            while (scanner.hasNext()) {
                int match = 0, points = 0;
                String line = scanner.nextLine();
                line = line.replaceAll("  ", " ");
                String numbersLine = (line.split(": "))[1];
                String[] numbersTab = numbersLine.split(" ");

                int[] winningNumbers = new int[10];
                int[] personalNumbers = new int[25];
                for (int i = 0; i < winningNumbers.length; i++) {
                    winningNumbers[i] = Integer.parseInt(numbersTab[i]);
                }
                for (int i = 0; i < personalNumbers.length; i++) {
                    personalNumbers[i] = Integer.parseInt(numbersTab[i+11]);
                }

                for(int winningNumber : winningNumbers) {
                    for(int i = 0; i < personalNumbers.length; i++) {
                        if (winningNumber == personalNumbers[i]) {
                            match++;
                        }
                    }
                }
                if (match == 1) {
                    points = 1;
                } else if (match > 1) {
                    points = (int)Math.pow(2,(match-1));
                }
                somme += points;
            }

            // => 19855
            // => That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

            System.out.println(somme);

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}