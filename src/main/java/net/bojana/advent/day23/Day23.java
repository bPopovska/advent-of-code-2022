package net.bojana.advent.day23;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.*;

public class Day23 extends MultiLineInput {

    public static void main(String[] args) throws IOException {
        Day23 run = new Day23();

        List<String> input = run.getInput(23, false);
        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(List<String> input) {

        int N = input.size();
        int M = input.get(0).length();

        int elfCount = 0;
        Map<Integer, Position> elves = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String line = input.get(i);
            for (int j = 0; j < M; j++) {
                char c = line.charAt(j);
                if (c == '#') {
                    elves.put(elfCount++, new Position(i, j));
                }
            }
        }

        int pos = 0;
        Position[][] positionsToCheck = new Position[][]{
                new Position[]{new Position(-1, 0), new Position(-1, -1), new Position(-1 , 1)}, // north
                new Position[]{new Position(1, 0), new Position(1, -1), new Position(1 , 1)}, // south
                new Position[]{new Position(0, -1), new Position(-1, -1), new Position(1 , -1)}, // west
                new Position[]{ new Position(0, 1), new Position(-1, 1), new Position(1 , 1)}, // east


        };

        Position[] allNeighbours = new Position[] {
          new Position(-1, -1), new Position(-1, 0), new Position(-1, 1),
          new Position(0, -1), new Position(0, 1),
          new Position(1, -1), new Position(1, 0), new Position(1, 1),
        };

        int roundCount = 100000;

        for (int i = 0; i < roundCount; i++) {

            System.out.println("== End of Round " + (i + 1) + " ==");
            Map<Integer, Position> proposedPositions = new HashMap<>();
            Map<Position, Integer> count = new HashMap<>();
            Set<Integer> noNeighbour = new HashSet<>();

            for (Map.Entry<Integer, Position> entry : elves.entrySet()) {

                Position elf = entry.getValue();

                boolean hasNeighbour = false;
                for (Position position : allNeighbours) {
                    Position neighbour = elf.add(position);
                    if (elves.containsValue(neighbour)) {
                        hasNeighbour = true;
                        break;
                    }
                }

                if (!hasNeighbour) {
                    noNeighbour.add(entry.getKey());
                    continue;
                }


                for (int j = pos; j < pos + 4; j++) {

                    Position[] toCheck = positionsToCheck[j % 4];
                   // System.out.println("j=" + (j % 4));

                    boolean canMove = true;
                    for (int k = 0; k < 3; k++) {
                        Position positionToCheck = elf.add(toCheck[k]);
                        if (positionToCheck != null && elves.containsValue(positionToCheck)) {
                            canMove = false;
                            break;
                        }
                    }

                    if (canMove) {
                       Position newPosition = elf.add(toCheck[0]);
                       proposedPositions.put(entry.getKey(), newPosition);
                       count.merge(newPosition, 1, Integer::sum);
                       break;
                    }

                }
            }

            boolean elfMoved = false;
            Map<Integer, Position> result = new HashMap<>();
            for (Map.Entry<Integer, Position> entry : elves.entrySet()) {
                Integer key = entry.getKey();
                Position elf = entry.getValue();

                if (noNeighbour.contains(key)) {
                    result.put(key, elf);
                    continue;
                }

                Position proposed = proposedPositions.get(key);
                int proposedCount = count.getOrDefault(proposed, 0);
                if (proposedCount > 1) {
                    result.put(key, elf);
                    continue;
                } else {
                    if (proposed != null) {
                        result.put(key, proposed);
                        elfMoved = true;
                    } else {
                        result.put(key, elf);
                    }

                }
            }
            elves = result;
            pos++;
            pos %= 4;

            if (!elfMoved) {
                break;
            }

           /* for (int ii = -2; ii <= 9; ii++) {
                for (int jj = -3; jj <= 10; jj++) {
                    if (elves.containsValue(new Position(ii, jj))) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println();
            }
            System.out.println();*/
        }

        int minI = Integer.MAX_VALUE;
        int minJ = Integer.MAX_VALUE;
        int maxI = Integer.MIN_VALUE;
        int maxJ = Integer.MIN_VALUE;

        for (Map.Entry<Integer, Position> entry : elves.entrySet()) {
            Position position = entry.getValue();
            if (position.i > maxI) {
                maxI = position.i;
            }
            if (position.i < minI) {
                minI = position.i;
            }
            if (position.j > maxJ) {
                maxJ = position.j;
            }
            if (position.j < minJ) {
                minJ = position.j;
            }
        }

        int total = (maxI - minI + 1) * (maxJ - minJ + 1);
        System.out.println(total - elves.size());
    }

    @Override
    public void part2(List<String> input) {

    }
}
