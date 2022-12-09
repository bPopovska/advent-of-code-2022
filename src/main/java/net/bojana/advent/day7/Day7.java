package net.bojana.advent.day7;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Day7 extends MultiLineInput {

    public static void main(String[] args) throws IOException {
        Day7 run = new Day7();
        List<String> input = run.getInput(7, false);

        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(List<String> input) {

        BigInteger MAX_DIR_SIZE = new BigInteger("100000");

        CLI cli = new CLI();
        Directory rootDirectory = new Directory("dir /");
        Directory currentDirectory = rootDirectory;

        for (String line : input) {
            if (line.startsWith("$")) {
                // command
                Command command = new Command(line);
                if (command.commandType.equals(CommandType.CD)) {
                    // navigate through the tree
                    if (command.arguments.get(0).equals("/")) {
                        currentDirectory = rootDirectory;
                    } else if (command.arguments.get(0).equals("..")) {
                        currentDirectory = currentDirectory.parent;
                    } else {
                        String directoryToNavigateTo = command.arguments.get(0);
                        currentDirectory = currentDirectory.children.get(directoryToNavigateTo);
                    }
                }

                cli.addCommand(command);
            } else {
                // output
                Directory newDirectory = new Directory(line);
                newDirectory.setParent(currentDirectory);
                currentDirectory.addChild(newDirectory);
            }
        }

        Map<Integer, List<Directory>> byLevels = new HashMap();
        Queue<Directory> allFiles = new LinkedList<>();
        Queue<Integer> allLevels = new LinkedList<>();

        allFiles.add(rootDirectory);
        allLevels.add(0);

        while (!allFiles.isEmpty()) {
            Directory dir = allFiles.poll();
            Integer level = allLevels.poll();

            if (dir == null) {
                continue;
            }

            if (!byLevels.containsKey(level)) {
                byLevels.put(level, new LinkedList<>());
            }

            byLevels.get(level).add(dir);

            for (Map.Entry<String, Directory> entry : dir.children.entrySet()) {
                allFiles.add(entry.getValue());
                allLevels.add(level + 1);
            }

        }

        for (int i = byLevels.size() - 1; i >= 0; i--) {
            if (!byLevels.containsKey(i)) {
                continue;
            }
            for (Directory dir : byLevels.get(i)) {
                if (dir.parent == null) {
                    continue;
                }
                dir.parent.addSize(dir.sizeInBytes);
            }
        }

        allFiles = new LinkedList<>();
        allFiles.add(rootDirectory);

        // Part 2
        BigInteger TOTAL_FILE_SIZE = new BigInteger("70000000");

        BigInteger NEEDED_AVAILABLE = TOTAL_FILE_SIZE.subtract(rootDirectory.sizeInBytes);
        BigInteger toDeleteSize = new BigInteger("30000000").subtract(NEEDED_AVAILABLE);
        BigInteger minSolution = TOTAL_FILE_SIZE;

        BigInteger sum = BigInteger.ZERO;
        while (!allFiles.isEmpty()) {
            Directory directory = allFiles.poll();
            BigInteger diff = directory.sizeInBytes.subtract(MAX_DIR_SIZE);
            if (!directory.isFile && diff.compareTo(BigInteger.ZERO) <= 0 ){
                sum = sum.add(directory.sizeInBytes);
            }


            if (directory.sizeInBytes.compareTo(toDeleteSize) >= 0) {
                //System.out.println(availableIfDeleteDir);
                if (minSolution.compareTo(directory.sizeInBytes) >= 0) {
                    minSolution = directory.sizeInBytes;
                }
            }
            for (Map.Entry<String, Directory> entry : directory.children.entrySet()) {
                allFiles.add(entry.getValue());
            }
        }

       // System.out.println(sum);

        System.out.println(minSolution);

    }

    @Override
    public void part2(List<String> input) {

    }
}
