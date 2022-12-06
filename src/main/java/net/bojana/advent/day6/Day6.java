package net.bojana.advent.day6;

import net.bojana.advent.SingleLineInput;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day6 extends SingleLineInput {

    public static void main(String[] args) throws IOException {
        Day6 run = new Day6();

        String input = run.getInput(6, false);

        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(String input) {
        execute(input, 4);
    }

    @Override
    public void part2(String input) {
        execute(input, 14);
    }

    private void execute(String input, int WINDOW_SIZE) {
        int result = -1;

        Map<Character, Integer> countOccurences = new HashMap<>();

        for (int i = 0; i < WINDOW_SIZE; i++) {
            countOccurences.merge(input.charAt(i), 1, Integer::sum);
        }

        for (int i = 1; i < input.length() - WINDOW_SIZE + 1; i++) {

            if (checkIfUnique(countOccurences)) {
                result = i;
                break;
            }

            char toBeRemoved = input.charAt(i - 1);
            char toBeAdded = input.charAt(i + WINDOW_SIZE - 1);
            countOccurences.put(toBeRemoved, countOccurences.get(toBeRemoved) - 1);
            countOccurences.merge(toBeAdded, 1, Integer::sum);
        }

        if (result == -1 && checkIfUnique(countOccurences)) {
            result = input.length() - WINDOW_SIZE;
        }

        System.out.println(result + WINDOW_SIZE -1);
    }

    private boolean checkIfUnique(Map<Character, Integer> map) {
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 0) {
                continue;
            }
            if (entry.getValue() != 1) {
                return false;
            }
        }
        return true;
    }
}
