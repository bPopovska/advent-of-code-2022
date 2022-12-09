package net.bojana.advent.day8;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8 extends MultiLineInput {

    public static void main(String[] args) throws IOException {
        Day8 run = new Day8();

        List<String> input = run.getInput(8, true);

        run.part1(input);
        run.part2(input);
    }

    @Override
    public void part1(List<String> input) {
        int N = input.size();
        int M = input.get(0).length();
        int[][] treePatch = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                treePatch[i][j] = input.get(i).charAt(j) - '0';
            }
        }

        Set<String> seen = new HashSet<>();

        for (int i = 0; i < N; i++) {
            int[] max = new int[M];
            max[0] = treePatch[i][0];
            seen.add(i + " " + 0);
            for (int j = 1; j < M; j++) {
                if (treePatch[i][j] > max[j-1]) {
                    max[j] = treePatch[i][j];
                    seen.add(i + " " + j);
                } else {
                    max[j] = max[j-1];
                }
            }

            max[M - 1] = treePatch[i][M-1];
            seen.add(i + " " + (M-1));

            for (int j = M - 2; j >= 0; j--) {
                if (treePatch[i][j] > max[j+1]) {
                    max[j] = treePatch[i][j];
                    seen.add(i + " " + j);
                } else {
                    max[j] = max[j+1];
                }
            }
        }

        for (int j = 0; j < M; j++) {
            int[] max = new int[N];

            max[0] = treePatch[0][j];
            seen.add(0 + " " + j);
            for (int i = 1; i < N; i++) {
                if (treePatch[i][j] > max[i-1]) {
                    max[i] = treePatch[i][j];
                    seen.add(i + " " + j);
                } else {
                    max[i] = max[i-1];
                }
            }

            max[N - 1] = treePatch[M-1][j];
            seen.add((M-1) + " " + j);
            for (int i = N - 2; i >= 0; i--) {
                if (treePatch[i][j] > max[i+1]) {
                    max[i] = treePatch[i][j];
                    seen.add(i + " " + j);
                } else {
                    max[i] = max[i+1];
                }
            }
        }
        System.out.println(seen.size());
    }

    @Override
    public void part2(List<String> input) {
        int N = input.size();
        int M = input.get(0).length();
        int[][] treePatch = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                treePatch[i][j] = input.get(i).charAt(j) - '0';
            }
        }

        int maxScore = 0;

        for (int i = 1; i < N - 1; i++) {
            for (int j = 1; j < M - 1; j++) {
                int score = 1;

                int count = 0;
                for (int i1 = i - 1; i1 >= 0; i1--) {
                    count++;
                    if (treePatch[i1][j] >= treePatch[i][j]) {
                        break;
                    }
                }
                score *= count;

                count = 0;
                for (int i1 = i + 1; i1 < N; i1++) {
                    count++;
                    if (treePatch[i1][j] >= treePatch[i][j]) {
                        break;
                    }
                }
                score *= count;

                count = 0;
                for (int j1 = j - 1; j1 >= 0; j1--) {
                    count++;
                    if (treePatch[i][j1] >= treePatch[i][j]) {
                        break;
                    }
                }
                score *= count;

                count = 0;
                for (int j1 = j + 1; j1 < M; j1++) {
                    count++;
                    if (treePatch[i][j1] >= treePatch[i][j]) {
                        break;
                    }
                }
                score *= count;

                if (score > maxScore) {
                    maxScore = score;
                }
            }
        }
        System.out.println(maxScore);
    }
}
