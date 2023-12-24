package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/* Day 9 Part One :
Analyze your OASIS report and extrapolate the next value for each history. What is the sum of these extrapolated values?
* */

public class Main {
    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-9-mirage.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int somme1 = 0, somme2 = 0;
            List<Integer> dataValues;
            List<Integer> differenceValues;
            List<Integer> stepAfterValues;
            List<Integer> stepBeforeValues;
            List<Integer> onlyZeroValues;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] valuesTab = line.split(" ");
                dataValues = Arrays.stream(valuesTab).map(Integer::parseInt).toList();
                stepAfterValues = new ArrayList<>();
                stepBeforeValues = new ArrayList<>();

                boolean finalStep = false;
                while (!finalStep) {
                    stepAfterValues.add(dataValues.get(dataValues.size()-1));
                    stepBeforeValues.add(dataValues.get(0));
                    differenceValues = new ArrayList<>();
                    for (int i = 0; i < dataValues.size()-1; i++) {     // dataLine.size() = 21
                        differenceValues.add(i, (dataValues.get(i+1) - dataValues.get(i)));
                    }
                    dataValues = differenceValues;

                    onlyZeroValues = new ArrayList<>(dataValues);
                    onlyZeroValues.removeIf(value -> value.equals(0));
                    finalStep = onlyZeroValues.isEmpty();
                }

                int expectedAfter = 0;
                for (int i = stepAfterValues.size()-1; i >= 0; i--) {
                    expectedAfter += stepAfterValues.get(i);
                }
                somme1 += expectedAfter;

                int expectedBefore = 0;
                for (int i = stepBeforeValues.size()-1; i >= 0; i--) {
                    expectedBefore = stepBeforeValues.get(i) - expectedBefore;
                }
                somme2 += expectedBefore;
            }
            System.out.println();
            System.out.println("Sum of the extrapolated values after the line = " + somme1);
            System.out.println("Sum of the extrapolated values before the line = " + somme2);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

            // Part Two : Rather than adding a zero to the end and filling in the next values of each previous sequence, you should instead
            //  add a zero to the beginning of your sequence of zeroes, then fill in new first values for each previous sequence.
            // Extrapolating the previous value for each history, what is the sum of these extrapolated values?

            // That's the right answer! You are one gold star closer to restoring snow operations.

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}