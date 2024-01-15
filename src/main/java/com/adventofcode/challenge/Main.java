package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-15-lenslibrary.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
            }
            System.out.println("Hello Advent of code !");

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}