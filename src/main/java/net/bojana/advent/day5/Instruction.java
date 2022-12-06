package net.bojana.advent.day5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instruction {

    private static final Pattern PATTERN = Pattern.compile("move ([0-9]*) from ([0-9]*) to ([0-9]*)");

    Integer sourceId, destinationId, count;

    Instruction(Integer sourceId, Integer destinationId, Integer count) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.count = count;
    }

    Instruction(String input) {
        Matcher matcher = PATTERN.matcher(input);

        if (matcher.find()) {
            sourceId = Integer.parseInt(matcher.group(2));
            destinationId = Integer.parseInt(matcher.group(3));
            count = Integer.parseInt(matcher.group(1));
        }
    }
}
