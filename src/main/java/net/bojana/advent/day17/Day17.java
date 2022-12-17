package net.bojana.advent.day17;

import net.bojana.advent.SingleLineInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 extends SingleLineInput {

    public static void main(String[] args) throws IOException {
        Day17 day17 = new Day17();
        String input = day17.getInput(17, false);

        day17.part1(input);
        day17.part2(input);
    }

    private int getMaxHeight(int[] heights) {
        int max = -1;
        for (int i = 0; i < heights.length; i++) {
            if (heights[i] > max) {
                max = heights[i];
            }
        }

        return max;
    }

    @Override
    public void part1(String input) {
        int[] heights = new int[7];
        Arrays.fill(heights, -1);

        List<Direction> directionList = input.chars().mapToObj(Direction::create).collect(Collectors.toList());

        List<Tetris> tetrises = new ArrayList<>();
        tetrises.add(new Tetris(TetrisType.HORIZONTAL));
        tetrises.add(new Tetris(TetrisType.CROSS));
        tetrises.add(new Tetris(TetrisType.L));
        tetrises.add(new Tetris(TetrisType.VERTICAL));
        tetrises.add(new Tetris(TetrisType.SQUARE));

        int indexDirection = 0;
        int indexTetris = 0;

        int tetrisesDropped = 1;
        // >>><  | <><> ><<<>><>>><<<>>><<<><<<>><>><<>>

        while (tetrisesDropped < 2022) {

            Tetris tetris = tetrises.get(indexTetris);
            //System.out.println("--------- shape " + tetris.type);
            tetrisesDropped++;
            int maxHeight = getMaxHeight(heights);
            tetris.setPositionOffset(maxHeight);

            while (true) {
                Direction direction = directionList.get(indexDirection);
                //System.out.println("index " + indexDirection + " " + direction);
                indexDirection = (indexDirection + 1) % directionList.size();

                // check if move is possible

                tetris.moveHorizontal(direction);

                if (tetris.isOverlap(heights)) {
                    tetris.revertMove(direction);
                }

                // check if we touch the ground
                if (tetris.checkTouch(heights)) {
                    // update heights
                    heights = tetris.addNewPiece(heights);
                    break;
                }

                tetris.moveDown();

            }

            indexTetris = (indexTetris + 1) % tetrises.size();

            System.out.println(tetrisesDropped + " " + (getMaxHeight(heights) + 1));
        }

    }

    @Override
    public void part2(String input) {

    }
}
