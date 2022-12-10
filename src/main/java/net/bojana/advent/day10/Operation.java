package net.bojana.advent.day10;

import java.util.List;

public class Operation {
    OperationType operationType;
    List<Integer> operands;

    Operation(String line) {
        // todo
        String operation = line.split(" ")[0];
        if (operation.equals("noop")) {
            operationType = OperationType.NOOP;
        } else if (operation.equals("addx")) {
            operationType = OperationType.ADDX;
            operands = List.of(Integer.parseInt(line.split(" ")[1]));
        }
    }
}
