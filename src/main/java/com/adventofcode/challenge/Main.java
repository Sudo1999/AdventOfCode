package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/* Day 12 Part One
* For each row, count all of the different arrangements of operational and broken springs that meet the given criteria.
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

    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-test.txt";       // input-12-hotsprings
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int somme = 0;
            while (scanner.hasNext()) {
                int alternatives = 0;
                String line = scanner.nextLine();
                String initialRow = line.split(" ")[0];
                initialRow = initialRow + '.';

                // Les variables brokenSum, unknownSum, brokenGroups, unknownGroups, et nonEmptyGroups présentent des données de la ligne existante
                int brokenSum = 0;
                int unknownSum = 0;
                List<Group> brokenGroups = new ArrayList<>();
                List<Group> unknownGroups = new ArrayList<>();
                List<Group> nonEmptyGroups = new ArrayList<>();
                for (int i = 0; i < initialRow.length(); i++) {
                    int index = i;
                    if (initialRow.charAt(i) == '#') {
                        int items = 1;
                        while (initialRow.charAt(++i) == '#') {
                            items++;
                        }
                        i--;
                        System.out.println("brokenGroups => index = " + index + " && items = " + items);
                        Group group = new Group(index, items);
                        brokenGroups.add(group);
                        brokenSum += items;
                    }
                    if (initialRow.charAt(i) == '?') {
                        int items = 1;
                        while (initialRow.charAt(++i) == '?') {
                            items++;
                        }
                        i--;
                        System.out.println("unknownGroups => index = " + index + " && items = " + items);
                        Group group = new Group(index, items);
                        unknownGroups.add(group);
                        unknownSum += items;
                    }
                }

                for (int i = 0; i < initialRow.length(); i++) {
                    int index = i;
                    if (initialRow.charAt(i) != '.') {
                        int items = 1;
                        while (initialRow.charAt(++i) != '.') {
                            items++;
                        }
                        i--;
                        System.out.println("nonEmptyGroup ===> index = " + index + " && items = " + items);
                        Group group = new Group(i, items);
                        nonEmptyGroups.add(group);
                    }
                }

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

                System.out.println("Nombre de broken points = " + brokenSum);
                System.out.println("Nombre de unknown points = " + unknownSum);
                System.out.println("Assigned points = " + assignedPoints + " including " + (assignedPoints - spaces) + " broken and "
                        + spaces + " spaces");
                System.out.println("Non assigned points = " + (initialRow.length() - 1 - assignedPoints) + " sur " + (initialRow.length() - 1));
                // Le -1 sert à sortir du décompte le point neutre rajouté à la fin
                System.out.println();

                /* Je n'y arriverai pas toute seule, la solution fait probablement appel à un concept mathématique bien particulier.
                    => Reddit => Generally speaking, "Dynamic Programming" is an approach to writing algorithms when you can solve a subset
                     of the problem and use it as part of the solution for the larger problem.
                     Memoized recursion is one potential implementation of a dynamic programming algorithm.
                     * En informatique, la mémoïsation est la mise en cache des valeurs de retour d'une fonction selon ses valeurs d'entrée.
                * */

                if (assignedPoints == initialRow.length()-1) {
                    alternatives = 1;
                } else {
                    List<Integer> stillToBeFound = new ArrayList<>(springNumbers);
                    for (int number : springNumbers) {
                        for (int i = 0; i < initialRow.length(); i++) {
                            if (initialRow.charAt(i) != '.') {
                                if (initialRow.charAt(i) == '#') {
                                    final int x = i;
                                    int springLength = brokenGroups.stream().filter(g -> g.index == x).toList().get(0).items;
                                    if (number == springLength) {
                                        if (stillToBeFound.size() > 0) {
                                            stillToBeFound.remove(0);
                                        }
                                        i += (springLength - 1);
                                        continue;
                                    } else {    // number est forcément supérieur à springLength, et le caractère d'après est forcément un '?'
                                        i += (springLength - 1);
                                        while (number > springLength++) {
                                            i++;
                                        }
                                        if (stillToBeFound.size() > 0) {
                                            stillToBeFound.remove(0);
                                        }
                                    }
                                } else if (initialRow.charAt(i) == '?') {

                                }
                            }
                        }
                    }
                } // Fin du calcul des alternatives pour la ligne en cours
                somme += alternatives;
            }

            System.out.println(somme);

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}