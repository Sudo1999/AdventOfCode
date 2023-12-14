package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/* Day 2: Cube Conundrum - Part One
 Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes.
 What is the sum of the IDs of those games?
* */

public class Main {
    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-cubes.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int Id = 0;
            int somme = 0;
            while (scanner.hasNext()) {
                Id++;
                int linePower = 0;
                Map<String, Integer> colors = new HashMap<String, Integer>();
                colors.put("blue", 0);
                colors.put("green", 0);
                colors.put("red", 0);

                String line = scanner.nextLine();       // "Game 1: 7 green, 14 red, 5 blue; 8 red, 4 green; 6 green, 18 red, 9 blue"
                String cubes = line.split(": ")[1];  // "7 green, 14 red, 5 blue; 8 red, 4 green; 6 green, 18 red, 9 blue"
                String[] grabs = cubes.split("; ");     // ["7 green, 14 red, 5 blue", "8 red, 4 green", "6 green, 18 red, 9 blue"]
                for (String grab : grabs) {                     // ["7 green, 14 red, 5 blue"]
                    String[] numbers = grab.split(", ");    // ["7 green", "14 red", "5 blue"]
                    for (String number : numbers) {
                        int colorCount = Integer.parseInt(number.split(" ")[0]);
                        String colorName = number.split(" ")[1];

                        if(colors.get(colorName) < colorCount){
                            colors.put(colorName, colorCount);
                        }
                    }
                }
                // Part One :
                /*if(colors.get("blue") <= 14 && colors.get("green") <= 13 && colors.get("red") <= 12) {
                    somme += Id;
                }
            }
            System.out.println(somme);

            // => That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]*/

                // Part Two :
                // In each game you played, get the fewest number of cubes of each color that could have been in the bag to make it possible.
                // The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together.
                // Give the sum of the power of these sets.

                linePower = colors.get("blue") * colors.get("green") * colors.get("red");
                somme += linePower;
            }
            System.out.println(somme);

            // => That's the right answer! You are one gold star closer to restoring snow operations. You have completed Day 2!

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}