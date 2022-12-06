package net.bojana.advent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

abstract public class SingleLineInput extends FileInput {

    public String getInput(int day, boolean isTest) throws IOException {
        String fileName = getFileName(day, isTest);
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8).get(0);
    }

    abstract public void part1(String input);

    abstract public void part2(String input);
}
