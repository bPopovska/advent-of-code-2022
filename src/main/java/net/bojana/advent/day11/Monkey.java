package net.bojana.advent.day11;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Monkey implements Comparable {
    int id;
    Queue<Long> queue = new LinkedList<>();
    Operation operation;
    int divider;
    int monkeyIfTrue;
    int monkeyIfFalse;

    boolean divideByThree;

    long processingCount = 0;

    Monkey(List<String> input, boolean divideByThree) {
        initMonkeyId(input.get(0));
        initQueue(input.get(1));
        operation = new Operation(input.get(2));
        initDivider(input.get(3));
        initMonkeyIfTrue(input.get(4));
        initMonkeyIfFalse(input.get(5));
        this.divideByThree = divideByThree;
    }

    private void initMonkeyIfFalse(String s) {
        Pattern pattern = Pattern.compile("   If false: throw to monkey ([0-9]*)");
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            this.monkeyIfFalse = Integer.parseInt(matcher.group(1));
        } else {
            throw new RuntimeException("Cannot parse divider");
        }
    }

    private void initMonkeyIfTrue(String s) {
        Pattern pattern = Pattern.compile("   If true: throw to monkey ([0-9]*)");
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            this.monkeyIfTrue = Integer.parseInt(matcher.group(1));
        } else {
            throw new RuntimeException("Cannot parse divider");
        }
    }

    private void initDivider(String s) {
        Pattern pattern = Pattern.compile(" Test: divisible by ([0-9]*)");
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            this.divider = Integer.parseInt(matcher.group(1));
        } else {
            throw new RuntimeException("Cannot parse divider");
        }
    }

    private void initQueue(String s) {
        Pattern pattern = Pattern.compile("  Starting items: (.*)");
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            this.queue =
                    Arrays.stream(matcher.group(1).split(","))
                            .map(String::trim)
                            .map(Long::parseLong)
                            .collect(Collectors.toCollection(LinkedList::new));
        } else {
            throw new RuntimeException("Cannot input queue");
        }
    }

    private void initMonkeyId(String s) {
        Pattern pattern = Pattern.compile("Monkey ([0-9]*):");
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            this.id = Integer.parseInt(matcher.group(1));
        } else {
            throw new RuntimeException("Cannot parse monkey id");
        }
    }


    Long applyOperation(Long value) {
        Long val = operation.self ? value : operation.operator;
        Long result = null;
        switch (operation.operand) {
            case ADD:
                result = value + val;
                break;
            case MUL:
                result = value * val;
                break;
        }

        return result;
    }

    List<ProcessResult> processQueue() {
        Long magicNumber = (long) 11 * 19 * 7 * 17 * 3 * 5 * 13 * 2;
        List<ProcessResult> results = new LinkedList<>();
        while (!queue.isEmpty()) {
            Long oldValue = queue.poll();
            Long newValue = applyOperation(oldValue);

            if (divideByThree) {
                newValue = newValue / 3;
            } else {
                newValue = newValue % magicNumber;
            }

            int monkey = newValue % divider == 0 ? monkeyIfTrue : monkeyIfFalse;

            results.add(new ProcessResult(newValue, monkey));
            processingCount++;
        }

        return results;
    }

    void enqueue(Long value) {
        this.queue.add(value);
    }

    @Override
    public int compareTo(Object o) {
        Monkey m = (Monkey) o;
        if (m.processingCount == this.processingCount) {
            return 0;
        } else if (m.processingCount > this.processingCount) {
            return 1;
        } else {
            return -1;
        }
    }
}
