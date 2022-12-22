package net.bojana.advent.day22;

public class State {
    int positionI;
    int positionJ;

    // 0 - right >
    // 1 - down v
    // 2 - left <
    // 3 - up ^
    int direction;

    State (int positionI, int positionJ, int direction) {
        this.positionI = positionI;
        this.positionJ = positionJ;
        this.direction = direction;
    }

    public long getValue() {
        return 1000 * (positionI + 1) + 4 * (positionJ + 1) + direction;
    }

    @Override
    public String toString() {
        return (positionI + 1) +
                " " + (positionJ + 1) +
                " " + direction;
    }
}
