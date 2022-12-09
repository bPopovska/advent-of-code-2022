package net.bojana.advent.day9;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Position {
    int x, y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void move (Direction direction) {
        this.x += direction.x;
        this.y += direction.y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

enum Direction {

    DOWN(0, -1),
    UP(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    public final int x, y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Direction create(String input) {
        switch (input) {
            case "D":
                return DOWN;
            case "U":
                return UP;
            case "L":
                return LEFT;
            case "R":
                return RIGHT;
         }
        return null;
    }
}

public class Day9 extends MultiLineInput {


    boolean areNeighbours(Position tail, Position head) {
        return Math.abs(tail.x - head.x) <= 1 && Math.abs(tail.y - head.y) <= 1;
    }

    public static void main(String[] args) throws IOException {
        Day9 run = new Day9();
        List<String> input = run.getInput(9, false);

        run.part1(input);
        run.part2(input);
    }

    private void adjustPosition(Position tail, Position head) {
        if(tail.x > head.x) {
            tail.x--;
        }
        else if(tail.x < head.x) {
            tail.x++;
        }

        if(tail.y > head.y) {
            tail.y--;
        }
        else if(tail.y < head.y) {
            tail.y++;
        }
    }

    @Override
    public void part1(List<String> input) {
        Position head = new Position(0, 0);
        Position tail = new Position(0, 0);

        Set<String> seenPositions = new HashSet<>();

        seenPositions.add(tail.x + " " + tail.y);

        for (String line : input) {
            Direction direction = Direction.create(line.split(" ")[0]);
            int distance = Integer.parseInt(line.split(" ")[1]);

            for (int i = 0; i < distance; i++) {
                head.move(direction);
                if (!areNeighbours(tail, head)) {
                    adjustPosition(tail, head);
                }
                seenPositions.add(tail.x + " " + tail.y);
            }
        }

        System.out.println(seenPositions.size());
    }

    @Override
    public void part2(List<String> input) {

        int SIZE = 10;
        Position head = new Position(0, 0);
        Position[] tail = new Position[SIZE];

        for (int i = 0; i < SIZE; i++) {
            tail[i] = new Position(0, 0);
        }
        Set<String> seenPositions = new HashSet<>();

        seenPositions.add(tail[0].x + " " + tail[0].y);

        for (String line : input) {
            Direction direction = Direction.create(line.split(" ")[0]);
            int distance = Integer.parseInt(line.split(" ")[1]);

            for (int i = 0; i < distance; i++) {
                head.move(direction);
                Position neighbour = head;
                for (int j = 0; j < SIZE; j++) {
                    if (!areNeighbours(tail[j], neighbour)) {
                        adjustPosition(tail[j], neighbour);
                        neighbour = tail[j];
                    }
                }
                seenPositions.add(tail[SIZE - 1].x + " " + tail[SIZE - 1].y);
            }
        }

        System.out.println(seenPositions.size());
    }
}
