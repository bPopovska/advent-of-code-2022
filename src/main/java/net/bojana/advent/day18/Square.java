package net.bojana.advent.day18;

import java.util.Objects;

public class Square {
    Point lowerLeft;
    Point upperRight;

    Square(Point lowerLeft, Point upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return (Objects.equals(lowerLeft, square.lowerLeft) && Objects.equals(upperRight, square.upperRight))
                || (Objects.equals(lowerLeft, square.upperRight) && Objects.equals(upperRight, square.lowerLeft));
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerLeft, upperRight);
    }

    @Override
    public String toString() {
        return "Square{" +
                "lowerLeft=" + lowerLeft +
                ", upperRight=" + upperRight +
                '}';
    }
}
