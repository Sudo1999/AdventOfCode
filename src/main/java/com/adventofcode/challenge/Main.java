package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* Day 5 Part One
* What is the lowest location number that corresponds to any of the initial seed numbers ?
* */

public class Main {

//    public static List<Long> fromSeedToLocation (ArrayList<List<long[]>> allTheMappings, List<Long> seeds) {
//
//        long searchedValue = 0L;
//        List<Long> searchedValues = new ArrayList<>();
//        for (long seed : seeds) {

    public static long fromSeedToLocation (ArrayList<List<long[]>> allTheMappings, long seed) {

        long searchedValue = seed;
        for (long[] inputLine : allTheMappings.get(0)) {
            if (searchedValue >= inputLine[1] && searchedValue < inputLine[1] + inputLine[2]) {
                long gap = searchedValue - inputLine[1];
                searchedValue = inputLine[0] + gap;
                break;
            }
        }
        for (long[] inputLine : allTheMappings.get(1)) {
            if (searchedValue >= inputLine[1] && searchedValue < inputLine[1] + inputLine[2]) {
                long gap = searchedValue - inputLine[1];
                searchedValue = inputLine[0] + gap;
                break;
            }
        }
        for (long[] inputLine : allTheMappings.get(2)) {
            if (searchedValue >= inputLine[1] && searchedValue < inputLine[1] + inputLine[2]) {
                long gap = searchedValue - inputLine[1];
                searchedValue = inputLine[0] + gap;
                break;
            }
        }
        for (long[] inputLine : allTheMappings.get(3)) {
            if (searchedValue >= inputLine[1] && searchedValue < inputLine[1] + inputLine[2]) {
                long gap = searchedValue - inputLine[1];
                searchedValue = inputLine[0] + gap;
                break;
            }
        }
        for (long[] inputLine : allTheMappings.get(4)) {
            if (searchedValue >= inputLine[1] && searchedValue < inputLine[1] + inputLine[2]) {
                long gap = searchedValue - inputLine[1];
                searchedValue = inputLine[0] + gap;
                break;
            }
        }
        for (long[] inputLine : allTheMappings.get(5)) {
            if (searchedValue >= inputLine[1] && searchedValue < inputLine[1] + inputLine[2]) {
                long gap = searchedValue - inputLine[1];
                searchedValue = inputLine[0] + gap;
                break;
            }
        }
        for (long[] inputLine : allTheMappings.get(6)) {
            if (searchedValue >= inputLine[1] && searchedValue < inputLine[1] + inputLine[2]) {
                long gap = searchedValue - inputLine[1];
                searchedValue = inputLine[0] + gap;
                break;
            }
        }
//            searchedValues.add(searchedValue);
//        }
//        return searchedValues;

        return searchedValue;
    }

    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-5-seeds.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            String title = "";
            List<Long> seeds = new ArrayList<>();
            /*Map<Long, Long> seedtosoil = new HashMap<>();
            Map<Long, Long> soiltofertilizer = new HashMap<>();
            Map<Long, Long> fertilizertowater = new HashMap<>();
            Map<Long, Long> watertolight = new HashMap<>();
            Map<Long, Long> lighttotemperature = new HashMap<>();
            Map<Long, Long> temperaturetohumidity = new HashMap<>();
            Map<Long, Long> humiditytolocation = new HashMap<>();*/
            List<long[]> seedtosoil = new ArrayList<>();
            List<long[]> soiltofertilizer = new ArrayList<>();
            List<long[]> fertilizertowater = new ArrayList<>();
            List<long[]> watertolight = new ArrayList<>();
            List<long[]> lighttotemperature = new ArrayList<>();
            List<long[]> temperaturetohumidity = new ArrayList<>();
            List<long[]> humiditytolocation = new ArrayList<>();
            ArrayList<List<long[]>> allTheMappings = new ArrayList<>();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.split(" ")[0].equals("seeds:")) {
                    String[] tabSeeds = line.split(" ");
                    for (int i = 1; i < tabSeeds.length; i++) {
                        seeds.add(Long.parseLong(tabSeeds[i]));
                    }
                }

                if ((line.split(" ")[0]).matches("[a-z-]+")) {
                    title = line.split(" ")[0];
                }

                /* (Solution inappropriée pour les grands nombres)
                // Début des tables de correspondance :
                long destinationStart;
                long sourceStart;
                long rangeLength;
                if (title.equals("seed-to-soil")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            seedtosoil.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                if (title.equals("soil-to-fertilizer")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            soiltofertilizer.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                if (title.equals("fertilizer-to-water")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            fertilizertowater.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                if (title.equals("water-to-light")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            watertolight.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                if (title.equals("light-to-temperature")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            lighttotemperature.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                if (title.equals("temperature-to-humidity")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            temperaturetohumidity.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                if (title.equals("humidity-to-location")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            humiditytolocation.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                // Les longs (-9 223 372 036 854 775 808 à 9 223 372 036 854 775 807) doivent remplacer les ints (-2 147 483 648 à 2 147 483 647),
                //  en effet plusieurs valeurs dépassent la Integer.MAX_VALUE.
                // La solution fonctionne pour l'exemple, mais pas pour le vrai input :
                // => Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
                // Process finished with exit code 1.
                */

                if (title.equals("seed-to-soil")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        long[] inputLine = new long[3];
                        for(int i = 0; i < inputLine.length; i++) {
                            inputLine[i] = Long.parseLong(line.split(" ")[i]);
                        }
                        seedtosoil.add(inputLine);
                    }
                }
                if (title.equals("soil-to-fertilizer")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        long[] inputLine = new long[3];
                        for(int i = 0; i < inputLine.length; i++) {
                            inputLine[i] = Long.parseLong(line.split(" ")[i]);
                        }
                        soiltofertilizer.add(inputLine);
                    }
                }
                if (title.equals("fertilizer-to-water")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        long[] inputLine = new long[3];
                        for(int i = 0; i < inputLine.length; i++) {
                            inputLine[i] = Long.parseLong(line.split(" ")[i]);
                        }
                        fertilizertowater.add(inputLine);
                    }
                }
                if (title.equals("water-to-light")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        long[] inputLine = new long[3];
                        for(int i = 0; i < inputLine.length; i++) {
                            inputLine[i] = Long.parseLong(line.split(" ")[i]);
                        }
                        watertolight.add(inputLine);
                    }
                }
                if (title.equals("light-to-temperature")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        long[] inputLine = new long[3];
                        for(int i = 0; i < inputLine.length; i++) {
                            inputLine[i] = Long.parseLong(line.split(" ")[i]);
                        }
                        lighttotemperature.add(inputLine);
                    }
                }
                if (title.equals("temperature-to-humidity")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        long[] inputLine = new long[3];
                        for(int i = 0; i < inputLine.length; i++) {
                            inputLine[i] = Long.parseLong(line.split(" ")[i]);
                        }
                        temperaturetohumidity.add(inputLine);
                    }
                }
                if (title.equals("humidity-to-location")) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        long[] inputLine = new long[3];
                        for(int i = 0; i < inputLine.length; i++) {
                            inputLine[i] = Long.parseLong(line.split(" ")[i]);
                        }
                        humiditytolocation.add(inputLine);
                    }
                }
                allTheMappings.add(seedtosoil);
                allTheMappings.add(soiltofertilizer);
                allTheMappings.add(fertilizertowater);
                allTheMappings.add(watertolight);
                allTheMappings.add(lighttotemperature);
                allTheMappings.add(temperaturetohumidity);
                allTheMappings.add(humiditytolocation);

            }   // Fin du scan

            /*long result = 0;
            for (int i = 0; i < seeds.size(); i++) {
                long soilNumer = seedtosoil.getOrDefault(seeds.get(i), seeds.get(i));
                long fertilizerNumer = soiltofertilizer.getOrDefault(soilNumer, soilNumer);
                long waterNumer = fertilizertowater.getOrDefault(fertilizerNumer, fertilizerNumer);
                long lightNumer = watertolight.getOrDefault(waterNumer, waterNumer);
                long temperatureNumer = lighttotemperature.getOrDefault(lightNumer, lightNumer);
                long humidityNumer = temperaturetohumidity.getOrDefault(temperatureNumer, temperatureNumer);
                long locationNumer = humiditytolocation.getOrDefault(humidityNumer, humidityNumer);

                if (i == 0) {
                    result = locationNumer;
                } else if (locationNumer < result) {
                    result = locationNumer;
                }
            }*/

//            Part One :
//            List<Long> searchedValues = fromSeedToLocation(allTheMappings, seeds);
//            long result = searchedValues.get(0);
//            for (long finalValue : searchedValues) {
//                if (finalValue < result) {
//                    result = finalValue;
//                }
//            }

            long result = fromSeedToLocation(allTheMappings, seeds.get(0));
            for (int i = 0; i < seeds.size(); i+=2) {
                long range = seeds.get(i+1);
                for (long l = 0L; l < range; l++) {
                    long seed = seeds.get(i) + l;
                    long location = fromSeedToLocation(allTheMappings, seed);
                    if (location < result) {
                        result = location;
                    }
                }
            }
            System.out.println("Result = " + result);

            // Part One :
            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

            // Part Two (Résultat obtenu après plus de dix minutes !!!)
            // That's the right answer! You are one gold star closer to restoring snow operations. You have completed Day 5!

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}