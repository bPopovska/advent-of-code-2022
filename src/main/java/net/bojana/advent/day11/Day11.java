package net.bojana.advent.day11;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.*;

public class Day11 extends MultiLineInput {

    public static void main(String[] args) throws IOException {
        Day11 run = new Day11();

        List<String> input = run.getInput(11, false);

        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(List<String> input) {
        boolean divideByThree = true;
        int rounds = 20;

        process(divideByThree, rounds, input);
    }

    @Override
    public void part2(List<String> input) {
        boolean divideByThree = false;
        int rounds = 10000;

        process(divideByThree, rounds, input);
    }

    private void process(boolean divideByThree, int rounds, List<String> input) {
        List<Monkey> allMonkeyes = new LinkedList<>();

        for (int i = 0; i < input.size(); i += 7) {
            Monkey newMonkey = new Monkey(input.subList(i, i + 7), divideByThree);
            allMonkeyes.add(newMonkey);
        }

        for (int i = 0; i < rounds; i++) {
           // System.out.println("i " + i);
            for (Monkey monkey : allMonkeyes) {
                List<ProcessResult> result = monkey.processQueue();

                for (ProcessResult pr : result) {
                    allMonkeyes.get(pr.monkeyId).enqueue(pr.queueValue);
                }
            }
        }

        PriorityQueue<Monkey> priorityQueue = new PriorityQueue<>();
        for (Monkey monkey : allMonkeyes) {
            priorityQueue.add(monkey);
        }

        System.out.println(priorityQueue.poll().processingCount * priorityQueue.poll().processingCount);
    }
}
