package net.bojana.advent.day17;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tetris {
    TetrisType type;
    // 0, 0 leftmost bottom
    int[][] positions;
    int[] positionOffset;
    int width;
    int height;


    Tetris(TetrisType type) {
        this.type = type;
        this.positionOffset = new int[2];

        switch (this.type) {
            case HORIZONTAL:
                this.positions = new int[][]{
                      new int[]{0, 0},
                      new int[]{0, 1},
                      new int[]{0, 2},
                      new int[]{0, 3},
                };
                this.width = 4;
                this.height = 1;
                break;
            case VERTICAL:
                this.positions = new int[][]{
                  new int[]{0, 0},
                  new int[]{1, 0},
                  new int[]{2, 0},
                  new int[]{3, 0},
                };
                this.width = 1;
                this.height = 4;
                break;
            case L:
                this.positions = new int[][]{
                  new int[]{0, 0},
                  new int[]{0, 1},
                  new int[]{0, 2},
                  new int[]{1, 2},
                  new int[]{2, 2},
                };
                this.width = 3;
                this.height = 3;
                break;
            case CROSS:
                this.positions = new int[][]{
                  new int[]{0, 1},
                  new int[]{1, 0},
                  new int[]{1, 1},
                  new int[]{1, 2},
                  new int[]{2, 1},

                };
                this.height = 3;
                this.width = 3;
                break;
            case SQUARE:
                this.positions = new int[][]{
                    new int[]{0, 0},
                    new int[]{0, 1},
                    new int[]{1, 0},
                    new int[]{1, 1},
                };
                this.width = 2;
                this.height = 2;
                break;
        }
    }

    public void setPositionOffset(int maxHeight) {
        this.positionOffset[0] = maxHeight + 4;
        this.positionOffset[1] = 2;
    }

    public void moveDown() {
        if (positionOffset[0] > 0) {
            this.positionOffset[0]--;
        }
    }

    public void moveHorizontal(Direction d) {
        switch (d) {
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
    }

    public void revertMove(Direction d) {
        switch (d) {
            case LEFT:
                moveRight();
                break;
            case RIGHT:
                moveLeft();
                break;
        }
    }

    public boolean isOverlap(int[] heights) {
        Map<Integer, Integer> heightsMap = createHeightMap(heights);
        Map<Integer, List<Integer>> absolutePositions = createAbsolutePositionsMap();

        for (Map.Entry<Integer, List<Integer>> entry : absolutePositions.entrySet()) {
            int x = entry.getKey();
            int maxHeight = heightsMap.get(x);
            List<Integer> allPositionsForX = entry.getValue();
            allPositionsForX.sort(Comparator.comparingInt(a -> a));

            if (allPositionsForX.get(0) == maxHeight) {
                return true;
            }
        }

        return false;
    }

    public void moveLeft() {
        if (this.positionOffset[1] > 0) {
            this.positionOffset[1]--;
        }
    }

    public void moveRight() {
        if (this.positionOffset[1] + this.width < 7) {
            this.positionOffset[1]++;
        }
    }


    public boolean checkTouch(int[] heights) {
        Map<Integer, Integer> height = createHeightMap(heights);

        Map<Integer, List<Integer>> absolutePositions = createAbsolutePositionsMap();

        for (Map.Entry<Integer, List<Integer>> entry : absolutePositions.entrySet()) {
            int x = entry.getKey();
            int maxHeight = height.get(x);
            List<Integer> allPositionsForX = entry.getValue();
            allPositionsForX.sort(Comparator.comparingInt(a -> a));

            if (allPositionsForX.get(0) - 1 <= maxHeight) {
                return true;
            }
        }
        return false;
    }

    private Map<Integer, List<Integer>> createAbsolutePositionsMap() {
        Map<Integer, List<Integer>> absolutePositions = new HashMap<>();
        for (int i = 0; i < this.positions.length; i++) {
            int x = this.positions[i][0] + this.positionOffset[0];
            int y = this.positions[i][1] + this.positionOffset[1];
            absolutePositions.merge(y, new ArrayList<>(List.of(x)), (l1, l2) -> Stream.concat(l1.stream(), Stream.of(x)).collect(Collectors.toList()));
        }
        return absolutePositions;
    }

    public int[] addNewPiece(int[] heights) {
        int[] result = new int[heights.length];

        for (int i = 0; i < heights.length; i++) {
            result[i] = heights[i];
        }

        Map<Integer, Integer> heightMap = createHeightMap(heights);

        Map<Integer, List<Integer>> absolutePositions = createAbsolutePositionsMap();

        for (Map.Entry<Integer, List<Integer>> entry : absolutePositions.entrySet()) {
            int x = entry.getKey();
            int maxHeight = heightMap.get(x);
            List<Integer> allPositionsForX = entry.getValue();
            allPositionsForX.sort((a, b) -> b - a);

            result[x] = Math.max(allPositionsForX.get(0), maxHeight);
        }

        return result;
    }

    private Map<Integer, Integer> createHeightMap(int[] heights) {
        Map<Integer, Integer> heightMap = new HashMap<>();
        for (int i = 0; i < heights.length; i++) {
            heightMap.put(i, heights[i]);
        }
        return heightMap;
    }

    public void dropDown(int[] heights) {
        while (true) {
            if (this.checkTouch(heights)) {
                break;
            }
            this.moveDown();
        }
    }
}
