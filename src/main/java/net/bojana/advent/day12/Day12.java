package net.bojana.advent.day12;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.*;

public class Day12 extends MultiLineInput {

    int[] start = new int[2];
    int[] destination = new int[2];
    int[][] map;
    int N;
    int M;

    private static int[][] DIRECTIONS = new int[][]{
        new int[]{0, -1},
        new int[]{0, 1},
        new int[]{-1, 0},
        new int[]{1, 0},
    };


    public static void main(String[] args) throws IOException {
        Day12 run = new Day12();
        List<String> input = run.getInput(12, true);
        run.part1(input);
        run.part2(input);
    }

    private void initializeMap(List<String> input) {
        int indexI = 0;

        N = input.size();
        M = input.get(0).length();

        map = new int[N][M];
        for (String line : input) {
            int indexJ = 0;
            for (Character c : line.toCharArray()) {

                if (c != 'S' && c != 'E') {
                    map[indexI][indexJ] = c - 'a';
                } else if (c == 'S') {
                    start[0] = indexI;
                    start[1] = indexJ;
                    map[indexI][indexJ] = 0;
                } else {
                    destination[0] = indexI;
                    destination[1] = indexJ;
                    map[indexI][indexJ] = 25;
                }
                indexJ++;
            }
            indexI++;
        }
    }

    @Override
    public void part1(List<String> input) {

        initializeMap(input);

        Queue<int[]> queue = new LinkedList<>();
        Queue<Integer> distance = new LinkedList<>();
        Set<String> seen = new HashSet<>();

        queue.add(new int[]{start[0], start[1]});
        distance.add(0);

        int shortestDistance = -1;

        while (!queue.isEmpty()) {
            int[] position = queue.poll();
            int dist = distance.poll();

            int currentPositionValue = map[position[0]][position[1]];
            if (position[0] == destination[0] && position[1] == destination[1]) {
                shortestDistance = dist;
                break;
            }

            if (seen.contains(position[0] + " " + position[1])) {
                continue;
            }
            seen.add(position[0] + " " + position[1]);

            for (int i = 0; i < DIRECTIONS.length; i++) {
                int[] delta = DIRECTIONS[i];
                int[] newPosition = new int[]{position[0] + delta[0], position[1] + delta[1]};
                if (newPosition[0] < 0 || newPosition[1] < 0 || newPosition[0] >= N || newPosition[1] >= M) {
                    continue;
                }
                int newPositionValue = map[newPosition[0]][newPosition[1]];
                if (newPositionValue - currentPositionValue <= 1) {
                    queue.add(newPosition);
                    distance.add(dist + 1);
                }
            }
        }

        System.out.println(shortestDistance);
    }

    @Override
    public void part2(List<String> input) {
        int minSoFar = Integer.MAX_VALUE;
        initializeMap(input);

        Queue<int[]> queue = new LinkedList<>();
        Queue<Integer> distance = new LinkedList<>();
        Set<String> seen = new HashSet<>();

        queue.add(new int[]{destination[0], destination[1]});
        distance.add(0);

        while (!queue.isEmpty()) {
            int[] position = queue.poll();
            int dist = distance.poll();
            int currentPositionValue = map[position[0]][position[1]];

            if (currentPositionValue == 0) {
                if (dist < minSoFar) {
                    minSoFar = dist;
                }
            }

            if (seen.contains(position[0] + " " + position[1])) {
                continue;
            }
            seen.add(position[0] + " " + position[1]);

            for (int i = 0; i < DIRECTIONS.length; i++) {
                int[] delta = DIRECTIONS[i];
                int[] newPosition = new int[]{position[0] + delta[0], position[1] + delta[1]};
                if (newPosition[0] < 0 || newPosition[1] < 0 || newPosition[0] >= N || newPosition[1] >= M) {
                    continue;
                }
                int newPositionValue = map[newPosition[0]][newPosition[1]];
                if (currentPositionValue - newPositionValue <= 1) {
                    queue.add(newPosition);
                    distance.add(dist + 1);
                }
            }
        }
        System.out.println(minSoFar);
    }
}
