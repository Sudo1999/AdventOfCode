package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/* Day 8 Part One
Starting at AAA, follow the left/right instructions. How many steps are required to reach ZZZ?
* */

public class Main {

    public static boolean allNodesReached(List<String> directions, int nbOfNodes) {
        int nodeOk = 0;
        for (String direction : directions) {
            if (direction.endsWith("Z")) {
                nodeOk++;
            }
        }
        return nodeOk == nbOfNodes;
    }

    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-8-wasteland.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            // Le point d'entrée AAA se trouve en ligne 387
            // La référence à l'arrivée en ZZZ se trouve en ligne 291 : PSS = (FMH, ZZZ)

            int idLine = 0;
            List<String> instructions = new ArrayList<>();
            Map<String, String[]> network = new HashMap<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (idLine == 0) {
                    for (int i = 0; i < line.length(); i++) {
                        instructions.add(String.valueOf(line.charAt(i)));
                    }
                    idLine++;
                } else if (line.length() > 0) {
                    String entry = line.split(" ")[0];
                    String[] directions = new String[2];
                    directions[0] = String.valueOf(line.charAt(7)) + line.charAt(8) + line.charAt(9);
                    directions[1] = String.valueOf(line.charAt(12)) + line.charAt(13) + line.charAt(14);
                    network.put(entry, directions);
                }
            }   // Fin du scan

            /*
            int step = 0;
            String direction = "";
            String[] choice = network.get("AAA");
            while (!direction.equals("ZZZ")) {
                for (String letter : instructions) {
                    if (!direction.equals("ZZZ")) {
                        if (letter.equals("L")) {
                            direction = choice[0];
                        } else {
                            direction = choice[1];
                        }
                        choice = network.get(direction);
                        step++;
                    }
                }
            }*/

            // Part Two : Simultaneously start on every node that ends with A. How many steps does it take before you're only on nodes that end with Z?

            List<String> aEndEntryStrings = new ArrayList<>();
            List<String> zEndEntryStrings = new ArrayList<>();
            for (String entry : network.keySet()) {
                if (entry.endsWith("A")) {
                    aEndEntryStrings.add(entry);
                }
                if (entry.endsWith("Z")) {
                    zEndEntryStrings.add(entry);
                }
            }
            // Entrées finissant par A => MCA AAA VPA LGA NLA DCA
            // Entrées finissant par Z => FMZ DMZ SLZ VNZ ZZZ TNZ

            /*
            long step = 0;
            int nbOfNodes = aEndEntryStrings.size();
            List<String> directions = new ArrayList<>();
            List<String[]> choices = new ArrayList<>();
            for (int i = 0; i < nbOfNodes; i++) {
                directions.add(i, "");
                choices.add(i, network.get(aEndEntryStrings.get(i)));
            }
            while (!allNodesReached(directions, nbOfNodes)) {
                for (String letter : instructions) {
                    if(!allNodesReached(directions, nbOfNodes)) {
                        for (int i = 0; i < nbOfNodes; i++) {
                            if (letter.equals("L")) {
                                directions.set(i, choices.get(i)[0]);
                            } else {
                                directions.set(i, choices.get(i)[1]);
                            }
                            choices.set(i, network.get(directions.get(i)));
                        }
                        step++;
                        System.out.println(step);
                    }
                }
            }
            System.out.println("Nombre d'étapes = " + step);

            // Solution Part Two => La logique est correcte (le test fonctionne) mais la solution n'est pas du tout adaptée, elle est trop longue.
            // Abandon après avoir laissé tourner pendant plus de 20h. D'après les données, il me faudrait plus de six ans avec cet ordinateur.
            // Tant pis, je vais chercher une idée sur Reddit, et elle consiste à calculer le PPCM (LCM = Least Common Multiple)...
            */

            long step = 0;
            List<Long> aEndEntrySteps = new ArrayList<>();
            List<String> aEndEntryReachedNodes = new ArrayList<>();
            for (String entry : aEndEntryStrings) {
                long aEndEntryStep = 0;
                String direction = "";
                String[] choice = network.get(entry);
                while (!direction.endsWith("Z")) {
                    for (String letter : instructions) {
                        if (!direction.endsWith("Z")) {
                            if (letter.equals("L")) {
                                direction = choice[0];
                            } else {
                                direction = choice[1];
                            }
                            choice = network.get(direction);
                            aEndEntryStep++;
                        }
                    }
                }
                aEndEntrySteps.add(aEndEntryStep);
                aEndEntryReachedNodes.add(direction);
            }

            // Résultats :
            // 16343 steps pour l'entrée MCA (i = 0) => TNZ
            // 16897 steps pour l'entrée AAA (i = 1) => ZZZ
            // 21883 steps pour l'entrée VPA (i = 2) => DMZ
            // 18559 steps pour l'entrée LGA (i = 3) => SLZ
            // 11911 steps pour l'entrée NLA (i = 4) => VNZ
            // 20221 steps pour l'entrée DCA (i = 5) => FMZ

            /*
            aEndEntrySteps = new ArrayList<>();
            aEndEntryReachedNodes = new ArrayList<>();
            for (String entry : aEndEntryStrings) {
                int doubleBoucle = 0;
                long aEndEntryStep = 0;
                String direction = "";
                String[] choice = network.get(entry);
                while (doubleBoucle < 2) {
                    for (String letter : instructions) {
                        if (doubleBoucle < 2) {
                            if (letter.equals("L")) {
                                direction = choice[0];
                            } else {
                                direction = choice[1];
                            }
                            choice = network.get(direction);
                            aEndEntryStep++;
                            if (direction.endsWith("Z")) {
                                doubleBoucle++;
                            }
                        }
                    }
                }
                aEndEntrySteps.add(aEndEntryStep);
                aEndEntryReachedNodes.add(direction);
            }
            // int i = 0;
            // for (long aEntryStep : aEndEntrySteps) {
            //     System.out.println(aEndEntrySteps.get(i) + " steps pour l'entrée " + aEndEntryStrings.get(i) + " (i = " + i
            //             + ") => " + aEndEntryReachedNodes.get(i++));
            // }

            // => Bien qu'on ne revienne pas au même point de départ, les boucles gardent la même longueur par la suite :
            // 32686 steps pour l'entrée MCA (i = 0) => TNZ
            // 33794 steps pour l'entrée AAA (i = 1) => ZZZ
            // 43766 steps pour l'entrée VPA (i = 2) => DMZ
            // 37118 steps pour l'entrée LGA (i = 3) => SLZ
            // 23822 steps pour l'entrée NLA (i = 4) => VNZ
            // 40442 steps pour l'entrée DCA (i = 5) => FMZ
            */

            // Calcul du plus petit multiple commun :

            long ppcm = 0;
            /*
            //long firstInteger = aEndEntrySteps.get(0);
            long firstInteger = aEndEntrySteps.get(3);
            //for (int i = 0; i < 2; i++) {
            for (int i = 3; i < 5; i++) {
                int n = 1;
                boolean founded = false;
                List<Long> firstMultiples = new ArrayList<>();
                List<Long> secondMultiples = new ArrayList<>();
                while (!founded) {
                    firstMultiples.add(firstInteger * n);
                    secondMultiples.add(aEndEntrySteps.get(i+1) * n);
                    n++;
                    forBreak:
                    for (long firstMultiple : firstMultiples) {
                        for (long secondMultiple : secondMultiples) {
                            if (firstMultiple == secondMultiple) {
                                firstInteger = firstMultiple;
                                founded = true;
                                break forBreak;
                            }
                        }
                    }
                }
            }
            ppcm = firstInteger;

            // Je ne peux pas les faire en une fois, l'ordinateur ne suit pas
            // PPCM pour les valeurs 16343 16897 21883 => 78756917
            // PPCM pour les valeurs 18559 11911 20221 => 58256701
            // PPCM entre 78756917 et 58256701 => Pas encore de résultat après plus de dix heures
            */

            // Optimisation (https://www.baeldung.com/java-least-common-multiple) :

            long firstMultiple = 78756917;
            long secondMultiple = 58256701;
            long highestNumber = Math.max(firstMultiple, secondMultiple);
            long lowestNumber = Math.min(firstMultiple, secondMultiple);
            ppcm = highestNumber;
            while (ppcm % lowestNumber != 0) {
                ppcm += highestNumber;
            }

            System.out.println();
            System.out.println("Nombre d'étapes = " + ppcm);
            //System.out.println("Nombre d'étapes = " + step);

            // => That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

            // => That's the right answer! You are one gold star closer to restoring snow operations. You have completed Day 8!

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}