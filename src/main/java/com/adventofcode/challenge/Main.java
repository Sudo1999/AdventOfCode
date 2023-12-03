package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/* Day 3 : Gear Ratios
* Any number adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum.
* */

public class Main {
    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-gearratios.txt";
        try (Scanner preScan = new Scanner(new File(nom_fichier))) {

            int somme = 0;
            int lignes = 0, colonnes = 0;
            int x = 1, y = 1;

            // Détermination du nombre de lignes et de colonnes
            while (preScan.hasNext()) {
                String line = preScan.nextLine();   // lignes = 140 && colonnes = 140
                if (colonnes == 0) {
                    colonnes = line.length();
                }
                lignes++;
            }

            // Enregistrement des données dans un tableau à deux dimensions en ajoutant deux lignes et deux colonnes pour simplifier le traitement
            Scanner scanner = new Scanner(new File(nom_fichier));
            String[][] engineplan = new String[lignes+2][colonnes+2];
            for (int i = 0; i < colonnes+2; i++) {
                engineplan[0][i] = ".";
            }
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                engineplan[y][0] = ".";
                for(int i = 0; i < line.length(); i++) {
                    engineplan[y][x] = String.valueOf(line.charAt(i));
                    x++;
                }
                engineplan[y][colonnes+1] = ".";
                x = 1; y++;
            }
            for (int i = 0; i < colonnes+2; i++) {
                engineplan[lignes+1][i] = ".";
            }

            // Extraction des données pertinentes
            y = 0;
            for(String[] linetab : engineplan) {
                for(x = 0; x < linetab.length; x++) {
                    String signe = linetab[x];
                    if(signe.matches("[0-9]")) {
                        int index = x;
                        int signeLength = 1;
                        if(x+1 < linetab.length && linetab[x+1].matches("[0-9]")) {
                            signe += linetab[x+1];
                            signeLength++;
                            x++;
                            if(x+1 < linetab.length && linetab[x+1].matches("[0-9]")) {
                                signe += linetab[x+1];
                                signeLength++;
                                x++;
                            }
                        }

                        boolean valide = false;
                        switch (signeLength) {
                            case 1 -> {
                                if ((!engineplan[y-1][index-1].equals(".") && !engineplan[y-1][index-1].matches("[0-9]"))
                                        || (!engineplan[y-1][index].equals(".") && !engineplan[y-1][index].matches("[0-9]"))
                                        || (!engineplan[y-1][index+1].equals(".") && !engineplan[y-1][index+1].matches("[0-9]"))
                                        || (!engineplan[y][index-1].equals(".") && !engineplan[y][index-1].matches("[0-9]"))
                                        || (!engineplan[y][index+1].equals(".") && !engineplan[y][index+1].matches("[0-9]"))
                                        || (!engineplan[y+1][index-1].equals(".") && !engineplan[y+1][index-1].matches("[0-9]"))
                                        || (!engineplan[y+1][index].equals(".") && !engineplan[y+1][index].matches("[0-9]"))
                                        || (!engineplan[y+1][index+1].equals(".") && !engineplan[y+1][index+1].matches("[0-9]"))) {
                                    valide = true;
                                }
                            }
                            case 2 -> {
                                if ((!engineplan[y-1][index-1].equals(".")  && !engineplan[y-1][index-1].matches("[0-9]"))
                                        || (!engineplan[y-1][index].equals(".") && !engineplan[y-1][index].matches("[0-9]"))
                                        || (!engineplan[y-1][index+1].equals(".") && !engineplan[y-1][index+1].matches("[0-9]"))
                                        || (!engineplan[y-1][index+2].equals(".") && !engineplan[y-1][index+2].matches("[0-9]"))
                                        || (!engineplan[y][index-1].equals(".") && !engineplan[y][index-1].matches("[0-9]"))
                                        || (!engineplan[y][index+2].equals(".") && !engineplan[y][index+2].matches("[0-9]"))
                                        || (!engineplan[y+1][index-1].equals(".") && !engineplan[y+1][index-1].matches("[0-9]"))
                                        || (!engineplan[y+1][index].equals(".") && !engineplan[y+1][index].matches("[0-9]"))
                                        || (!engineplan[y+1][index+1].equals(".") && !engineplan[y+1][index+1].matches("[0-9]"))
                                        || (!engineplan[y+1][index+2].equals(".") && !engineplan[y+1][index+2].matches("[0-9]"))) {
                                    valide = true;
                                }
                            }
                            case 3 -> {
                                if ((!engineplan[y-1][index-1].equals(".") && !engineplan[y-1][index-1].matches("[0-9]"))
                                        || (!engineplan[y-1][index].equals(".") && !engineplan[y-1][index].matches("[0-9]"))
                                        || (!engineplan[y-1][index+1].equals(".") && !engineplan[y-1][index+1].matches("[0-9]"))
                                        || (!engineplan[y-1][index+2].equals(".") && !engineplan[y-1][index+2].matches("[0-9]"))
                                        || (!engineplan[y-1][index+3].equals(".") && !engineplan[y-1][index+3].matches("[0-9]"))
                                        || (!engineplan[y][index-1].equals(".") && !engineplan[y][index-1].matches("[0-9]"))
                                        || (!engineplan[y][index+3].equals(".") && !engineplan[y][index+3].matches("[0-9]"))
                                        || (!engineplan[y+1][index-1].equals(".") && !engineplan[y+1][index-1].matches("[0-9]"))
                                        || (!engineplan[y+1][index].equals(".") && !engineplan[y+1][index].matches("[0-9]"))
                                        || (!engineplan[y+1][index+1].equals(".") && !engineplan[y+1][index+1].matches("[0-9]"))
                                        || (!engineplan[y+1][index+2].equals(".") && !engineplan[y+1][index+2].matches("[0-9]"))
                                        || (!engineplan[y+1][index+3].equals(".") && !engineplan[y+1][index+3].matches("[0-9]"))) {
                                    valide = true;
                                }
                            }
                        }
                        if(valide) {
                            somme += Integer.parseInt(signe);
                        }
                    }
                }
                y++;    // Début d'une nouvelle ligne
            }
            System.out.println(somme);

            // => 525181
            // => That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}