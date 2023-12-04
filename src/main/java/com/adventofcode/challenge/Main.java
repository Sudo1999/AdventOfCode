package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/* Day 3 : Gear Ratios
* Any number adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum.
* */

public class Main {

    public static int searchAbove(String[][] tab, int y, int x) {
        // On arrive ici avec les coordonnées (y,x) d'une étoile située au-dessus et à droite d'un nombre
        int fullNumber = 0;
        if(tab[y+1][x+1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y+1, x+1);
        }
        return fullNumber;
    }
    public static int searchOnline(String[][] tab, int y, int x) {
        // On arrive ici avec les coordonnées (y,x) d'une étoile située à droite d'un nombre
        int fullNumber;
        String number = "0";
        if(tab[y][x+1].matches("[0-9]")) {
            number = tab[y][x+1];
            if(tab[y][x+2].matches("[0-9]")) {
                number += tab[y][x+2];
                if(tab[y][x+3].matches("[0-9]")) {
                    number += tab[y][x+3];
                }
            }
        }
        fullNumber = Integer.parseInt(number);

        if(tab[y-1][x-1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y - 1, x - 1);
        }
        if(tab[y-1][x].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y - 1, x);
        }
        if(tab[y-1][x+1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y - 1, x + 1);
        }
        return fullNumber;
    }
    public static int searchBelowOne(String[][] tab, int y, int x, int b, int a) {
        // On arrive ici avec les coordonnées (y,x) d'une étoile et les coordonnées (b, a) de la première moitié d'un gear à un chiffre
        int fullNumber = 0;
        if(tab[y-1][x-1].matches("[0-9]") && (x-1 != a)) {
            fullNumber = getFullNumber(tab, y-1, x-1);
        }
        if(tab[y-1][x].matches("[0-9]") && (x != a)) {
            fullNumber = getFullNumber(tab, y-1, x);
        }
        if(tab[y-1][x+1].matches("[0-9]") && (x+1 != a)) {
            fullNumber = getFullNumber(tab, y-1, x+1);
        }
        if(tab[y][x-1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y, x-1);
        }
        if(tab[y][x+1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y, x+1);
        }
        if(tab[y+1][x-1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y+1, x-1);
        }
        if(tab[y+1][x].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y+1, x);
        }
        if(tab[y+1][x+1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y+1, x+1);
        }
        return fullNumber;
    }
    public static int searchBelowTwo(String[][] tab, int y, int x, int b, int a) {
        // On arrive ici avec les coordonnées (y,x) d'une étoile et les coordonnées (b, a) de la première moitié d'un gear à deux chiffres
        int fullNumber = 0;
        if(tab[y-1][x-1].matches("[0-9]") && (x-1 != a && x-1 != a+1)) {
            fullNumber = getFullNumber(tab, y-1, x-1);
        }
        if(tab[y-1][x].matches("[0-9]") && (x != a && x != a+1)) {
            fullNumber = getFullNumber(tab, y-1, x);
        }
        if(tab[y-1][x+1].matches("[0-9]") && (x+1 != a && x+1 != a+1)) {
            fullNumber = getFullNumber(tab, y-1, x+1);
        }
        if(tab[y][x-1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y, x-1);
        }
        if(tab[y][x+1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y, x+1);
        }
        if(tab[y+1][x-1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y+1, x-1);
        }
        if(tab[y+1][x].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y+1, x);
        }
        if(tab[y+1][x+1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y+1, x+1);
        }
        return fullNumber;
    }
    public static int searchBelowThree(String[][] tab, int y, int x, int b, int a) {
        // On arrive ici avec les coordonnées (y,x) d'une étoile et les coordonnées (b, a) de la première moitié d'un gear à trois chiffres
        int fullNumber = 0;
        if(tab[y-1][x-1].matches("[0-9]") && (x-1 != a && x-1 != a+1 && x-1 != a+2)) {
            fullNumber = getFullNumber(tab, y-1, x-1);
        }
        if(tab[y-1][x].matches("[0-9]") && (x != a && x != a+1 && x != a+2)) {
            fullNumber = getFullNumber(tab, y-1, x);
        }
        if(tab[y-1][x+1].matches("[0-9]") && (x+1 != a && x+1 != a+1 && x+1 != a+2)) {
            fullNumber = getFullNumber(tab, y-1, x+1);
        }
        if(tab[y][x-1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y, x-1);
        }
        if(tab[y][x+1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y, x+1);
        }
        if(tab[y+1][x-1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y+1, x-1);
        }
        if(tab[y+1][x].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y+1, x);
        }
        if(tab[y+1][x+1].matches("[0-9]")) {
            fullNumber = getFullNumber(tab, y+1, x+1);
        }
        return fullNumber;
    }

    public static int getFullNumber(String[][] tab, int y, int x) {     // Ici les coordonnées (y, x) sont celles de la deuxième moitié du gear
        String number = tab[y][x];
        if(tab[y][x-1].matches("[0-9]")) {
            number = tab[y][x-1] + tab[y][x];
            if(tab[y][x-2].matches("[0-9]")) {
                number = tab[y][x-2] + tab[y][x-1] + tab[y][x];
            } else if(tab[y][x+1].matches("[0-9]")) {
                number = tab[y][x-1] + tab[y][x] + tab[y][x+1];
            }
        } else if(tab[y][x+1].matches("[0-9]")) {
            number += tab[y][x+1];
            if (tab[y][x+2].matches("[0-9]")) {
                number += tab[y][x+2];
            }
        }
        return Integer.parseInt(number);
    }

    /*================================================================================================================*/
    /*                                                   == MAIN ==                                                   */
    /*================================================================================================================*/

    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-test.txt";   // "src/main/resources/input-gearratios.txt";
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

                        /*boolean valide = false;
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

                        // => 525181
                        // => That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]
                        */

                        // Part Two : A gear is any * symbol that is adjacent to exactly two part numbers. Its gear ratio is the result of multiplying
                        // those two numbers together. This time, you need to find the gear ratio of every gear and add them all up.

                        int[] etoile = new int[2];  // etoile[0] = y && etoile[1] = x
                        int[] halfgear = new int[2];    // halfgear[0] = y && halfgear[1] = x
                        halfgear[0] = y; halfgear[1] = index;
                        switch (signeLength) {
                            case 1 -> {
                                if (engineplan[y - 1][index + 1].equals("*")) {
                                    //etoile[0] = y - 1; etoile[1] = index + 1;
                                    somme += (Integer.parseInt(signe) * searchAbove(engineplan, y - 1, index + 1));
                                }
                                if (engineplan[y][index + 1].equals("*")) {
                                    //etoile[0] = y; etoile[1] = index + 1;
                                    somme += (Integer.parseInt(signe) * searchOnline(engineplan, y, index + 1));
                                }
                                if (engineplan[y + 1][index - 1].equals("*")) {
                                    //etoile[0] = y + 1; etoile[1] = index - 1;
                                    somme += (Integer.parseInt(signe) * searchBelowOne(engineplan, y+1, index-1, y, index));
                                }
                                if (engineplan[y + 1][index].equals("*")) {
                                    //etoile[0] = y + 1; etoile[1] = index;
                                    somme += (Integer.parseInt(signe) * searchBelowOne(engineplan, y+1, index, y, index));
                                }
                                if (engineplan[y + 1][index + 1].equals("*")) {
                                    etoile[0] = y + 1; etoile[1] = index + 1;
                                    somme += (Integer.parseInt(signe) * searchBelowOne(engineplan, y+1, index+1, y, index));
                                }
                            }
                            case 2 -> {
                                if (engineplan[y-1][index+2].equals("*")) {
                                    //etoile[0] = y - 1; etoile[1] = index + 2;
                                    somme += (Integer.parseInt(signe) * searchAbove(engineplan, y-1, index+2));
                                }
                                if (engineplan[y][index+2].equals("*")) {
                                    //etoile[0] = y; etoile[1] = index + 2;
                                    somme += (Integer.parseInt(signe) * searchOnline(engineplan, y, index+2));
                                }
                                if (engineplan[y+1][index-1].equals("*")) {
                                    //etoile[0] = y + 1; etoile[1] = index - 1;
                                    somme += (Integer.parseInt(signe) * searchBelowTwo(engineplan, y+1, index-1, y, index));
                                }
                                if (engineplan[y+1][index].equals("*")) {
                                    //etoile[0] = y + 1; etoile[1] = index;
                                    somme += (Integer.parseInt(signe) * searchBelowTwo(engineplan, y+1, index, y, index));
                                }
                                if (engineplan[y+1][index+1].equals("*")) {
                                    //etoile[0] = y + 1; etoile[1] = index + 1;
                                    somme += (Integer.parseInt(signe) * searchBelowTwo(engineplan, y+1, index+1, y, index));
                                }
                                if (engineplan[y+1][index+2].equals("*")) {
                                    //etoile[0] = y + 1; etoile[1] = index + 2;
                                    somme += (Integer.parseInt(signe) * searchBelowTwo(engineplan, y+1, index+2, y, index));
                                }
                            }
                            case 3 -> {
                                if (engineplan[y-1][index+3].equals("*")) {
                                    //etoile[0] = y - 1; etoile[1] = index + 3;
                                    somme += (Integer.parseInt(signe) * searchAbove(engineplan, y-1, index+3));
                                }
                                if (engineplan[y][index+3].equals("*")) {
                                    //etoile[0] = y; etoile[1] = index + 3;
                                    somme += (Integer.parseInt(signe) * searchOnline(engineplan, y, index+3));
                                }
                                if (engineplan[y+1][index-1].equals("*")) {
                                    //etoile[0] = y + 1; etoile[1] = index - 1;
                                    somme += (Integer.parseInt(signe) * searchBelowThree(engineplan, y+1, index-1, y, index));
                                }
                                if (engineplan[y+1][index].equals("*")) {
                                    //etoile[0] = y + 1; etoile[1] = index;
                                    somme += (Integer.parseInt(signe) * searchBelowThree(engineplan, y+1, index, y, index));
                                }
                                if (engineplan[y+1][index+1].equals("*")) {
                                    //etoile[0] = y + 1; etoile[1] = index + 1;
                                    somme += (Integer.parseInt(signe) * searchBelowThree(engineplan, y+1, index+1, y, index));
                                }
                                if (engineplan[y+1][index+2].equals("*")) {
                                    //etoile[0] = y + 1; etoile[1] = index + 2;
                                    somme += (Integer.parseInt(signe) * searchBelowThree(engineplan, y+1, index+2, y, index));
                                }
                                if (engineplan[y+1][index+3].equals("*")) {
                                    //etoile[0] = y + 1; etoile[1] = index + 3;
                                    somme += (Integer.parseInt(signe) * searchBelowThree(engineplan, y+1, index+3, y, index));
                                }
                            }
                        }

                    }
                }
                y++;    // Début d'une nouvelle ligne
            }
            System.out.println(somme);

            // => 79024204 => 79129070 => 80480350
            // => That's not the right answer; your answer is too low.
            //

        }
        catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}