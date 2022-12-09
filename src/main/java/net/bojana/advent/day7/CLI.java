package net.bojana.advent.day7;

import java.util.LinkedList;
import java.util.List;

public class CLI {
    List<Command> history = new LinkedList<>();

    public void addCommand(Command command) {
        history.add(command);
    }
}
