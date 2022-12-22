package net.bojana.advent.day21;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Node {

    private static final Pattern VALUE_PATTERN = Pattern.compile("^(.*): ([0-9]*)$");
    private static final Pattern OPERATION_PATTERN = Pattern.compile("^(.*): (.*) (\\+|\\-|\\/|\\*) (.*)$");

    String name;
    long number;
    Operation operation;
    String left;
    String right;

    Node(String line) {
        Matcher valueMatcher = VALUE_PATTERN.matcher(line);
        Matcher operationMatcher = OPERATION_PATTERN.matcher(line);

        if (valueMatcher.find()) {
            this.name = valueMatcher.group(1);
            this.number = Long.parseLong(valueMatcher.group(2));
            this.left = null;
            this.right = null;
        } else if (operationMatcher.find()) {
            this.name = operationMatcher.group(1);
            this.left = operationMatcher.group(2);
            this.operation = Operation.fromVal(operationMatcher.group(3));
            this.right = operationMatcher.group(4);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", operation=" + operation +
                ", left='" + left + '\'' +
                ", right='" + right + '\'' +
                '}';
    }
}
