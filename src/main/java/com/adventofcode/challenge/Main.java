package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/* Day 5 Part One
* What is the lowest location number that corresponds to any of the initial seed numbers ?
* */

public class Main {
    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-5-seeds.txt";
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            String title = "";
            long destinationStart;
            long sourceStart;
            long rangeLength;
            List<Long> seeds = new ArrayList<>();
            Map<Long, Long> seedtosoil = new HashMap<>();
            Map<Long, Long> soiltofertilizer = new HashMap<>();
            Map<Long, Long> fertilizertowater = new HashMap<>();
            Map<Long, Long> watertolight = new HashMap<>();
            Map<Long, Long> lighttotemperature = new HashMap<>();
            Map<Long, Long> temperaturetohumidity = new HashMap<>();
            Map<Long, Long> humiditytolocation = new HashMap<>();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.split(" ")[0].equals("seeds:")) {
                    String[] tabSeeds = line.split(" ");
                    for (int i = 1; i < tabSeeds.length; i++) {
                        seeds.add(Long.parseLong(tabSeeds[i]));
                    }
                }

                if ((line.split(" ")[0]).matches("[a-z[-]]+")) {
                    title = line.split(" ")[0];
                }

                /*
                // Début des tables de correspondance :
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
                if (title.equals("soil-to-fertilizer") && line.length() > 0) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            soiltofertilizer.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                if (title.equals("fertilizer-to-water") && line.length() > 0) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            fertilizertowater.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                if (title.equals("water-to-light") && line.length() > 0) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            watertolight.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                if (title.equals("light-to-temperature") && line.length() > 0) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            lighttotemperature.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                if (title.equals("temperature-to-humidity") && line.length() > 0) {
                    if (line.length() > 0 && (line.split(" ")[0]).matches("[0-9]+")) {
                        destinationStart =  Long.parseLong(line.split(" ")[0]);
                        sourceStart =  Long.parseLong(line.split(" ")[1]);
                        rangeLength =Long.parseLong(line.split(" ")[2]);
                        for (int i = 0; i < rangeLength; i++) {
                            temperaturetohumidity.put(sourceStart+i, destinationStart+i);
                        }
                    }
                }
                if (title.equals("humidity-to-location") && line.length() > 0) {
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

            }   // Fin du scan

            long result = 0;
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
            }
            System.out.println("Result = " + result);

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}