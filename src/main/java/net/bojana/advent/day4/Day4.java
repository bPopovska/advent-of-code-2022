package net.bojana.advent.day4;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 extends MultiLineInput {

    public static void main(String[] args) throws IOException {
        Day4 run = new Day4();
        List<String> input = run.getInput(4, false);

        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(List<String> input) {
        Long result = input.stream().map(Pair::new).map(Pair::fullOverlap).filter(v -> v).collect(Collectors.counting());
        System.out.println(result);
    }

    @Override
    public void part2(List<String> input) {
        Long result = input.stream().map(Pair::new).map(Pair::overlap).filter(v -> v).collect(Collectors.counting());
        System.out.println(result);
    }
}
