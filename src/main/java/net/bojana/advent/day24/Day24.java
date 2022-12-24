package net.bojana.advent.day24;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day24 extends MultiLineInput {
    int N, M;

    private static Position[] offsets =  new Position[]{
        new Position(-1, 0),
        new Position(1, 0),
        new Position(0, -1),
        new Position(0, 1)

    };

    public static void main(String[] args) throws IOException {

        Day24 day24 = new Day24();
        List<String> input = day24.getInput(24, false);

        day24.part1(input);
        day24.part2(input);
    }

    @Override
    public void part1(List<String> input) {
        N = input.size();
        M = input.get(0).length();

        Position start = new Position(0, 1);
        Position end = new Position(N - 1, M - 2);

        Map<Position, List<Direction>> allBlizzards = initializeBlizzards(input);

        Set<Position> positions = new HashSet<>();
        positions.add(start);
        int count = 0;

        while (!positions.contains(end)) {

            allBlizzards = calculateNextBlizzardPositions(allBlizzards);
            positions = calculateMyNextPositions(allBlizzards, positions);

            count++;
        }

        System.out.println(count);
    }

    @Override
    public void part2(List<String> input) {
        N = input.size();
        M = input.get(0).length();

        Position start = new Position(0, 1);
        Position end = new Position(N - 1, M - 2);

        Map<Position, List<Direction>> allBlizzards = initializeBlizzards(input);
        int count = 0;

        Set<Position> positions = new HashSet<>();
        positions.add(start);


        while (!positions.contains(end)) {

            allBlizzards = calculateNextBlizzardPositions(allBlizzards);
            positions = calculateMyNextPositions(allBlizzards, positions);

            count++;
        }


        positions = new HashSet<>();
        positions.add(end);


        while (!positions.contains(start)) {

            allBlizzards = calculateNextBlizzardPositions(allBlizzards);
            positions = calculateMyNextPositions(allBlizzards, positions);

            count++;
        }

        positions = new HashSet<>();
        positions.add(start);


        while (!positions.contains(end)) {

            allBlizzards = calculateNextBlizzardPositions(allBlizzards);
            positions = calculateMyNextPositions(allBlizzards, positions);

            count++;
        }


        System.out.println(count);
    }


    private Map<Position, List<Direction>> initializeBlizzards(List<String> input) {
        Map<Position, List<Direction>> allBlizzards = new HashMap<>();
        for (int i = 1; i < N-1; i++) {
            String line = input.get(i);
            for (int j = 1; j < M - 1; j++) {
                char c = line.charAt(j);
                if (c != '.') {
                   allBlizzards.put(new Position(i, j), new LinkedList<>(List.of(Direction.ofValue(c))));
                }
            }
        }
        return allBlizzards;
    }



    private Set<Position> calculateMyNextPositions(Map<Position, List<Direction>> allBlizzards, Set<Position> positions) {
        Set<Position> nextPositions = new HashSet<>();
        for (Position currentPosition : positions) {
            // check where can I go
            for (Position p : offsets) {
                Position myNextPos = currentPosition.addOffsetPerson(p, N, M);

                if (myNextPos != null && !allBlizzards.containsKey(myNextPos)) {
                    nextPositions.add(myNextPos);
                }
            }
            if (!allBlizzards.containsKey(currentPosition)) {
                nextPositions.add(currentPosition);
            }
        }
        return nextPositions;
    }

    private Map<Position, List<Direction>> calculateNextBlizzardPositions(Map<Position, List<Direction>> allBlizzards) {
        Map<Position, List<Direction>> nextBlizzardPositions = new HashMap<>();
        for (Map.Entry<Position, List<Direction>> entry : allBlizzards.entrySet()) {
            Position currentPosOfBlizzard = entry.getKey();
            List<Direction> directions = entry.getValue();

            for (Direction d : directions) {
                Position nextPos = calculateNextPosition(currentPosOfBlizzard, d);
                nextBlizzardPositions.merge(nextPos, List.of(d), (l1, l2) -> Stream.concat(l1.stream(), Stream.of(d)).collect(Collectors.toList()));
            }
        }
        return nextBlizzardPositions;
    }

    Position calculateNextPosition(Position position, Direction direction) {
        switch (direction) {
            case RIGHT:
                return position.addOffsetBlizzard(new Position(0, 1), N, M);
            case UP:
                return position.addOffsetBlizzard(new Position(-1, 0), N, M);
            case LEFT:
                return position.addOffsetBlizzard(new Position(0, -1), N, M);
            case DOWN:
                return position.addOffsetBlizzard(new Position(1, 0), N, M);
        }
        return position;
    }


}
