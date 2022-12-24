package net.bojana.advent.day24;

public enum Direction {
    RIGHT(">"), LEFT("<"), UP("^"), DOWN("v");

    private String value;

    Direction(String value) {
        this.value = value;
    }

    public static Direction ofValue(char c) {
        switch (c) {
            case '>':
                return RIGHT;
            case '<':
                return LEFT;
            case '^':
                return UP;
            case 'v':
                return DOWN;
        }
        return DOWN;
    }
}
