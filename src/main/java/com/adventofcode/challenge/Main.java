package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/* Day 4 Part One
The first match makes the card worth one point and each match after the first doubles the point value of that card.
* */

public class Main {
    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-4-scratchcards.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            /*
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

                for (int winningNumber : winningNumbers) {
                    for (int i = 0; i < personalNumbers.length; i++) {
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
            */

            // Part Two : Including the original set of scratchcards, how many total scratchcards do you end up with ?

            LinkedHashMap<Integer, Integer> lineScore = new LinkedHashMap<>();
            ArrayList<int[]> instanceList = new ArrayList();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                int idLine;
                if (String.valueOf(line.charAt(5)).equals(" ") && String.valueOf(line.charAt(6)).equals(" ")) {
                    idLine = Integer.parseInt(String.valueOf(line.charAt(7)));
                } else if (String.valueOf(line.charAt(5)).equals(" ")) {
                    idLine = Integer.parseInt(String.valueOf(line.charAt(6)) + String.valueOf(line.charAt(7)));
                } else {
                    idLine = Integer.parseInt(String.valueOf(line.charAt(5)) + String.valueOf(line.charAt(6)) + String.valueOf(line.charAt(7)));
                }

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

                int match = 0;
                for (int winningNumber : winningNumbers) {
                    for (int personalNumber : personalNumbers) {
                        if (winningNumber == personalNumber) {
                            match++;
                        }
                    }
                }
                lineScore.put(idLine, match);
            }
            // Fin du scanner : Toutes les cartes ont été lues et leur score enregistré

            for (int id : lineScore.keySet()) {
                int[] oneLineScore = {id, lineScore.get(id)};
                instanceList.add(oneLineScore);

                int instances = 0;
                for (int i = 0; i < instanceList.size(); i++) {
                    if (instanceList.get(i)[0] == id) {
                        instances++;
                    }
                }
                while (instances > 0) {
                    for (int i = 1; i <= lineScore.get(id); i++) {
                        oneLineScore = new int[2];
                        oneLineScore[0] = id + i;
                        oneLineScore[1] = lineScore.get(id + i);
                        instanceList.add(oneLineScore);
                    }
                    instances--;
                }
            }
            System.out.println(instanceList.toArray().length);

            // => 10378710
            // => That's the right answer! You are one gold star closer to restoring snow operations.

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}