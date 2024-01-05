package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/* Day 13 Part One
* Find the line of reflection in each of the patterns in your notes.
* What number do you get after summarizing all of your notes?
* */

public class Main {

    public static long horizontalReflection(List<String> pattern) {
        //System.out.println("Inside horizontalReflection");
        long aboveRows = 0;
        SortedSet<Integer> rowsIds = new TreeSet<>();
        for (String row : pattern) {
//            System.out.println(row);
//            System.out.println("Count = " + pattern.stream().filter(s -> s.equals(row)).count());
//            System.out.println("Index = " + pattern.indexOf(row));

            if (pattern.stream().filter(s -> s.equals(row)).count() == 2) {
                rowsIds.add(pattern.indexOf(row));
            }
        }
        // C'est pas bon !!! Il y a des milieux qui peuvent être situés à une extrémité moins une rangée
        if (rowsIds.size() == pattern.size()/ 2) {    // En effet 7/2 = 3 && 6/2 = 3
            aboveRows = rowsIds.last() + 1;
        }
        return 100 * aboveRows;
    }

    private static long verticalReflection(List<String> pattern) {
        //System.out.println("Inside verticalReflection");
        long leftColumns = 0;

        // Les lignes les plus courtes ont sept caractères
        char[] firstRow = pattern.get(0).toCharArray();
        char firstChar = firstRow[0];
        char secondChar = firstRow[1];
        char thirdChar = firstRow[2];
        char antepenultimateChar = firstRow[firstRow.length-3];
        char penultimateChar = firstRow[firstRow.length-2];
        char lastChar = firstRow[firstRow.length-1];

        int columnId;
        boolean verticalSymmetry = false;
        if (firstChar == lastChar) {
            if (secondChar == penultimateChar) {

            }
        } else if (firstChar == penultimateChar) {

        } else if (secondChar == lastChar) {

        }

        List<char[]> rowsChars = new ArrayList<>();
        for (String row : pattern) {
            char[] rowChars = row.toCharArray();
            rowsChars.add(rowChars);
        }

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
                    long horizontalResult  = horizontalReflection(pattern);
                    somme += (horizontalResult > 0 ? horizontalResult : verticalReflection(pattern));
                    pattern = new ArrayList<>();
                }
            }
            long horizontalResult  = horizontalReflection(pattern);
            somme += (horizontalResult > 0 ? horizontalResult : verticalReflection(pattern));

            System.out.println();
            System.out.println("Le résultat de la première partie est " + somme);

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}