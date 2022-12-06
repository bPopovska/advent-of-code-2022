package net.bojana.advent.day2;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Day2 extends MultiLineInput {

    public static void main(String[] args) throws IOException {
        Day2 run = new Day2();
        List<String> list = run.getInput(2, false);

        run.part1(list);
        run.part2(list);
    }

    @Override
    public void part1(List<String> input) {
        Optional<Integer> totalScore
                = input
                .stream()
                .map(val -> new RockPaperScissors(val, false))
                .map(RockPaperScissors::getScore)
                .reduce(Integer::sum);

        System.out.println(totalScore.get());
    }

    @Override
    public void part2(List<String> input) {
        Optional<Integer> totalScore
                = input
                .stream()
                .map(val -> new RockPaperScissors(val, true))
                .map(RockPaperScissors::getScore)
                .reduce(Integer::sum);

        System.out.println(totalScore.get());
    }
}
