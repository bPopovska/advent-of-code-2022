package net.bojana.advent.day23;

import java.util.Objects;

public class Position {
    int i, j;

    Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    Position add(Position offset) {
       return new Position(i + offset.i, j + offset.j);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return i == position.i && j == position.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
