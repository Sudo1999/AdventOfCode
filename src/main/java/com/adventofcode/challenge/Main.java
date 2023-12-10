package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

/* Day 7 Part One
You can determine the total winnings of this set of hands by adding up the result of multiplying each hand's bid with its rank.
What are the total winnings?
* */

public class Main {

    public static class CardComparator implements Comparator<String> {
        @Override
        public int compare(String hand1, String hand2) {

            // Les cartes = A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, ou 2, de la plus forte à la plus faible.

            List<String> cards1 = new ArrayList<>();
            for (int i = 0; i < hand1.length(); i++) {
                cards1.add(String.valueOf(hand1.charAt(i)));
            }
            List<String> cards2 = new ArrayList<>();
            for (int i = 0; i < hand2.length(); i++) {
                cards2.add(String.valueOf(hand2.charAt(i)));
            }

            String card1, card2;
            int sortResult = 0;
            // hand1.length() = hand2.length() = 5
            for (int i = 0; i < 5; i++) {
                if (sortResult == 0) {
                    card1 = cards1.get(i);
                    card2 = cards2.get(i);

                    sortResult = switch (card1) {
                        case "A" -> {
                            if (card2.equals("A")) {
                                yield 0;
                            } else {
                                yield -1;
                            }
                        }
                        case "K" -> {
                            if (card2.equals("A")) {
                                yield 1;
                            } else if (card2.equals("K")) {
                                yield 0;
                            } else {
                                yield -1;
                            }
                        }
                        case "Q" -> {
                            if (card2.equals("A") || card2.equals("K")) {
                                yield 1;
                            } else if (card2.equals("Q")) {
                                yield 0;
                            } else {
                                yield -1;
                            }
                        }
                        case "J" -> {
                            if (card2.equals("A") || card2.equals("K") || card2.equals("Q")) {
                                yield 1;
                            } else if (card2.equals("J")) {
                                yield 0;
                            } else {
                                yield -1;
                            }
                        }
                        case "T" -> {
                            if (card2.equals("A") || card2.equals("K") || card2.equals("Q") || card2.equals("J")) {
                                yield 1;
                            } else if (card2.equals("T")) {
                                yield 0;
                            } else {
                                yield -1;
                            }
                        }
                        default -> {
                            yield card2.compareTo(card1);
                        }
                    };   // Fin du switch
                }   // Fin du if
            }   // Fin du for
            return sortResult;
        }
    }

    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-7-camelcards.txt";    // input-7-camelcards.txt
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            TreeMap<String, Integer> fiveOfAKind = new TreeMap<>(new CardComparator());
            TreeMap<String, Integer> fourOfAKind = new TreeMap<>(new CardComparator());
            TreeMap<String, Integer> fullHouse = new TreeMap<>(new CardComparator());
            TreeMap<String, Integer> threeOfAKind = new TreeMap<>(new CardComparator());
            TreeMap<String, Integer> twoPairs = new TreeMap<>(new CardComparator());
            TreeMap<String, Integer> onePair = new TreeMap<>(new CardComparator());
            TreeMap<String, Integer> highCard = new TreeMap<>(new CardComparator());
            HashMap<String, Integer> allTheHands = new HashMap<>();
            int rank = 0;

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String hand = line.split(" ")[0];
                int bidAmount = Integer.parseInt(line.split(" ")[1]);

                // Vérification faite => il n'y a pas de doublon
                if (allTheHands.containsKey(hand)) {
                    System.out.println("Attention doublon !");
                } else {
                    allTheHands.put(hand, bidAmount);
                }
                rank = allTheHands.size();

                // Extraction des cartes de la main
                List<String> cards = new ArrayList<>();
                for (int i = 0; i < hand.length(); i++) {
                    cards.add(String.valueOf(hand.charAt(i)));
                }

                // Typage des mains
                if (cards.get(0).equals(cards.get(1)) && cards.get(0).equals(cards.get(2))
                        && cards.get(0).equals(cards.get(3)) && cards.get(0).equals(cards.get(4))) {
                    fiveOfAKind.put(hand, bidAmount);
                } else {
                    Collections.sort(cards);
                    if ((cards.get(0).equals(cards.get(1)) && cards.get(0).equals(cards.get(2))
                            && cards.get(0).equals(cards.get(3))) || (cards.get(1).equals(cards.get(2))
                            && cards.get(1).equals(cards.get(3)) && cards.get(1).equals(cards.get(4)))) {
                        fourOfAKind.put(hand, bidAmount);
                    } else if ((cards.get(0).equals(cards.get(1)) && cards.get(0).equals(cards.get(2))
                            && cards.get(3).equals(cards.get(4))) || (cards.get(0).equals(cards.get(1))
                            && cards.get(2).equals(cards.get(3)) && cards.get(2).equals(cards.get(4)))) {
                        fullHouse.put(hand, bidAmount);
                    } else if ((cards.get(0).equals(cards.get(1)) && cards.get(0).equals(cards.get(2))) ||
                            (cards.get(1).equals(cards.get(2)) && cards.get(1).equals(cards.get(3))) ||
                            (cards.get(2).equals(cards.get(3)) && cards.get(2).equals(cards.get(4)))) {
                        threeOfAKind.put(hand, bidAmount);
                    } else if ((cards.get(0).equals(cards.get(1)) && (cards.get(2).equals(cards.get(3)) || cards.get(3).equals(cards.get(4))))
                            || (cards.get(1).equals(cards.get(2)) && cards.get(3).equals(cards.get(4)))) {
                        twoPairs.put(hand, bidAmount);
                    } else if (cards.get(0).equals(cards.get(1)) || cards.get(1).equals(cards.get(2))
                            || cards.get(2).equals(cards.get(3)) || cards.get(3).equals(cards.get(4))) {
                        onePair.put(hand, bidAmount);
                    } else {
                        highCard.put(hand, bidAmount);
                    }
                }
            }

            // Multiplication des mises et des rangs (la plus forte main a le rang le plus élevé)
            int somme = 0;
            int handAmount = 0;
            for (String rankedHand : fiveOfAKind.keySet()) {
                handAmount += fiveOfAKind.get(rankedHand) * rank;
                rank--;
            }
            somme += handAmount;
            handAmount = 0;
            for (String rankedHand : fourOfAKind.keySet()) {
                handAmount += fourOfAKind.get(rankedHand) * rank;
                rank--;
            }
            somme += handAmount;
            handAmount = 0;
            for (String rankedHand : fullHouse.keySet()) {
                handAmount += fullHouse.get(rankedHand) * rank;
                rank--;
            }
            somme += handAmount;
            handAmount = 0;
            for (String rankedHand : threeOfAKind.keySet()) {
                handAmount += threeOfAKind.get(rankedHand) * rank;
                rank--;
            }
            somme += handAmount;
            handAmount = 0;
            for (String rankedHand : twoPairs.keySet()) {
                handAmount += twoPairs.get(rankedHand) * rank;
                rank--;
            }
            somme += handAmount;
            handAmount = 0;
            for (String rankedHand : onePair.keySet()) {
                handAmount += onePair.get(rankedHand) * rank;
                rank--;
            }
            somme += handAmount;
            handAmount = 0;
            for (String rankedHand : highCard.keySet()) {
                handAmount += highCard.get(rankedHand) * rank;
                rank--;
            }
            somme += handAmount;

            System.out.println();
            System.out.println("La somme de toutes les multiplications est de " + somme);

            // => 250347426
            // => That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}