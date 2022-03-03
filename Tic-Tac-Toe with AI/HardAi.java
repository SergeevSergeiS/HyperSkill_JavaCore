package tictactoe;

public class HardAi extends Player {
    private String name;

    public HardAi(Board board) {
        super(board);
        this.name = "Hard";
    }

    public void move() {

        char max = super.board.toPlace();
        char min = 'X';
        if (max == 'X') {
            min = 'O';
        }
        System.out.println("Making move level \"" + this.name + "\"");
        findBestMove(max, min);

    }

    public void findBestMove(char max, char min) {
        int[] bestMove = {-1, -1};
        int bestVal = -11;
        int bestRow = -1;
        int bestColumn = -1;
        for (int row = 1; row < 4; row++) {
            for (int column = 1; column < 4; column++) {
                if (super.board.getField()[row][column] == ' ') {
                    super.board.getField()[row][column] = max;
                    int checkVal = minimax(max, min, false);
                    if (checkVal > bestVal) {
                        bestVal = checkVal;
                        bestRow = row;
                        bestColumn = column;
                    }
                    super.board.getField()[row][column] = ' ';
                }
            }
        }
        super.board.addMove(bestRow, bestColumn);
    }

    private int minimax(char max, char min, boolean isMax) {
        if (super.board.hasWon(max)) {
            return 10;
        }
        if (super.board.hasWon(min)) {
            return -10;
        }
        if (super.board.isFull()) {
            return 0;
        }
        if (isMax) {
            int bestVal = -100;
            for (int row = 1; row < 4; row++) {
                for (int column = 1; column < 4; column++) {
                    if (super.board.getField()[row][column] == ' ') {
                        super.board.getField()[row][column] = max;
                        bestVal = Math.max(bestVal, minimax(max, min, false));
                        super.board.getField()[row][column] = ' ';
                    }
                }
            }
            return bestVal;
        }

        if (!isMax) {
            int worstVal = 100;
            for (int row = 1; row < 4; row++) {
                for (int column = 1; column < 4; column++) {
                    if (super.board.getField()[row][column] == ' ') {
                        super.board.getField()[row][column] = min;
                        worstVal = Math.min(worstVal, minimax(max, min, true));
                        super.board.getField()[row][column] = ' ';
                    }
                }
            }
            return worstVal;
        }


        return 1000000000;
    }
}
