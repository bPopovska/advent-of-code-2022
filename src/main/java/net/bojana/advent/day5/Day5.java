package net.bojana.advent.day5;

import net.bojana.advent.Day;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 extends Day {
    Map<Integer, Stack<Character>> stacks = new HashMap<>();
    List<Integer> stackIds = new LinkedList<>();

    List<Instruction> instructions = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        Day5 run = new Day5();

        List<String> input = run.getInput(5, false);

        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(List<String> input) {

        parseInput(input);

        for (Instruction instruction : instructions) {
            Stack<Character> source = stacks.get(instruction.sourceId);
            Stack<Character> destination = stacks.get(instruction.destinationId);

            for (int i = 0; i < instruction.count; i++) {
                if (!source.isEmpty()) {
                    char c = source.pop();
                    destination.add(c);
                }
            }
        }

        printResult();
    }


    @Override
    public void part2(List<String> input) {
        stacks = new HashMap<>();
        stackIds = new LinkedList<>();
        instructions = new LinkedList<>();

        parseInput(input);

        for (Instruction instruction : instructions) {
            Stack<Character> source = stacks.get(instruction.sourceId);
            Stack<Character> destination = stacks.get(instruction.destinationId);

            List<Character> fromSource = new LinkedList<>();

            for (int i = 0; i < instruction.count; i++) {
                if (!source.isEmpty()) {
                    char c = source.pop();
                    fromSource.add(c);
                }
            }

            for (int i = fromSource.size() - 1; i >= 0; i--) {
                destination.add(fromSource.get(i));
            }
        }

        printResult();
    }

    private void parseInput(List<String> input) {
        Stack<String> inputLines =  new Stack<>();
        boolean startReadingInstructions = false;

        for (String line : input) {
            if (line.isBlank()) {
                startReadingInstructions = true;
                continue;
            }
            if (!startReadingInstructions) {
                inputLines.add(line + " ");
            } else {
                instructions.add(new Instruction(line));
            }
        }

        String lineIds = inputLines.pop();
        stackIds = Arrays.stream(lineIds.split(" "))
                .map(String::trim)
                .filter(t -> !t.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        while (!inputLines.isEmpty()) {
            String line = inputLines.pop();

            for (int i = 0, count = 0; i < line.length(); i+=4, count++) {
                char c = line.charAt(i + 1);
                if (c != ' ') {
                   int stackId = stackIds.get(count);
                   if (stacks.containsKey(stackId)) {
                       stacks.get(stackId).add(c);
                   } else {
                       Stack<Character> st = new Stack<>();
                       st.add(c);
                       stacks.put(stackId, st);
                   }
                }
            }
        }
    }

    private void printResult() {
        for (Integer stackId : stackIds) {
            Stack<Character> s = stacks.get(stackId);
            if (!s.isEmpty()) {
                System.out.print(s.peek() + "");
            } else {
                System.out.print("  ");
            }
        }
        System.out.println();
    }

}
