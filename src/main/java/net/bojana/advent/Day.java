package net.bojana.advent;

import net.bojana.advent.day1.Day1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

abstract public class Day {
    public List<String> getInput(int day, boolean isTest) throws IOException {
        String fileName = Objects.requireNonNull(Day1.class.getClassLoader().getResource(isTest ? "day" + day + "_test.txt" : "day" + day + ".txt")).getFile();
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    abstract public void part1(List<String> input);

    abstract public void part2(List<String> input);
}
