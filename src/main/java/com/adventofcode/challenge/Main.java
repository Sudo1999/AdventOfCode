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

    public static long fixedMirrorReflection(List<String> pattern) {
        long fixedResult = 0;
        long patternHorizontal = horizontalReflection(pattern)[0];
        long patternVertical = verticalReflection(pattern)[0];

        for (int rowId = 0; rowId < pattern.size(); rowId++) {
            String initialRow = pattern.get(rowId);
            List<String> fixedPattern = new ArrayList<>(pattern);
            for (int i = 0; i < initialRow.length(); i++) {
                char smudge = initialRow.charAt(i) == '.' ? '#' : '.';
                String fixedRow = initialRow.substring(0, i) + smudge + initialRow.substring(i+1);
                fixedPattern.set(rowId, fixedRow);

                long[] fixedHorizontalTab = horizontalReflection(fixedPattern);
                long fixedHorizontal = (fixedHorizontalTab[0] == patternHorizontal ? fixedHorizontalTab[1] : fixedHorizontalTab[0]);
                if (fixedHorizontal > 0) {
                    fixedResult += 100 * fixedHorizontal;
                }
                long[] fixedVerticalTab = verticalReflection(fixedPattern);
                long fixedVertical = (fixedVerticalTab[0] == patternVertical ? fixedVerticalTab[1] : fixedVerticalTab[0]);
                if (fixedVertical > 0) {
                    fixedResult += fixedVertical;
                }
            }
        }
        return fixedResult/ 2;
    }

    public static long[] horizontalReflection(List<String> pattern) {
        long aboveRows = 0;
        long fixedAboveRows = 0;
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
                    // Dans la première partie il n'y avait qu'une seule solution, mais maintenant par définition il y en a deux
                    if (reflection) {
                        if (aboveRows == 0) {
                            aboveRows = pair.get(0) + ((gap + 1)/ 2);
                        } else if (aboveRows != (pair.get(0) + ((gap + 1)/ 2))) {
                            fixedAboveRows = pair.get(0) + ((gap + 1) / 2);
                        }
                    }
                }
            }
        }
        long[] twoResults = {aboveRows, fixedAboveRows};
        return twoResults;
    }

    private static long[] verticalReflection(List<String> pattern) {
        long[] leftColumns;
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

        long somme1 = 0, somme2 = 0;
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
                    long horizontalResult  = 100 * horizontalReflection(pattern)[0];
                    somme1 += (horizontalResult > 0 ? horizontalResult : verticalReflection(pattern)[0]);
                    somme2 += fixedMirrorReflection(pattern);
                    pattern = new ArrayList<>();
                }
            }
            long horizontalResult  = 100 * horizontalReflection(pattern)[0];
            somme1 += (horizontalResult > 0 ? horizontalResult : verticalReflection(pattern)[0]);
            somme2 += fixedMirrorReflection(pattern);

            System.out.println();
            System.out.println("Le résultat de la première partie est " + somme1);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

            // Part Two :
            // In each pattern, fix the smudge and find the different line of reflection.
            // What number do you get after summarizing the new reflection line in each pattern in your notes?

            System.out.println();
            System.out.println("Le résultat de la seconde partie est " + somme2);

            // That's the right answer! You are one gold star closer to restoring snow operations.

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}