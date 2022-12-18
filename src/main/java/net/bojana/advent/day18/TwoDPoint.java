package net.bojana.advent.day18;

import java.util.Objects;

public class TwoDPoint {
    int x, y;

    TwoDPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwoDPoint twoDPoint = (TwoDPoint) o;
        return x == twoDPoint.x && y == twoDPoint.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
