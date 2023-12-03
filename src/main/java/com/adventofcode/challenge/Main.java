package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-test.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int somme = 0;
            int lignes = 0, colonnes = 0;
            int x = 0, y = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();   // lignes = 140 && colonnes = 140
                if (colonnes == 0) {
                    colonnes = line.length();
                }
                lignes++;
            }

            Scanner newScanner = new Scanner(new File(nom_fichier));
            String[][] engineplan = new String[lignes][colonnes];
            while (newScanner.hasNext()) {
                String line = newScanner.nextLine();
                for(int i = 0; i < line.length(); i++) {
                    engineplan[x][y] = String.valueOf(line.charAt(i));
                    y++;
                }
                y = 0;
                x++;
            }
            for(String[] linetab : engineplan) {
                for (String signe : linetab) {
                    System.out.print(signe + "\t");
                }
                System.out.println("\n");
            }



            System.out.println(somme);

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}