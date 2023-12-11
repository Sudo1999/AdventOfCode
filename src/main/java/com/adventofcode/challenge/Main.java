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

            int step = 0;
            String direction = "";
            //while (true) {    // Tant qu'on n'a pas atteint "ZZZ" (et sinon on recommence la séquence)
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
                }
                System.out.println("Nombre d'étapes = " + step);

                // => 16897
            // => That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}