package net.bojana.advent.day14;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.List;

public class Day14 extends MultiLineInput {

    public static void main(String[] args) throws IOException {
        Day14 run = new Day14();
        List<String> input = run.getInput(14, false);

        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(List<String> input) {
        int[][] map = new int[550][550];

        int max = -1;

        for (String line : input) {
            String[] segments = line.split(" -> ");

            for (int index = 1; index < segments.length; index++) {
                String s1 = segments[index - 1];
                String s2 = segments[index];

                int s11 = Integer.parseInt(s1.split(",")[0]);
                int s12 = Integer.parseInt(s1.split(",")[1]);

                int s21 = Integer.parseInt(s2.split(",")[0]);
                int s22 = Integer.parseInt(s2.split(",")[1]);

                if (s12 > max) {
                    max = s12;
                }

                if (s22 > max) {
                    max = s22;
                }

                if (s11 == s21) {
                    for (int k = Math.min(s12, s22); k <= Math.max(s12, s22); k++) {
                        map[k][s11] = 1;
                    }
                } else if (s12 == s22) {
                    for (int k = Math.min(s11, s21); k <= Math.max(s11, s21); k++) {
                        map[s12][k] = 1;
                    }
                }
            }
        }

        boolean fallenOffTheEdge = false;
        int count = 0;
        while (!fallenOffTheEdge) {
            boolean hasChange = true;
            int[] position = new int[]{0, 500};
            int i = position[0];
            int j = position[1];
            while (hasChange) {
                hasChange = false;
                 while (map[i][j] == 0) {
                    i++;
                    if (i > max) {
                        fallenOffTheEdge = true;
                        break;
                    }
                    hasChange = true;
                }
                if (map[i][j -1] == 0) {
                    i++;
                    j--;
                    hasChange = true;
                    if (i > max) {
                        fallenOffTheEdge = true;
                        break;
                    }
                } else if (map[i][j + 1] == 0) {
                    i++;
                    j++;
                    hasChange = true;
                    if (i > max) {
                        fallenOffTheEdge = true;
                        break;
                    }
                }

            }
            if (fallenOffTheEdge) {
                break;
            }
            map[i-1][j] = 2;
            System.out.println("--" + (i-1) + " " + j);
            count++;
        }
        System.out.println(count);

        for (int i = 0; i <= max; i++) {
            for (int j = 494; j < 550; j++) {
                if (map[i][j] == 1) {
                    System.out.print('#');
                } else if (map[i][j] == 2){
                    System.out.print('o');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }

    }

    @Override
    public void part2(List<String> input) {
        int[][] map = new int[2000][2000];

        int max = -1;

        for (String line : input) {
            String[] segments = line.split(" -> ");

            for (int index = 1; index < segments.length; index++) {
                String s1 = segments[index - 1];
                String s2 = segments[index];

                int s11 = Integer.parseInt(s1.split(",")[0]);
                int s12 = Integer.parseInt(s1.split(",")[1]);

                int s21 = Integer.parseInt(s2.split(",")[0]);
                int s22 = Integer.parseInt(s2.split(",")[1]);

                if (s12 > max) {
                    max = s12;
                }

                if (s22 > max) {
                    max = s22;
                }

                if (s11 == s21) {
                    for (int k = Math.min(s12, s22); k <= Math.max(s12, s22); k++) {
                        map[k][s11] = 1;
                    }
                } else if (s12 == s22) {
                    for (int k = Math.min(s11, s21); k <= Math.max(s11, s21); k++) {
                        map[s12][k] = 1;
                    }
                }
            }
        }

        for (int j = 0; j < 2000; j++) {
            map[max + 2][j] = 1;
        }


        boolean fallenOffTheEdge = false;
        int count = 0;
        while (true) {
            boolean hasChange = true;
            int[] position = new int[]{0, 500};
            int i = position[0];
            int j = position[1];
            while (hasChange) {
                hasChange = false;
                while (map[i][j] == 0) {
                    i++;
                    hasChange = true;
                }
                if (i == 0 && j == 500) {
                    break;
                }
                if (map[i][j -1] == 0) {
                    i++;
                    j--;
                    hasChange = true;
                } else if (map[i][j + 1] == 0) {
                    i++;
                    j++;
                    hasChange = true;
                }
            }

            if (i == 0 && j == 500) {
                break;
            }
            map[i-1][j] = 2;
            System.out.println("--" + (i-1) + " " + j + " " + count);
            count++;
        }
        System.out.println(count);

        for (int i = 0; i <= max + 2; i++) {
            for (int j = 300; j < 800; j++) {
                if (map[i][j] == 1) {
                    System.out.print('#');
                } else if (map[i][j] == 2){
                    System.out.print('.');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }

    }
}
