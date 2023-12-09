package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/* Day 7 Part One
You can determine the total winnings of this set of hands by adding up the result of multiplying each hand's bid with its rank.
Find the rank of every hand in your set. What are the total winnings?
* */

public class Main {
    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-7-test.txt";    // input-7-camelcards.txt
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            List<String> fiveOfAKind = new ArrayList<>();
            List<String> fourOfAKind = new ArrayList<>();
            List<String> fullHouse = new ArrayList<>();
            List<String> threeOfAKind = new ArrayList<>();
            List<String> twoPairs = new ArrayList<>();
            List<String> onePair = new ArrayList<>();
            List<String> highCard = new ArrayList<>();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String hand = line.split(" ")[0];
                List<String> cards = new ArrayList<>();
                for (int i = 0; i < hand.length(); i++) {
                    cards.add(String.valueOf(hand.charAt(i)));
                }

                if (cards.get(0).equals(cards.get(1)) && cards.get(0).equals(cards.get(2))
                        && cards.get(0).equals(cards.get(3)) && cards.get(0).equals(cards.get(4))) {
                    fiveOfAKind.add(hand);
                } else {
                    Collections.sort(cards);
                    if ((cards.get(0).equals(cards.get(1)) && cards.get(0).equals(cards.get(2))
                            && cards.get(0).equals(cards.get(3))) || (cards.get(1).equals(cards.get(2))
                            && cards.get(1).equals(cards.get(3)) && cards.get(1).equals(cards.get(4)))) {
                        fourOfAKind.add(hand);
                    } else if ((cards.get(0).equals(cards.get(1)) && cards.get(0).equals(cards.get(2))
                            && cards.get(3).equals(cards.get(4))) || (cards.get(0).equals(cards.get(1))
                            && cards.get(2).equals(cards.get(3)) && cards.get(2).equals(cards.get(4)))) {
                        fullHouse.add(hand);
                    } else if ((cards.get(0).equals(cards.get(1)) && cards.get(0).equals(cards.get(2))) ||
                            (cards.get(1).equals(cards.get(2)) && cards.get(1).equals(cards.get(3))) ||
                            (cards.get(2).equals(cards.get(3)) && cards.get(2).equals(cards.get(4)))) {
                        threeOfAKind.add(hand);
                    } else if ((cards.get(0).equals(cards.get(1)) && (cards.get(2).equals(cards.get(3)) || cards.get(3).equals(cards.get(4))))
                            || (cards.get(1).equals(cards.get(2)) && cards.get(3).equals(cards.get(4)))) {
                        twoPairs.add(hand);
                    } else if (cards.get(0).equals(cards.get(1)) || cards.get(1).equals(cards.get(2))
                            || cards.get(2).equals(cards.get(3)) || cards.get(3).equals(cards.get(4))) {
                        onePair.add(hand);
                    } else {
                        highCard.add(hand);
                    }
                }
            }

            System.out.println(fiveOfAKind.size());
            System.out.println(fourOfAKind.size());
            System.out.println(fullHouse.size());
            System.out.println(threeOfAKind.size());
            System.out.println(twoPairs.size());
            System.out.println(onePair.size());
            System.out.println(highCard.size());
            System.out.println("Hello Advent of code !");

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}