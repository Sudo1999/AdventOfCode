package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

/* Day 13 Part One
* Find the line of reflection in each of the patterns in your notes.
* What number do you get after summarizing all of your notes?
* */

public class Main {

    public static long horizontalReflection(List<String> pattern) {
        //System.out.println("Inside horizontalReflection");
        long aboveRows = 0;
        List<List<Integer>> pairs = new ArrayList<>();
        boolean includeFirst = false, includeLast = false, reflection;
        for (String row : pattern) {
//            System.out.println(row);
//            System.out.println("Count = " + pattern.stream().filter(s -> s.equals(row)).count());
//            System.out.println("Index in rowsIds = " + pattern.indexOf(row));

            // Il faut gérer les cas où la ligne y est plus de deux fois !!!
            if (pattern.stream().filter(s -> s.equals(row)).count() == 2) {
                List<Integer> pairIds = IntStream.range(0, pattern.size())
                        .filter(index -> Objects.equals(pattern.get(index), row)).boxed().toList();
                pairs.add(pairIds);
                if (pairIds.contains(0)) {
                    includeFirst = true;
                }
                if (pairIds.contains(pattern.size() - 1)) {
                    includeLast = true;
                }
            }
        }
        //System.out.println(pairs);

        if (includeFirst || includeLast) {

            if (includeFirst) {     // Si includeFirst => pairs.get(0).get(0) = 0
                int range = pairs.get(0).get(1) - (pairs.get(0).get(0) - 1);
                //System.out.println("range = " + range);
                reflection = true;
                for (int i = 1; i < range/ 2; i++) {
                    reflection = (reflection && (pairs.get(i).get(0) == (pairs.get(i-1).get(0) + 1))
                        && (pairs.get(i).get(1) == pairs.get(i-1).get(1) - 1));
                }
                if (reflection) {
                    //System.out.println("Il y a une réflexion horizontale");
                    aboveRows = range/ 2;
                    System.out.println("aboveRows = " + aboveRows);
                }
            } else {
                int range = pairs.get(pairs.size()-1).get(1) - (pairs.get(pairs.size()-1).get(0) - 1);
                //System.out.println("range = " + range);
                reflection = true;
                for (int i = pairs.size() - 1; i > (pairs.size() - range/ 2); i--) {
                    reflection = (reflection && (pairs.get(i).get(0) == (pairs.get(i - 1).get(0) - 1))
                            && (pairs.get(i).get(1) == pairs.get(i - 1).get(1) + 1));
                }
                if (reflection) {
                    //System.out.println("Il y a une réflexion horizontale");
                    aboveRows = pattern.size() - range/ 2;
                    System.out.println("aboveRows = " + aboveRows);
                }
            }
        }
        return aboveRows;
    }

    private static long verticalReflection(List<String> pattern) {
        System.out.println("Inside verticalReflection");
        long leftColumns;

        List<String> upsidePattern = new ArrayList<>();
        for (int i = 0; i < pattern.get(0).length(); i++) {
            String upsideLine = "";
            for (String line : pattern) {
                upsideLine += String.valueOf(line.charAt(i));
            }
            upsidePattern.add(upsideLine);
            //System.out.println(upsideLine);
        }
        //System.out.println();

        leftColumns = horizontalReflection(upsidePattern);
        System.out.println("leftColumns = " + leftColumns);
        return leftColumns;
    }

    public static void main(String[] args) {

        long somme = 0;
        String nom_fichier = "src/main/resources/input-test.txt";   // input-13-pointofincidence
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
                    //long verticalResult = verticalReflection(pattern);
                    //somme += (horizontalResult > 0 ? horizontalResult : verticalReflection(pattern));
                    pattern = new ArrayList<>();
                }
            }
            long horizontalResult  = 100 * horizontalReflection(pattern);
            //long verticalResult = verticalReflection(pattern);
            //somme += (horizontalResult > 0 ? horizontalResult : verticalReflection(pattern));

            System.out.println();
            System.out.println("Le résultat de la première partie est " + somme);

            // => 25500
            // That's not the right answer; your answer is too low.

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}