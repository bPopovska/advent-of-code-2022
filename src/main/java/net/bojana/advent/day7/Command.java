package net.bojana.advent.day7;

import java.util.LinkedList;
import java.util.List;

public class Command {
    CommandType commandType;
    List<String> arguments;

    Command(CommandType commandType, List<String> arguments) {
        this.commandType = commandType;
        this.arguments = arguments;
    }

    Command(String s) {
        String[] parts = s.split(" ");
        String command = parts[1];
        if (command.equals("ls")) {
            commandType = CommandType.LS;
        } else if (command.equals("cd")){
            commandType = CommandType.CD;
            arguments = new LinkedList<>();

            for (int i = 2; i < parts.length; i++) {
                arguments.add(parts[i]);
            }
        }
    }
}
