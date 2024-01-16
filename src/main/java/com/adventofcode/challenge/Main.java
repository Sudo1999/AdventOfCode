package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/* Day 15 Part One
Run the HASH algorithm on each step in the initialization sequence. What is the sum of the results?
* */

public class Main {

    public record Lens(String label, int focalLength) {}
    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-15-lenslibrary.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            long hashSum = 0, powerSum = 0;
            List<String> steps = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                steps = Arrays.stream(line.split(",")).toList();
            }

            Set<Character> allChars = new HashSet<>();
            for (String step : steps) {     // steps.size() = 4000
                int value = 0;
                for (int i = 0; i < step.length(); i++) {
                    char sign = step.charAt(i);
                    int ascii = (int)sign;
                    value += ascii;
                    value *= 17;
                    value = value % 256;
                }
                hashSum += value;
            }
            // Caractères de l'input => [b, c, d, f, g, h, j, k, l, -, m, n, p, q, 1, r, 2, s, 3, t, 4, 5, 6, v, 7, x, 8, 9, z, =]
            // => Des valeurs de 45 (-), 48 à 57 (0-9), 61 (=), et 97 à 122 (a-z).

            System.out.println();
            System.out.println("The hashSum = " + hashSum);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

            // Part Two :
            // The book goes on to explain how to perform each step in the initialization sequence.
            // To confirm that all of the lenses are installed correctly, add up the focusing power of all of the lenses.

            Map<Integer, List<Lens>> boxes = new HashMap<>();
            for (String step : steps) {         // Each step operates on a particular lens with a label, an operation, and a focal length.
                StringBuilder builder = new StringBuilder();
                char operation = '0';
                int focalLength = 0;
                for (int index = 0; index < step.length(); index++) {
                    char charAt = step.charAt(index);
                    if ((int) charAt > 96 && (int) charAt < 123) {
                        builder.append(charAt);
                    } else if (charAt == '-' || charAt == '=') {
                        operation = charAt;
                    } else {
                        focalLength = Character.getNumericValue(charAt);
                    }
                }
                Lens lens = new Lens(builder.toString(), focalLength);

                int hashValue = 0;
                for (int index = 0; index < builder.length(); index++) {
                    int ascii = (int) step.charAt(index);
                    hashValue += ascii;
                    hashValue *= 17;
                    hashValue = hashValue % 256;
                }
                if (boxes.get(hashValue) == null) {
                    boxes.put(hashValue, new ArrayList<>());
                }

                if (operation == '-') {
                    List<Lens> lenses = new ArrayList<>(boxes.get(hashValue));
                    // Remove the lens with the given label if it is present in the box. Then, move any remaining lenses as far forward in the box
                    // as they can go without changing their order, filling any space made by removing the indicated lens.
                    lenses = lenses.stream().filter(l -> !l.label.contentEquals(builder)).toList();
                    boxes.replace(hashValue, lenses);

                } else if (operation == '=') {
                    List<Lens> lenses = new ArrayList<>(boxes.get(hashValue));
                    // The lens needs to go into the relevant box. There are two possible situations:
                    boolean oldLensExists = false;
                    for (Lens oldLens : lenses) {
                        if (oldLens.label.contentEquals(builder)) {
                            // If there is already a lens in the box with the same label, replace the old lens with the new lens:
                            // remove the old lens and put the new lens in its place, not moving any other lenses in the box.
                            lenses.set(lenses.indexOf(oldLens), lens);
                            oldLensExists = true;
                            break;
                        }
                    }
                    // If not, add the lens to the box immediately behind any lenses already in the box.
                    if (!oldLensExists) {
                        lenses.add(lens);
                    }
                    boxes.replace(hashValue, lenses);
                }
            }

            for (Integer boxId : boxes.keySet()) {      // boxId = la hashValue calculée plus haut
                for (Lens lens : boxes.get(boxId)) {
                    int rank = boxes.get(boxId).indexOf(lens) + 1;
                    int focusingPower = (1 + boxId) * rank * lens.focalLength;
                    powerSum += focusingPower;
                }
            }

            System.out.println();
            System.out.println("The focusing power of all of the lenses = " + powerSum);

            // That's the right answer! You are one gold star closer to restoring snow operations.

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}