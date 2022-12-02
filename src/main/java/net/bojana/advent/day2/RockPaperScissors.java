package net.bojana.advent.day2;

public class RockPaperScissors {
    Hand player1;
    Hand player2;

    RockPaperScissors(String line, boolean isPart2) {
        String[] parts = line.split(" ");

        assignPlayer1(parts[0]);

        if (isPart2) {
            assignPlayer2Part2(parts[1]);
        } else {
            assignPlayer2Part1(parts[1]);
        }
    }

    private void assignPlayer2Part2(String part) {

        if ("Y".equals(part)) {
            player2 = player1;
        }

        switch (player1) {
            case ROCK:
                if ("X".equals(part)) {
                   player2 = Hand.SCISSORS;
                } else if("Z".equals(part)){
                    player2 = Hand.PAPER;
                }
                break;
            case PAPER:
                if ("X".equals(part)) {
                    player2 = Hand.ROCK;
                } else if("Z".equals(part)){
                    player2 = Hand.SCISSORS;
                }
                break;
            case SCISSORS:
                if ("X".equals(part)) {
                    player2 = Hand.PAPER;
                } else if("Z".equals(part)){
                    player2 = Hand.ROCK;
                }
                break;
            default:
                break;
        }
    }

    private void assignPlayer2Part1(String part) {
        String second = part.trim();
        if ("X".equals(second)) {
            player2 = Hand.ROCK;
        } else if ("Y".equals(second)) {
            player2 = Hand.PAPER;
        } else if ("Z".equals(second)) {
            player2 = Hand.SCISSORS;
        }
    }

    private void assignPlayer1(String part) {
        String first = part.trim();
        if ("A".equals(first)) {
            player1 = Hand.ROCK;
        } else if ("B".equals(first)) {
            player1 = Hand.PAPER;
        } else if ("C".equals(first)) {
            player1 = Hand.SCISSORS;
        }
    }

    // 3 for draw, 0 for player 1 wins, 6 for player 2 wins
    private int getWinningScore() {
        if (player1 == player2) {
            return 3;
        }
        if (player1 == Hand.PAPER) {
            if (player2 == Hand.SCISSORS) {
                return 6;
            } else if (player2 == Hand.ROCK) {
                return 0;
            }
        } else if (player1 == Hand.SCISSORS) {
            if (player2 == Hand.PAPER) {
                return 0;
            } else if (player2 == Hand.ROCK) {
                return 6;
            }
        } else if (player1 == Hand.ROCK) {
            if (player2 == Hand.PAPER) {
                return 6;
            } else if (player2 == Hand.SCISSORS) {
                return 0;
            }
        }
        return 3;
    }

    private int getHandWeight() {
        switch (player2) {
            case ROCK:
                return 1;
            case PAPER:
                return 2;
            case SCISSORS:
                return 3;
            default:
                return 0;
        }
    }

    public int getScore() {
        return getWinningScore() + getHandWeight();
    }

}
