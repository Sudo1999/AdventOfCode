package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/* Day 12 Part One
* For each row, count all the different arrangements of operational and broken springs that meet the given criteria.
* What is the sum of those counts?
* */

public class Main {

    /*================================================================================================================*/
    /*                                                 checkRow                                                       */
    /*================================================================================================================*/

    public static int checkRow(String row, List<Integer> springNumbers) {
        int alternatives = 0;
        char charZero = row.charAt(0);
        String alternateRow;

        if (springNumbers.size() == 0) {
            for (int i = 0; i < row.length(); i++) {
                if (row.charAt(i) == '#') {
                    return 0;
                }
            }
            return 1;
        }

        while (row.length() > 1) {
            // Cas du point
            if (charZero == '.') {
                alternateRow = row.substring(1);
                return checkRow(alternateRow, springNumbers);

            // Cas du point d'interrogation
            } else if (charZero == '?') {
                List<Integer> loopSpringNumbers = new ArrayList<>(springNumbers);
                // Remplacement par '.'
                alternateRow = row.substring(1);
                alternatives += checkRow(alternateRow, springNumbers);

                // Remplacement par '#'
                alternateRow = '#' + row.substring(1);
                return alternatives + checkRow(alternateRow, loopSpringNumbers);

            // Cas du dièse
            } else if (charZero == '#') {       // Traitement du groupe de springs dans sa totalité
                int target = springNumbers.get(0);
                int contiguousSprings = 1;
                alternateRow = row;
                for (int i = 1; i < target; i++) {
                    char next = alternateRow.charAt(i);
                    if (next == '.') {          // L'alternative est invalide
                        return 0;
                    }
                    if (next == '?') {          // La seule alternative valide est que ce char soit un '#'
                        alternateRow = alternateRow.substring(0, i) + '#' + alternateRow.substring(i + 1);
                        contiguousSprings++;
                    }
                    if (next == '#') {          // L'alternative reste valide
                        contiguousSprings++;
                    }
                }
                if (contiguousSprings == target) {
                    if (alternateRow.charAt(target) == '.') {     // On a identifié le goupe de springs, on peut passer aux suivants
                        springNumbers.remove(0);
                        alternateRow = '.' + alternateRow.substring(target + 1);
                        return alternatives + checkRow(alternateRow, springNumbers);
                    } else if (alternateRow.charAt(target) == '?') {     // La seule alternative valide est que ce char soit un '.'
                        springNumbers.remove(0);
                        alternateRow = '.' + alternateRow.substring(target + 1);
                        return alternatives + checkRow(alternateRow, springNumbers);
                    } else if (alternateRow.charAt(target) == '#') {    // L'alternative est invalide (trop de #)
                        return 0;
                    }
                }
            }
        }
        return alternatives;
    }

    /*================================================================================================================*/
    /*                                             checkUnfoldedRow                                                   */
    /*================================================================================================================*/

    public static long checkUnfoldedRow(String row, List<Integer> springNumbers, Map<String, Long> memoization) {
        long alternatives = 0;
        char charZero = row.charAt(0);
        String alternateRow;

        String keyMap = row + " " + springNumbers.toString();
        if (memoization.containsKey(keyMap)) {
            return memoization.get(keyMap);
        }

        if (springNumbers.size() == 0) {
            for (int i = 0; i < row.length(); i++) {
                if (row.charAt(i) == '#') {
                    keyMap = row + " " + springNumbers.toString();
                    memoization.put(keyMap, 0L);
                    return 0;
                }
            }
            keyMap = row + " " + springNumbers.toString();
            memoization.put(keyMap, 1L);
            return 1;
        }

        while (row.length() > 1) {
            // Vérification de la capacité de la ligne à contenir les ressorts
            int sumOfSprings = 0;
            for (int value : springNumbers) {
                sumOfSprings += value;
            }
            if (sumOfSprings > row.length()) {
                keyMap = row + " " + springNumbers.toString();
                memoization.put(keyMap, 0L);
                return 0;
            }

            // Cas du point
            if (charZero == '.') {
                alternateRow = row.substring(1);
                keyMap = row + " " + springNumbers.toString();
                long value = checkUnfoldedRow(alternateRow, springNumbers, memoization);
                memoization.put(keyMap, value);
                return value;

            // Cas du point d'interrogation
            } else if (charZero == '?') {
                List<Integer> loopSpringNumbers = new ArrayList<>(springNumbers);
                // Remplacement par '.'
                alternateRow = row.substring(1);
                alternatives += checkUnfoldedRow(alternateRow, springNumbers, memoization);

                // Remplacement par '#'
                alternateRow = '#' + row.substring(1);
                return alternatives + checkUnfoldedRow(alternateRow, loopSpringNumbers, memoization);

            // Cas du dièse
            } else if (charZero == '#') {       // Traitement du groupe de springs dans sa totalité
                int target = springNumbers.get(0);
                int contiguousSprings = 1;
                alternateRow = row;
                for (int i = 1; i < target; i++) {
                    char next = alternateRow.charAt(i);
                    if (next == '.') {          // L'alternative est invalide
                        return 0;
                    }
                    if (next == '?') {          // La seule alternative valide est que ce char soit un '#'
                        alternateRow = alternateRow.substring(0, i) + '#' + alternateRow.substring(i + 1);
                        contiguousSprings++;
                    }
                    if (next == '#') {          // L'alternative reste valide
                        contiguousSprings++;
                    }
                }
                if (contiguousSprings == target) {
                    if (alternateRow.charAt(target) == '.') {     // On a identifié le goupe de springs, on peut passer aux suivants
                        springNumbers.remove(0);
                        alternateRow = '.' + alternateRow.substring(target + 1);
                        return alternatives + checkUnfoldedRow(alternateRow, springNumbers, memoization);
                    } else if (alternateRow.charAt(target) == '?') {     // La seule alternative valide est que ce char soit un '.'
                        springNumbers.remove(0);
                        alternateRow = '.' + alternateRow.substring(target + 1);
                        return alternatives + checkUnfoldedRow(alternateRow, springNumbers, memoization);
                    } else if (alternateRow.charAt(target) == '#') {    // L'alternative est invalide (trop de #)
                        return 0;
                    }
                }
            }
        }
        return alternatives;
    }

