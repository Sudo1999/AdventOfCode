package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

/* Day 13 Part One
* Find the line of reflection in each of the patterns in your notes.
* What number do you get after summarizing all of your notes?
* */

public class Main {

    public static long horizontalReflection(List<String> pattern) {
        long aboveRows = 0;
        Set<List<Integer>> pairs = new HashSet<>();
        Set<List<Integer>> endPairs  = new HashSet<>();
        boolean includeEnd = false, reflection = false;

        for (String row : pattern) {
            if (pattern.stream().filter(s -> s.equals(row)).count() > 2) {
                List<Integer> pairIds = IntStream.range(0, pattern.size())
                        .filter(index -> Objects.equals(pattern.get(index), row)).boxed().toList();
                for (int i = 0; i < pairIds.size(); i++) {
                    for (int n = i+1; n < pairIds.size(); n++) {
                        List<Integer> binomial = Arrays.asList(pairIds.get(i), pairIds.get(n));
                        if (binomial.contains(0) || binomial.contains(pattern.size() - 1)) {
                            includeEnd = true;
                            endPairs.add(binomial);
                        }
                        pairs.add(binomial);
                    }
                }
            }
            if (pattern.stream().filter(s -> s.equals(row)).count() == 2) {
                List<Integer> binomial = IntStream.range(0, pattern.size())
                        .filter(index -> Objects.equals(pattern.get(index), row)).boxed().toList();
                if (binomial.contains(0) || binomial.contains(pattern.size() - 1)) {
                    includeEnd = true;
                    endPairs.add(binomial);
                }
                pairs.add(binomial);
            }
        }

        if (includeEnd) {
            for (List<Integer> pair : endPairs) {
                reflection = true;
                int gap = pair.get(1) - pair.get(0);
                if (gap % 2 > 0) {
                    for (int i = 1; i < ((gap + 1)/ 2); i++) {
                        int nextMin = pair.get(0) + i;
                        int nextMax = pair.get(1) - i;
                        List<Integer> nextPair = Arrays.asList(nextMin, nextMax);
                        reflection = (reflection && pairs.contains(nextPair));
                    }
                    if (reflection) {
                        aboveRows = pair.get(0) + ((gap + 1)/ 2);
                        break;
                    }
                }
            }
        }
        return aboveRows;
    }

    private static long verticalReflection(List<String> pattern) {
        long leftColumns;
        List<String> upsidePattern = new ArrayList<>();
        for (int i = 0; i < pattern.get(0).length(); i++) {
            String upsideLine = "";
            for (String line : pattern) {
                upsideLine += String.valueOf(line.charAt(i));
            }
            upsidePattern.add(upsideLine);
        }
        leftColumns = horizontalReflection(upsidePattern);
        return leftColumns;
    }

    public static void main(String[] args) {

        long somme = 0;
        String nom_fichier = "src/main/resources/input-13-pointofincidence.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            List<String> pattern = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.length() > 0) {
                    pattern.add(line);
                } else {
                    // To summarize your pattern notes, add up the number of columns to the left of each vertical line of reflection;
                    // to that, also add 100 multiplied by the number of rows above each horizontal line of reflection.
                    long horizontalResult  = 100 * horizontalReflection(pattern);
                    somme += (horizontalResult > 0 ? horizontalResult : verticalReflection(pattern));
                    pattern = new ArrayList<>();
                }
            }
            long horizontalResult  = 100 * horizontalReflection(pattern);
            somme += (horizontalResult > 0 ? horizontalResult : verticalReflection(pattern));

            System.out.println();
            System.out.println("Le résultat de la première partie est " + somme);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

            // Part Two :
            // In each pattern, fix the smudge and find the different line of reflection.
            // What number do you get after summarizing the new reflection line in each pattern in your notes?

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}