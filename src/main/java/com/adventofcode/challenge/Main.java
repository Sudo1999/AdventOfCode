package com.adventofcode.challenge;

import java.util.ArrayList;
import java.util.List;

/* Day 6 Part One
Determine the number of ways you could beat the record in each race. What do you get if you multiply these numbers together?
Input values :
Time:        40     82     91     66
Distance:   277   1338   1349   1063
* */

public class Main {
    public static void main(String[] args) {

        List<int[]> fourRaces = new ArrayList<int[]>();
        // race[0] = raceDuration && race[1] = recordToBeat && race[2] = wayToWin
//        // Exemple fourni :
//        int[] race1 = {7, 9, 0};
//        int[] race2 = {15, 40, 0};
//        int[] race3 = {30, 200, 0};
        int[] race1 = {40, 277, 0};
        int[] race2 = {82, 1338, 0};
        int[] race3 = {91, 1349, 0};
        int[] race4 = {66, 1063, 0};
        fourRaces.add(race1);
        fourRaces.add(race2);
        fourRaces.add(race3);
        fourRaces.add(race4);

        int result = 1;
        for (int[] race : fourRaces) {
            int remainingTime = race[0];
            int speed = 0;
            int wayToWin = 0;
            for (int i = remainingTime; i > 0; i--) {
                if (i * speed > race[1]) {
                    wayToWin++;
                }
                speed++;
            }
            race[2] = wayToWin;
        }
        for (int[] race : fourRaces) {
            result *= race[2];
        }

        // => 505494
        // => That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

        System.out.println("Result = " + result);
    }
}