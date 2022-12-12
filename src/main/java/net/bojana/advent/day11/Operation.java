package net.bojana.advent.day11;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operation {
    Operand operand;
    Long operator;
    boolean self;

    Operation(String string) {
        Pattern test = Pattern.compile("  Operation: new = old (.*) (.*)");

        Matcher matcher = test.matcher(string);
        if (matcher.find()) {
            String operand = matcher.group(1);
            switch (operand) {
                case "*":
                    this.operand = Operand.MUL;
                    break;
                case "+":
                    this.operand = Operand.ADD;
                    break;
                default:
                    throw new RuntimeException("Unknown operator");
            }
            String op = matcher.group(2);
            if ("old".equals(op)) {
                this.self = true;
            } else {
                this.operator = Long.parseLong(matcher.group(2));
            }
        } else {
            throw new RuntimeException("Invalid operation format");
        }
    }
}
