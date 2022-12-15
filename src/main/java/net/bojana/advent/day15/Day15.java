package net.bojana.advent.day15;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day15 extends MultiLineInput {

    public static void main(String[] args) throws IOException {
        Day15 run = new Day15();

        List<String> input = run.getInput(15, false);
        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(List<String> input) {

        final Long Y = 2000000L;

        List<Pair> pairs = input.stream().map(Pair::new).collect(Collectors.toList());
        List<Interval> intervals = new ArrayList<>();

        Set<Long> beaconsOnTheLine = new HashSet<>();

        for (Pair pair : pairs) {
            Point beacon = pair.beacon;
            if (beacon.y == Y) {
                beaconsOnTheLine.add(beacon.x);
            }

            long yDiff = Math.abs(pair.sensor.y - Y);

            long distance = pair.getManhattan();
            long diff = distance - yDiff;
            if (diff < 0) {
                continue;
            }
            long start = pair.sensor.x - diff;
            long end = pair.sensor.x + diff;
            intervals.add(new Interval(start, end));
        }

        Collections.sort(intervals);

        Stack<Interval> stack = new Stack<>();
        List<Interval> intervalList = new ArrayList<>();

        for (Interval interval : intervals) {
            stack.add(interval);
        }

        while (stack.size() > 1) {
            Interval i1 = stack.pop();
            Interval i2 = stack.pop();
            if (i1.overlap(i2)) {
                Interval merged = i1.merge(i2);
                stack.push(merged);
            } else {
                intervalList.add(i1);
                stack.push(i2);
            }
        }

        intervalList.add(stack.pop());
        int sum = 0;

        for (Interval interval : intervalList) {
            sum += Math.abs(interval.end - interval.start) + 1;
        }

        for (Long beacon : beaconsOnTheLine) {
            for (Interval interval : intervalList) {
                if (interval.contains(beacon)) {
                    sum--;
                    break;
                }
            }
        }

        System.out.println(sum);
    }

    @Override
    public void part2(List<String> input) {
        final Integer MAX_DIMENSION = 4000000;

        long xx = 3012821;
        long yy = 3042458;

        List<Pair> pairs = input.stream().map(Pair::new).collect(Collectors.toList());
        for (int Y = 0; Y < MAX_DIMENSION; Y++){
            List<Interval> intervals = new ArrayList<>();

            Set<Long> beaconsOnTheLine = new HashSet<>();

            for (Pair pair : pairs) {
                Point beacon = pair.beacon;
                if (beacon.y == Y) {
                    beaconsOnTheLine.add(beacon.x);
                }

                long yDiff = Math.abs(pair.sensor.y - Y);

                long distance = pair.getManhattan();
                long diff = distance - yDiff;
                if (diff < 0) {
                    continue;
                }
                long start = pair.sensor.x - diff;
                long end = pair.sensor.x + diff;
                intervals.add(new Interval(start, end));
            }

            Collections.sort(intervals);

            Stack<Interval> stack = new Stack<>();
            List<Interval> intervalList = new ArrayList<>();

            for (Interval interval : intervals) {
                stack.add(interval);
            }

            while (stack.size() > 1) {
                Interval i1 = stack.pop();
                Interval i2 = stack.pop();
                if (i1.overlap(i2)) {
                    Interval merged = i1.merge(i2);
                    stack.push(merged);
                } else {
                    intervalList.add(i1);
                    stack.push(i2);
                }
            }

            intervalList.add(stack.pop());

            for (Interval interval : intervalList) {
                interval.normalize(0, MAX_DIMENSION);
                if (interval.getLength() != MAX_DIMENSION) {
                    yy = Y;
                    xx = interval.end + 1;
                }
            }
        }

        System.out.println(xx * MAX_DIMENSION + yy);

    }
}
