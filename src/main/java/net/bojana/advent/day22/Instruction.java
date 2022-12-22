package net.bojana.advent.day22;

public class Instruction {

    Direction direction;
    Integer steps;

    Instruction(Character direction) {
        this.direction = 'L' == direction ? Direction.LEFT : Direction.RIGHT;
        this.steps = null;
    }

    Instruction(Integer steps) {
        this.direction = null;
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "direction=" + direction +
                ", steps=" + steps +
                '}';
    }
}
