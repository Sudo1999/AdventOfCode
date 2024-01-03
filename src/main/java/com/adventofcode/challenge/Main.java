package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/* Day 12 Part One
* For each row, count all the different arrangements of operational and broken springs that meet the given criteria.
* What is the sum of those counts?
* */

public class Main {

    public static class Group {
        int index;
        int items;
        public Group(int index, int items) {
            this.index = index;
            this.items = items;
        }
    }

    public static int checkRow(String row, List<Integer> springNumbers) {
        int alternatives = 0;
        char charZero = row.charAt(0);
        String alternateRow;
        System.out.println("Appel de checkRow() avec row = " + row + " et springNumbers = " + springNumbers);

        if (springNumbers.size() == 0) {
            for (int i = 0; i < row.length(); i++) {
                if (row.charAt(i) == '#') {
                    return 0;
                }
            }
            System.out.println("Return 1");
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
                System.out.println("Cas ? => . (alternatives = " + alternatives + ") => on relance un checkRow");
                alternatives += checkRow(alternateRow, springNumbers);

                // Remplacement par '#'
                alternateRow = '#' + row.substring(1);
                System.out.println("Cas ? => # (alternatives = " + alternatives + ") => on relance un checkRow");
                return alternatives + checkRow(alternateRow, loopSpringNumbers);

            // Cas du dièse
            } else if (charZero == '#') {       // Le groupe de springs doit être réglé tout entier à l'intérieur de la condition
                int target = springNumbers.get(0);
                int contiguousSprings = 1;
                alternateRow = row;
                System.out.println("Cas # :");
                for (int i = 1; i < target; i++) {
                    char next = alternateRow.charAt(i);
                    if (next == '.') {          // L'alternative devient invalide
                        System.out.println("Le groupe de # est trop court => Return 0");
                        return 0;
                    }
                    if (next == '?') {          // La seule alternative valide est que ce char soit un '#'
                        alternateRow = alternateRow.substring(0, i) + '#' + alternateRow.substring(i + 1);
                        //System.out.println("Le groupe est dans la boucle for avec un '?' qui devient '#' => alternateRow = " + alternateRow
                                //+ " et contiguousSprings = " + contiguousSprings);
                        contiguousSprings++;
                    }
                    if (next == '#') {          // L'alternative reste valide
                        //System.out.println("Le groupe est dans la boucle for avec un '#' => contiguousSprings = " + contiguousSprings);
                        contiguousSprings++;
                    }
                }
                System.out.println("Le groupe est arrivé à la valeur target");
                if (contiguousSprings == target) {
                    if (alternateRow.charAt(target) == '.') {     // On a identifié le goupe de springs, on peut passer aux suivants
                        springNumbers.remove(0);
                        alternateRow = '.' + alternateRow.substring(target + 1);
                        //System.out.println("Le groupe de # est atteint (alternatives = " + alternatives + "), on relance un checkRow");
                        return alternatives + checkRow(alternateRow, springNumbers);
                    } else if (alternateRow.charAt(target) == '?') {     // La seule alternative valide est que ce char soit un '.'
                        springNumbers.remove(0);
                        alternateRow = '.' + alternateRow.substring(target + 1);
                        //System.out.println("Le groupe de # est suivi d'un '?' qui devient '.' (alternatives = " + alternatives
                                //+ "), on relance un checkRow");
                        return alternatives + checkRow(alternateRow, springNumbers);
                    } else if (alternateRow.charAt(target) == '#') {    // Rien ne se passe, l'alternative est invalide (trop de #)
                        System.out.println("Le groupe de # est trop long => Return 0");
                        return 0;
                    }
                }
            }
        }
        return alternatives;
    }

    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-12-hotsprings.txt";       // input-12-hotsprings
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int somme = 0;
            while (scanner.hasNext()) {
                int alternatives;
                String line = scanner.nextLine();
                String initialRow = line.split(" ")[0];
                initialRow = initialRow + '.';

                // Les variables brokenSum, unknownSum, brokenGroups, et unknownGroups présentent des données de la ligne existante
//                int brokenSum = 0;      // Pour les #
//                int unknownSum = 0;     // Pour les ?
//                List<Group> brokenGroups = new ArrayList<>();       // groupes de un ou plusieurs # consécutifs
//                List<Group> unknownGroups = new ArrayList<>();      // groupes de un ou plusieurs ? consécutifs
//                for (int i = 0; i < initialRow.length(); i++) {
//                    int index = i;
//                    if (initialRow.charAt(i) == '#') {
//                        int items = 1;
//                        while (initialRow.charAt(++i) == '#') {
//                            items++;
//                        }
//                        i--;
//                        System.out.println("brokenGroups => index = " + index + " && items = " + items);
//                        Group group = new Group(index, items);
//                        brokenGroups.add(group);
//                        brokenSum += items;
//                    }
//                    if (initialRow.charAt(i) == '?') {
//                        int items = 1;
//                        while (initialRow.charAt(++i) == '?') {
//                            items++;
//                        }
//                        i--;
//                        System.out.println("unknownGroups => index = " + index + " && items = " + items);
//                        Group group = new Group(index, items);
//                        unknownGroups.add(group);
//                        unknownSum += items;
//                    }
//                }

                // La liste springNumbers recense les groupes théoriques de broken springs qui doivent trouver place dans la ligne
                List<Integer> springNumbers = Arrays.stream((line.split(" ")[1]).split(","))
                        .map(Integer::parseInt).collect(Collectors.toList());
                System.out.println(springNumbers);

                int assignedPoints = 0;
                int spaces = 0;
                for (int i = 0; i < springNumbers.size(); i++) {
                    assignedPoints += springNumbers.get(i);
                    if (i < springNumbers.size()-1) {   // On ajoute à chaque spring théorique (sauf le dernier) le point vide qui le suit
                        spaces++;
                        assignedPoints++;
                    }
                }

//                System.out.println("Nombre de broken points = " + brokenSum);
//                System.out.println("Nombre de unknown points = " + unknownSum);
                System.out.print("Assigned points = " + assignedPoints + " including " + (assignedPoints-spaces) + " broken and " + spaces + " spaces. ");
                System.out.println("Non assigned points = " + (initialRow.length()-1-assignedPoints) + " out of a total of " + (initialRow.length()-1));
                // Le -1 sert à sortir du décompte le point neutre rajouté à la fin

                /* Je n'y arriverai pas toute seule, la solution fait probablement appel à un concept mathématique bien particulier.
                 => Reddit => Generally speaking, "Dynamic Programming" is an approach to writing algorithms when you can solve a subset
                 of the problem and use it as part of the solution for the larger problem.
                 Memoized recursion is one potential implementation of a dynamic programming algorithm.
                 * Une fonction récursive est une fonction qui s'appelle elle-même au cours de son exécution.
                 * La mémoïsation est la mise en cache des valeurs de retour d'une fonction selon ses valeurs d'entrée.
                * */

                if (assignedPoints == initialRow.length()-1) {
                    alternatives = 1;
                } else {
                    alternatives = checkRow(initialRow, springNumbers);
                }
                // Fin du calcul des alternatives pour la ligne en cours
                System.out.println(alternatives);
                System.out.println();
                somme += alternatives;
            }

            System.out.println("La somme finale est égale à " + somme);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}