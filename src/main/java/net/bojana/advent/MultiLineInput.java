package net.bojana.advent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

abstract public class MultiLineInput extends FileInput {

    public List<String> getInput(int day, boolean isTest) throws IOException {
        String fileName = getFileName(day, isTest);
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    abstract public void part1(List<String> input);

    abstract public void part2(List<String> input);
}