    /*================================================================================================================*/
    /*                                                                                                                */
    /*                                                   MAIN                                                         */
    /*                                                                                                                */
    /*================================================================================================================*/

    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-12-hotsprings.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int somme1 = 0;
            long somme2 = 0;
            Map<String, Long> memoization = new HashMap<String, Long>();
            long startTime = System.currentTimeMillis();
            while (scanner.hasNext()) {
                int alternatives1;
                long alternatives2;
                String line = scanner.nextLine();
                String initialRow = line.split(" ")[0];
                initialRow = initialRow + '.';

                String unfoldedRow = line.split(" ")[0];
                for (int i = 0; i < 4; i++) {
                     unfoldedRow += ('?' + line.split(" ")[0]);
                }
                unfoldedRow += '.';

                // La liste springNumbers recense les groupes théoriques de broken springs qui doivent trouver place dans la ligne
                List<Integer> springNumbers = Arrays.stream((line.split(" ")[1]).split(","))
                        .map(Integer::parseInt).collect(Collectors.toList());
                int assignedPoints = 0;
                int spaces = 0;
                for (int i = 0; i < springNumbers.size(); i++) {
                    assignedPoints += springNumbers.get(i);
                    if (i < springNumbers.size()-1) {   // On ajoute à chaque spring théorique (sauf le dernier) le point vide qui le suit
                        spaces++;
                        assignedPoints++;
                    }
                }

                List<Integer> unfoldedNumbers = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    unfoldedNumbers.addAll(springNumbers);
                }
                int assigned = 0;
                for (int i = 0; i < unfoldedNumbers.size(); i++) {
                    assigned += unfoldedNumbers.get(i);
                    if (i < unfoldedNumbers.size()-1) {
                        assigned++;
                    }
                }

                /* Avec l'aide de Reddit :
                 => Generally speaking, "Dynamic Programming" is an approach to writing algorithms when you can solve a subset
                 of the problem and use it as part of the solution for the larger problem.
                 Memoized recursion is one potential implementation of a dynamic programming algorithm.
                 * Une fonction récursive est une fonction qui s'appelle elle-même au cours de son exécution.
                 * La mémoïsation est la mise en cache des valeurs de retour d'une fonction selon ses valeurs d'entrée.
                * */

                // Part One
                if (assignedPoints == initialRow.length()-1) {
                    alternatives1 = 1;
                } else {
                    alternatives1 = checkRow(initialRow, springNumbers);
                }
                somme1 += alternatives1;

                // Part Two
                if (assigned == unfoldedRow.length()-1) {
                    alternatives2 = 1;
                } else {
                    alternatives2 = checkUnfoldedRow(unfoldedRow, unfoldedNumbers, memoization);
                }
                somme2 += alternatives2;
            }

            System.out.println();
            System.out.println("La somme des alternatives est égale à " + somme1);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

            // Part Two
            // To unfold the records, on each row, replace the list of spring conditions with five copies of itself (separated by ?)
            // and replace the list of contiguous groups of damaged springs with five copies of itself (separated by ,).
            // What is the new sum of possible arrangement counts?

            System.out.println("La somme après dépliage de l'enregistrement est égale à " + somme2);

            // => That's the right answer! You are one gold star closer to restoring snow operations.

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}