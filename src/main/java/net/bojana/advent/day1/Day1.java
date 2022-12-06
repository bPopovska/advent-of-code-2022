package net.bojana.advent.day1;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day1 extends MultiLineInput {

    private static final String FILE_NAME = "day1.txt";

    public static void main(String[] args) throws IOException {

        Day1 run = new Day1();

        List<String> input = run.getInput(1, false);

       run.part1(input);
       run.part2(input);
    }


    @Override
    public void part2(List<String> input) {
        // Part 1
        long sum = 0;
        List<Long> results = new LinkedList<>();
        for (String string : input) {
            if (!string.isBlank()) {
                long calories = Long.parseLong(string);
                sum += calories;
            } else {
                results.add(sum);
                sum = 0;
            }
        }
        results.sort((a, b) -> (int) (b - a));

        System.out.println(results.get(0) + results.get(1) + results.get(2));
    }

    @Override
    public void part1(List<String> input) {
        // Part 1
        long sum = 0;
        long max = 0;
        for (String string : input) {
            if (!string.isBlank()) {
                long calories = Long.parseLong(string);
                sum += calories;
            } else {
                if (sum > max){
                    max = sum;
                }
                sum = 0;
            }
        }
        if (sum > max){
            max = sum;
        }
        System.out.println(max);

    }

}
