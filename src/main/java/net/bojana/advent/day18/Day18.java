package net.bojana.advent.day18;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day18 extends MultiLineInput {

    List<Point> offsetFromBottom = List.of(new Point(1, 1, 0), new Point(1, 0, 1), new Point(0, 1, 1));
    List<Point> offsetFromTop = List.of(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));

    public static void main(String[] args) throws IOException {
        Day18 run = new Day18();

        List<String> input = run.getInput(18, true);
        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(List<String> input) {

        List<Point> points = input.stream().map(Point::new).collect(Collectors.toList());

        int result = getAreaOfCubes(points);

        System.out.println(result);
    }

    private int getAreaOfCubes(List<Point> points) {
        int result = points.size() * 6;

        Set<Square> uniqueSquares = new HashSet<>();

        for (Point point : points) {
            for (Point offset : offsetFromBottom) {
                Square square = new Square(point, point.addOffset(offset));
                if (uniqueSquares.contains(square)) {
                    result -= 2;
                }
                uniqueSquares.add(square);
            }

            for (Point offset : offsetFromTop) {
                Square square = new Square(point.addOffset(offset), point.addOffset(new Point(1, 1, 1)));
                if (uniqueSquares.contains(square)) {
                    result -= 2;
                }
                uniqueSquares.add(square);
            }
        }
        return result;
    }

    @Override
    public void part2(List<String> input) {
        List<Point> points = input.stream().map(Point::new).collect(Collectors.toList());

        Set<Point> hollowSquares = getHollowSquares(points);
        int areaOfHollow = getAreaOfCubes(new ArrayList<>(hollowSquares));
        int areaOfTotal = getAreaOfCubes(points);

        System.out.println(areaOfTotal - areaOfHollow);
    }

    private Set<Point> getHollowSquares(List<Point> points) {

        Set<Point> result = new HashSet<>();

        // group by z
        Map<Integer, List<TwoDPoint>> groupedByZ = new HashMap<>();

        for (Point p : points) {
            TwoDPoint twoD = new TwoDPoint(p.x, p.y);
            groupedByZ.merge(p.z, List.of(twoD), (l1, l2) -> Stream.concat(l1.stream(), Stream.of(twoD)).collect(Collectors.toList()));
        }

        for (Map.Entry<Integer, List<TwoDPoint>> entry : groupedByZ.entrySet()) {
            Integer z = entry.getKey();
            List<TwoDPoint> value = entry.getValue();

            if (value.size() == 1) {
                continue;
            }

            Map<Integer, List<Integer>> groupedByX = new HashMap<>();
            for (TwoDPoint tdp : value) {
                groupedByX.merge(tdp.x, new ArrayList<>(List.of(tdp.y)), (l1, l2) -> Stream.concat(l1.stream(), Stream.of(tdp.y)).collect(Collectors.toList()));
            }

            Set<TwoDPoint> missingValuesX = new HashSet<>();
            for (Map.Entry<Integer, List<Integer>> e : groupedByX.entrySet()) {
                int x = e.getKey();
                if (e.getValue().size() == 1) {
                    continue;
                }
                Collections.sort(e.getValue());
                List<Integer> list = e.getValue();

                for (int i = 0; i < list.size() - 1; i++) {
                    if (list.get(i) != list.get(i + 1) - 1) {
                        for (int j = list.get(i) + 1; j < list.get(i + 1); j++) {
                            missingValuesX.add(new TwoDPoint(x, j));
                        }
                    }
                }
            }

            Map<Integer, List<Integer>> groupedByY = new HashMap<>();
            for (TwoDPoint tdp : value) {
                groupedByY.merge(tdp.y, new ArrayList<>(List.of(tdp.x)), (l1, l2) -> Stream.concat(l1.stream(), Stream.of(tdp.x)).collect(Collectors.toList()));
            }

            Set<TwoDPoint> missingValuesY = new HashSet<>();
            for (Map.Entry<Integer, List<Integer>> e : groupedByY.entrySet()) {
                int y = e.getKey();
                if (e.getValue().size() == 1) {
                    continue;
                }
                Collections.sort(e.getValue());
                List<Integer> list = e.getValue();

                for (int i = 0; i < list.size() - 1; i++) {
                    if (list.get(i) != list.get(i + 1) - 1) {
                        for (int j = list.get(i) + 1; j < list.get(i + 1); j++) {
                            missingValuesY.add(new TwoDPoint(j, y));
                        }
                    }
                }
            }

            for (TwoDPoint tp : missingValuesX) {
                if (missingValuesY.contains(tp)) {
                    result.add(new Point(tp.x, tp.y, z));
                }
            }
        }


        return result;
    }

}
