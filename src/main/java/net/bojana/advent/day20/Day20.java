package net.bojana.advent.day20;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day20 extends MultiLineInput {

    List<Long> original;
    LinkedList<Long> numbers;
    int N;

    public static void main(String[] args) throws IOException {
        Day20 day20 = new Day20();
        List<String> input = day20.getInput(20, false);
        day20.part1(input);
        day20.part2(input);
    }

    @Override
    public void part1(List<String> input) {
        execute(input, 1, 1);
    }

    @Override
    public void part2(List<String> input) {
        execute(input, 10, 811589153L);
    }

    private void execute(List<String> input, int iterations, long mul) {
        N = input.size();
        original = new LinkedList<>();
        numbers = new LinkedList<>();

        // numbers are not unique
        for (int i = 0; i < N; i++) {
            long num = Long.parseLong(input.get(i)) * mul;
            // start off by mapping indexes one to one
            original.add(num);
            numbers.add(num);
        }

        for (int i = 0; i < iterations; i++) {
            for (Long number : original) {
                int mod = N - 1;
                int currentIndex = numbers.indexOf(number);

                if (number == 0) {
                    continue;
                }
                long newIndex = ((number % mod) + currentIndex) % mod;

                if (newIndex <= 0) {
                    newIndex += mod;
                }

                numbers.add((int) (newIndex > currentIndex ? newIndex + 1 : newIndex), number);
                numbers.remove(newIndex < currentIndex ? currentIndex + 1 : currentIndex);
            }
        }


        int zeroAtIndex = -1;
        for (int i = 0; i < N; i++) {
            if (numbers.get(i) == 0) {
                zeroAtIndex = i;
            }
        }

        System.out.println(zeroAtIndex);

        int indexFirst = (zeroAtIndex + 1000) % N;
        int indexSecond = (zeroAtIndex + 2000) % N;
        int indexThird = (zeroAtIndex + 3000) % N;

        System.out.println(numbers.get(indexFirst) + numbers.get(indexSecond) + numbers.get(indexThird));

    }
}
