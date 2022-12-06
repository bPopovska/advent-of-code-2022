package net.bojana.advent.day3;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day3 extends MultiLineInput {
    public static void main(String[] args) throws IOException {
        Day3 run = new Day3();

        List<String> input = run.getInput(3, false);

        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(List<String> input) {

        Optional<Integer> result = input.stream()
                .map(this::getStringValue)
                .filter(Objects::nonNull)
                .reduce(Integer::sum);
        result.ifPresent(System.out::println);
    }

    @Override
    public void part2(List<String> input) {
        int sum = 0;
        for (int i = 0; i < input.size() - 2; i+=3) {
            boolean[][] count =
                    countOccurencesInGroup(new String[] {input.get(i), input.get(i + 1), input.get(i + 2)});
            int result = getCommonItemIndex(count);
            sum += result;
        }
        System.out.println(sum);
    }

    private int getCommonItemIndex(boolean[][] items) {
        for (int i = 0; i < items[0].length; i++) {
            for(int j = 0; j < items.length; j++) {
                items[0][i] &= items[j][i];
            }
            if (items[0][i]) {
                return i;
            }
        }
        return -1;
    }

    private boolean[][] countOccurencesInGroup(String[] group) {
        boolean[][] result = new boolean[3][53];
        for (int i = 0; i < result.length; i++) {
            String item = group[i];

            for (char c : item.toCharArray()) {
                int value = getNumericValue(c);
                result[i][value] = true;
            }
        }
        return result;
    }

    private Integer getStringValue(String input) {
        boolean[] firstHalf = new boolean[53];
        boolean[] secondHalf = new boolean[53];

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int numValue = getNumericValue(c);


            if (i < input.length() / 2) {
                firstHalf[numValue] = true;
            } else {
                secondHalf[numValue] = true;
            }
        }

        for (int i = 0; i < firstHalf.length; i++) {
            firstHalf[i] &= secondHalf[i];
        }

        OptionalInt result = IntStream.range(0, firstHalf.length)
                .filter(i -> firstHalf[i])
                .reduce(Integer::sum);

        return result.isPresent() ? result.getAsInt() : null;
    }

    private int getNumericValue(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 1;
        } else {
            return c - 'A' + 27;
        }
    }
}
