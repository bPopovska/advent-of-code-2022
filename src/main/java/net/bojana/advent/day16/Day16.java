package net.bojana.advent.day16;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16 extends MultiLineInput {
    int countValves = 0;
    int[][][] flow;

    private boolean checkIfIndexIsSet(int mask, int index) {
        int length = Integer.toString(mask, 2).length();
        StringBuilder binary = new StringBuilder(Integer.toString(mask, 2));
        for (int i = 0; i < countValves - length; i++) {
            binary.insert(0, '0');
        }
        return binary.charAt(countValves - index - 1) == '1';
    }

    private int setIndexInMask(int mask, int index) {
        int length = Integer.toString(mask, 2).length();

        StringBuilder binary = new StringBuilder(Integer.toString(mask, 2));
        for (int i = 0; i < countValves - length; i++) {
            binary.insert(0, '0');
        }
        StringBuilder sb = new StringBuilder(binary.toString());
        sb.setCharAt(countValves - index - 1, '1');
        return Integer.parseInt(sb.toString(), 2);

    }

    public static void main(String[] args) throws IOException {
        Day16 run = new Day16();
        List<String> list = run.getInput(16, false);

        run.dpi(30, list);

        run.part1(list);
        run.part2(list);
    }

    private void dpi (int steps, List<String> input) {
        List<String> indexes = new ArrayList<>();
        Map<String, Node> graph = new HashMap<>();

        int index = 0;
        for (String line : input) {
            Node node = new Node(line, index, countValves);
            if (node.valveIndex != -1) {
                countValves++;
            }
            graph.put(node.name, node);
            indexes.add(node.name);
            index++;
        }

        int valveCombinations = (int) Math.pow(2, countValves);
        flow = new int[steps][index][valveCombinations];

        for (int i = 0; i < flow.length; i++) {
            for (int j = 0; j < flow[0].length; j++) {
                for (int k = 0; k < valveCombinations; k++) {
                    flow[i][j][k] = -1;
                }
            }
        }

        int initIndex = graph.get("AA").index;
        flow[0][initIndex][0] = 0;

        for (int i = 0; i < flow.length; i++) {
            for (int j = 0; j < flow[0].length; j++) {
                for (int k = 0; k < valveCombinations; k++) {
                    if (flow[i][j][k] != -1) {
                        Node n = graph.get(indexes.get(j));
                        List<String> connections = n.connections;
                        int valveFlow = n.valveFlow;
                        int valveIndex = n.valveIndex;

                        boolean isValveOpen = valveIndex != -1 && checkIfIndexIsSet(k, valveIndex);
                        for (String connection : connections) {
                            int connectionIndex = graph.get(connection).index;

                            if (i + 1 < steps) {
                                flow[i + 1][connectionIndex][k] = Math.max(flow[i + 1][connectionIndex][k], flow[i][j][k]);
                            }
                            if (i + 2 < steps && valveFlow != 0 && !isValveOpen) {
                                // calculate new valve index
                                int newK = setIndexInMask(k, valveIndex);
                                flow[i + 2][connectionIndex][newK] = Math.max(flow[i + 2][connectionIndex][newK], flow[i][j][k] + valveFlow * (steps - i - 1));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void part1(List<String> input) {
        final int STEPS = 30;

        int max = -1;
        int maxJ = -1;
        int maxK = -1;
        for (int j = 0; j < flow[0].length; j++) {
            for (int k = 0; k < flow[0][0].length; k++) {
                if (flow[STEPS - 1][j][k] > max) {
                    max = flow[STEPS - 1][j][k];
                    maxJ = j;
                    maxK = k;
                }
            }
        }

        System.out.println(max);
        System.out.println(maxJ);
        System.out.println(Integer.toString(maxK, 2));
    }

    @Override
    public void part2(List<String> input) {
        final int STEPS = 26;

        int max = -1;
        int maxJ = -1;
        int maxK = -1;
        for (int j1 = 0; j1 < flow[0].length; j1++) {
            for (int j2 = 0; j2 < flow[0].length; j2++) {
                for (int k = 0; k < flow[0][0].length; k++) {
                    int flow1 = flow[STEPS - 1][j1][k];
                    int opositeMask = calculateOpositeMask(k);
                    int flow2 = flow[STEPS - 1][j2][opositeMask];
                    if (flow1 + flow2 > max) {
                        max = flow1 + flow2;
                        maxJ = j1;
                        maxK = k;
                    }
                }
            }
        }


        System.out.println(max);
        System.out.println(maxJ);
        System.out.println(Integer.toString(maxK, 2));
    }

    private int calculateOpositeMask(int k) {
        int length = Integer.toString(k, 2).length();
        StringBuilder binary = new StringBuilder(Integer.toString(k, 2));
        for (int i = 0; i < countValves - length; i++) {
            binary.insert(0, '0');
        }

        StringBuilder opposite = new StringBuilder();
        for (Character c : binary.toString().toCharArray()) {
            if (c == '0') {
                opposite.append('1');
            } else {
                opposite.append('0');
            }
        }
        return Integer.parseInt(opposite.toString(), 2);
    }
}
