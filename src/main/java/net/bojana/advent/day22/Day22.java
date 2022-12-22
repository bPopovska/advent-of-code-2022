package net.bojana.advent.day22;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day22 extends MultiLineInput {

    public static void main(String[] args) throws IOException {
        Day22 day22 = new Day22();
        List<String> input = day22.getInput(22, false);
        day22.part1(input);
        day22.part2(input);
    }

    int max(List<String> input) {
        int max = -1;

        for (int i = 0; i < input.size() - 2; i++) {
            String s = input.get(i);
            if (s.length() > max) {
                max = s.length();
            }
        }
        return max;
    }

    @Override
    public void part1(List<String> input) {

        int[][] map = new int[input.size() - 2][max(input)];

        int count = 0;
        String line = input.get(count);

        int initialPositionI = -1;
        int initialPositionJ = -1;

        while (!line.isBlank()) {

            // -1 no map in location
            // 1 open space in map
            // 2 wall
            for (int i = 0; i < map[0].length; i++) {
                if (i >= line.length()) {
                    map[count][i] = -1;
                } else {
                    char c = line.charAt(i);
                    if (c == ' ') {
                        map[count][i] = -1;
                    } else {
                        if (initialPositionI == -1) {
                            initialPositionI = count;
                            initialPositionJ = i;
                        }
                        if (c == '.') {
                            map[count][i] = 1;
                        } else if (c == '#') {
                            map[count][i] = 2;
                        }
                    }
                }
            }
            count++;
            line = input.get(count);
        }


        // initial state facing right
        State state = new State(initialPositionI, initialPositionJ, 0);

        String instruction = input.get(++count);
        List<Instruction> instructions = new ArrayList<>();

        count = 0;
        int number = 0;
        boolean inNumber = false;
        while (count < instruction.length()) {
            char c = instruction.charAt(count);
            if (c == 'L' || c == 'R') {
                if (inNumber) {
                    inNumber = false;
                    instructions.add(new Instruction(number));
                    number = 0;
                }
                instructions.add(new Instruction(c));
            } else {
                number *= 10;
                number += (c - '0');
                inNumber = true;
            }
            // read movement
            count++;
        }
        if (inNumber) {
            instructions.add(new Instruction(number));
        }

       // System.out.println(state);
        int i = 0;
        for (Instruction nextInstruction : instructions) {
            //System.out.println("--" + nextInstruction);
            state = applyInstruction(map, state, nextInstruction);
            if (i % 2 == 0) System.out.println(state);
            i++;
        }

        System.out.println(state.getValue());
    }

    private State applyInstruction(int[][] map, State state, Instruction nextInstruction) {

        // turn
        if (nextInstruction.direction != null) {
                int newDirection  =
                        nextInstruction.direction.equals(Direction.LEFT) ? (state.direction == 0 ? 3 : state.direction - 1) : (state.direction + 1) % 4;
                return new State(state.positionI, state.positionJ, newDirection);
        } else {

            if (state.direction % 2 == 0) {
                // left right
                // modify j
                int count = 0;
                int j = state.positionJ;
                int lastValidJ = j;
                if (state.direction == 0) {
                    // plus
                    while (count < nextInstruction.steps) {
                        //System.out.println("--" + state.positionI + " " + j);

                        if (j >= map[0].length) {
                            j %= map[0].length;
                        }
                        int mapVal = map[state.positionI][j];

                        if (mapVal == 2) {
                            j = lastValidJ;
                            break;
                        } else if (mapVal == 1) {
                            lastValidJ = j;
                            j++;
                            count++;

                        } else {
                            j++;
                        }
                    }
                    if (j >= map[0].length) {
                        j %= map[0].length;
                    }
                    // NEW
                    while (map[state.positionI][j] == -1) {
                        j++;
                        if (j >= map[0].length) {
                            j %= map[0].length;
                        }
                    }
                    // NEW
                } else {
                    // minus
                    while (count < nextInstruction.steps) {
                        if (j < 0) {
                            j += map[0].length;
                        }

                        int mapVal = map[state.positionI][j];
                        if (mapVal == 2) {
                            j = lastValidJ;
                            break;
                        } else if (mapVal == 1) {
                            lastValidJ = j;
                            j--;
                            count++;

                        } else {
                            j--;
                        }
                    }
                    if (j < 0) {
                        j += map[0].length;
                    }
                    // NEW
                    while (map[state.positionI][j] == -1) {
                        j--;
                        if (j < 0) {
                            j += map[0].length;
                        }
                    }
                    // NEW

                }
                if (map[state.positionI][j] == 2) {
                    j = lastValidJ;
                }
                return new State(state.positionI, j, state.direction);

            } else {
                // up down
                // modify i
                int i = state.positionI;
                int lastValidI = i;
                int count = 0;
                if (state.direction == 1) {

                    // plus
                    while (count < nextInstruction.steps) {
                        if (i >= map.length) {
                            i %= map.length;
                        }
                        int mapVal = map[i][state.positionJ];

                        if (mapVal == 2) {
                            i = lastValidI;
                            break;
                        } else if (mapVal == 1) {
                            lastValidI = i;
                            i++;
                            count++;

                        } else {
                            i++;
                        }

                    }
                    if (i >= map.length) {
                        i %= map.length;
                    }
                    // NEW
                    while (map[i][state.positionJ] == -1) {
                        i++;
                        if (i >= map.length) {
                            i %= map.length;
                        }
                    }
                    // NEW

                } else {
                    // minus
                    while (count < nextInstruction.steps) {
                        if (i < 0) {
                            i += map.length;
                        }

                        int mapVal = map[i][state.positionJ];
                        if (mapVal == 2) {
                            i = lastValidI;
                            break;
                        } else if (mapVal == 1) {
                            lastValidI = i;
                            i--;
                            count++;

                        } else {
                            i--;
                        }
                    }
                    if (i < 0) {
                        i += map.length;
                    }

                    // NEW
                    while (map[i][state.positionJ] == -1) {
                        i--;
                        if (i < 0) {
                            i += map.length;
                        }
                    }
                    // NEW
                }
                if (map[i][state.positionJ] == 2) {
                    i = lastValidI;
                }
                return new State(i, state.positionJ, state.direction);

            }
        }
    }

    @Override
    public void part2(List<String> input) {

    }
}
