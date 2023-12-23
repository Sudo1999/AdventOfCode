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

        String nom_fichier = "src/main/resources/input-9-mirage.txt";   // input-9-mirage.txt
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int somme = 0;
            List<Integer> dataValues;
            List<Integer> differenceValues;
            List<Integer> stepValues;
            List<Integer> onlyZeroValues;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] valuesTab = line.split(" ");
                dataValues = Arrays.stream(valuesTab).map(Integer::parseInt).toList();
                stepValues = new ArrayList<>();

                boolean finalStep = false;
                while (!finalStep) {
                    stepValues.add(dataValues.get(dataValues.size()-1));
                    differenceValues = new ArrayList<>();
                    for (int i = 0; i < dataValues.size()-1; i++) {     // dataLine.size() = 21
                        differenceValues.add(i, (dataValues.get(i+1) - dataValues.get(i)));
                    }
                    dataValues = differenceValues;

                    onlyZeroValues = new ArrayList<>(dataValues);
                    onlyZeroValues.removeIf(value -> value.equals(0));
                    finalStep = onlyZeroValues.isEmpty();
                }
                int expected = 0;
                for (int i = stepValues.size()-1; i >= 0; i--) {
                    expected += stepValues.get(i);
                }
                somme += expected;
            }
            System.out.println();
            System.out.println("Sum of the extrapolated values = " + somme);

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}