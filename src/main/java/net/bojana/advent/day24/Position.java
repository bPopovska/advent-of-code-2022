package net.bojana.advent.day24;

import java.util.Objects;

public class Position {
    public int i, j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    Position addOffsetPerson(Position offset, int N, int M) {
        Position newPos = new Position(i + offset.i, j + offset.j);
        if (newPos.i == N -1 && newPos.j == M - 2) {
            return newPos;
        }

        if (newPos.i > 0 && newPos.j > 0 && newPos.i < N -1 && newPos.j < M -1) {
            return newPos;
        }
        return null;
    }

    public Position move(Position offset) {
        return new Position(i + offset.i, j + offset.j);
    }

    Position addOffsetBlizzard(Position offset, int N, int M) {
        Position newPos = new Position(i + offset.i, j + offset.j);

        if (newPos.i > 0 && newPos.j > 0 && newPos.i < N -1 && newPos.j < M -1) {
            return newPos;
        } else if (newPos.i == 0) {
            return new Position(N - 2, newPos.j);
        } else if (newPos.i == N - 1) {
            return new Position(1, newPos.j);
        } else if (newPos.j == 0) {
            return new Position(newPos.i, M - 2);
        } else if (newPos.j == M - 1) {
            return new Position(newPos.i, 1);
        }

        return newPos;
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

    @Override
    public String toString() {
        return "Position{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}
