package net.bojana.advent.day10;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day10 extends MultiLineInput {

    public static void main(String[] args) throws IOException {
        Day10 run = new Day10();
        List<String> input = run.getInput(10, false);
        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(List<String> input) {
        Set<Integer> interestingIndexes = new HashSet<>();
        interestingIndexes.add(20);
        interestingIndexes.add(60);
        interestingIndexes.add(100);
        interestingIndexes.add(140);
        interestingIndexes.add(180);
        interestingIndexes.add(220);

        int max = input.size() * 2;
        int operationIndex = 0;
        List<Operation> operations = input.stream().map(Operation::new).collect(Collectors.toList());

        long result = 0;
        int x = 1;
        boolean inX = false;
        int increment = 0;

        for(int i = 1; i < max; i++) {
            if (interestingIndexes.contains(i)) {
                result += (long) i * x;
                System.out.println(i + " " + x + " " + result);
            }
            if (inX) {
                inX = false;
                x += increment;
                continue;
            }

            if (operationIndex >= operations.size()) {
                break;
            }
            Operation nextOperation = operations.get(operationIndex);

            if (nextOperation.operationType == OperationType.NOOP) {
                operationIndex++;
                continue;
            } else if (nextOperation.operationType == OperationType.ADDX) {
                increment = nextOperation.operands.get(0);
                inX = true;
                operationIndex++;
            }
        }
        System.out.println(result);
    }

    @Override
    public void part2(List<String> input) {
        int max = input.size() * 2;
        int operationIndex = 0;
        List<Operation> operations = input.stream().map(Operation::new).collect(Collectors.toList());

        int x = 1;
        boolean inX = false;
        int increment = 0;

        int pixelDrawn = 0;

        for(int i = 1; i < max; i++) {
            pixelDrawn = (i -1) % 40;

            if ((i - 1) % 40 == 0) {
                System.out.println();
            }

            if (pixelDrawn == x - 1 || pixelDrawn == x || pixelDrawn == x + 1) {
                System.out.print("##");
            } else {
            }


            if (inX) {
                inX = false;
                x += increment;
                continue;
            }

            if (operationIndex >= operations.size()) {
                break;
            }
            Operation nextOperation = operations.get(operationIndex);

            if (nextOperation.operationType == OperationType.NOOP) {
                operationIndex++;
                continue;
            } else if (nextOperation.operationType == OperationType.ADDX) {
                increment = nextOperation.operands.get(0);
                inX = true;
                operationIndex++;
            }
        }
    }
}
