package com.adventofcode.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* Day 16 Part One
With the beam starting in the top-left heading right, how many tiles end up being energized?
* */

public class Main {

    public enum To {
        RIGHT, DOWN, LEFT, UP, UPDOWN, LEFTRIGHT
    }

    public record Tile(int x, int y, char nature, boolean energized) {}
    // Nature = empty space (.), mirrors (/ and \), or splitters (| and -).

    public record Contraption() {
        static List<int[]> visited = new ArrayList<>();
        public static void bounces(List<Tile> tiles, int x, int y, To direction) {
            int width = tiles.stream().map(t -> t.x).max(Integer::compare).orElseThrow() + 1;
            int depth = tiles.stream().map(t -> t.y).max(Integer::compare).orElseThrow() + 1;
            int toId = direction.ordinal();
            int[] visite = {x, y, toId};
            boolean redundant = visited.stream().filter(v -> v[0] == x).filter(v -> v[1] == y).anyMatch(v -> v[2] == toId);

            if (!redundant) {
                visited.add(visite);
                boolean allowed;
                switch (direction) {
                    case RIGHT -> {
                        allowed = (x + 1) < width;
                        if (allowed) {
                            Tile nextTile = tiles.stream().filter(t -> (t.x == x + 1 && t.y == y)).findFirst().orElseThrow();
                            int nextTileId = tiles.indexOf(nextTile);
                            nextTile = new Tile(x + 1, y, nextTile.nature, true);
                            tiles.set(nextTileId, nextTile);
                            bounces(tiles, x + 1, y, towards(nextTile.nature, "left"));
                        }
                    }
                    case DOWN -> {
                        allowed = (y + 1) < depth;
                        if (allowed) {
                            Tile nextTile = tiles.stream().filter(t -> (t.x == x && t.y == y + 1)).findFirst().orElseThrow();
                            int nextTileId = tiles.indexOf(nextTile);
                            nextTile = new Tile(x, y + 1, nextTile.nature, true);
                            tiles.set(nextTileId, nextTile);
                            bounces(tiles, x, y + 1, towards(nextTile.nature, "up"));
                        }
                    }
                    case LEFT -> {
                        allowed = (x - 1) >= 0;
                        if (allowed) {
                            Tile nextTile = tiles.stream().filter(t -> (t.x == x - 1 && t.y == y)).findFirst().orElseThrow();
                            int nextTileId = tiles.indexOf(nextTile);
                            nextTile = new Tile(x - 1, y, nextTile.nature, true);
                            tiles.set(nextTileId, nextTile);
                            bounces(tiles, x - 1, y, towards(nextTile.nature, "right"));
                        }
                    }
                    case UP -> {
                        allowed = (y - 1) >= 0;
                        if (allowed) {
                            Tile nextTile = tiles.stream().filter(t -> (t.x == x && t.y == y - 1)).findFirst().orElseThrow();
                            int nextTileId = tiles.indexOf(nextTile);
                            nextTile = new Tile(x, y - 1, nextTile.nature, true);
                            tiles.set(nextTileId, nextTile);
                            bounces(tiles, x, y - 1, towards(nextTile.nature, "down"));
                        }
                    }
                    case UPDOWN -> {
                        bounces(tiles, x, y, To.UP);
                        bounces(tiles, x, y, To.DOWN);
                    }
                    case LEFTRIGHT -> {
                        bounces(tiles, x, y, To.LEFT);
                        bounces(tiles, x, y, To.RIGHT);
                    }
                }
            } else {
                return;
            }
        }

        public static To towards(char tileNature, String from) {
            // tileNature = empty space (.), mirrors (/ and \), or splitters (| and -).
            // String from = "left", "up", "right", or "down".
            To to;
            to = switch(tileNature) {
                case '/' -> {
                    switch(from) {
                        case "left" -> {
                            yield To.UP;
                        }
                        case "up" -> {
                            yield To.LEFT;
                        }
                        case "right" -> {
                            yield To.DOWN;
                        }
                        case "down" -> {
                            yield To.RIGHT;
                        }
                        default -> {
                            yield null;
                        }
                    }
                }
                case '\\' -> {
                    switch(from) {
                        case "left" -> {
                            yield To.DOWN;
                        }
                        case "up" -> {
                            yield To.RIGHT;
                        }
                        case "right" -> {
                            yield To.UP;
                        }
                        case "down" -> {
                            yield To.LEFT;
                        }
                        default -> {
                            yield null;
                        }
                    }
                }
                case '|' -> {
                    switch(from) {
                        case "left", "right" -> {
                            yield To.UPDOWN;
                        }
                        case "up" -> {
                            yield To.DOWN;
                        }
                        case "down" -> {
                            yield To.UP;
                        }
                        default -> {
                            yield null;
                        }
                    }
                }
                case '-' -> {
                    switch(from) {
                        case "left" -> {
                            yield To.RIGHT;
                        }
                        case "up", "down" -> {
                            yield To.LEFTRIGHT;
                        }
                        case "right" -> {
                            yield To.LEFT;
                        }
                        default -> {
                            yield null;
                        }
                    }
                }
                case '.' -> {
                    switch(from) {
                        case "left" -> {
                            yield To.RIGHT;
                        }
                        case "up" -> {
                            yield To.DOWN;
                        }
                        case "right" -> {
                            yield To.LEFT;
                        }
                        case "down" -> {
                            yield To.UP;
                        }
                        default -> {
                            yield null;
                        }
                    }
                }
                default -> null;
            };
            return to;
        }
    }

    public static void main(String[] args) {

        String nom_fichier = "src/main/resources/input-16-floor.txt";       // input-16-floor.txt => DOWN !!!
        try (Scanner scanner = new Scanner(new File(nom_fichier))) {

            int energizedTiles = 0;
            int lineId = 0;
            List<Tile> tiles = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                for (int colId = 0; colId < line.length(); colId++) {
                    Tile tile = new Tile(colId, lineId, line.charAt(colId), false);
                    tiles.add(tile);
                }
                lineId++;
            }

            Tile firstTile = tiles.stream().filter(t -> (t.x == 0 && t.y == 0)).findFirst().orElseThrow();
            firstTile = new Tile(0, 0, firstTile.nature, true);
            tiles.set(0, firstTile);

            //Contraption.bounces(tiles, 0, 0, To.RIGHT);
            Contraption.bounces(tiles, 0, 0, To.DOWN);

            for (Tile tile : tiles) {
                if (tile.energized) {
                    energizedTiles++;
                }
            }

            System.out.println();
            System.out.println("The energized tiles are " + energizedTiles);

            // That's the right answer! You are one gold star closer to restoring snow operations [Continue to Part Two]

        } catch(FileNotFoundException e) {
            System.out.println("Aucun fichier à lire");
        }
    }
}