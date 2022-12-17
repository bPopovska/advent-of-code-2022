package net.bojana.advent.day17;

public enum Direction {

    LEFT('<'), RIGHT('>');

    private char c;
    Direction(char c) {
        this.c = c;
    }

    public static Direction create(int i) {
        char c = (char) i;
        return c == '<' ? LEFT : RIGHT;
    }
}
