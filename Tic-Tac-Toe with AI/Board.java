package tictactoe;

public class Board {
    private char[][] field;
    private int xCount;
    private int oCount;

    public Board() {
        this.field = createField();
        this.xCount = 0;
        this.oCount = 0;
    }

    private char[][] createField() {
        char[][] field = new char[5][5];
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                if (row == 0 || row == 4) {
                    field[row][column] = '-';
                    continue;
                }
                if (column == 0 || column == 4) {
                    field[row][column] = '|';
                    continue;
                }
                field[row][column] = ' ';
            }
        }
        return field;
    }

    public void addMove(int x, int y) {

        this.field[x][y] = toPlace();
        if (toPlace() == 'X') {
            this.xCount++;
        } else {
            this.oCount++;
        }
        printBoard();
    }

    char toPlace() {
        char toPlace = 'X';
        if (this.xCount > this.oCount) {
            toPlace = 'O';
        }
        return toPlace;
    }

    public Player currentTurn(Player xPlayer, Player oPlayer) {
        if (toPlace() == 'X') {
            return xPlayer;
        } else {
            return oPlayer;
        }
    }

    public void printBoard() {
        for (int row = 0; row < 5; row++) {
            if (row > 0) {
                System.out.println();
            }
            for (int column = 0; column < 5; column++) {
                if ((row == 0 || row == 4) & column < 4) {
                    System.out.print(this.field[row][column] + "-");
                    continue;
                }
                System.out.print(this.field[row][column] + " ");
            }
        }
        System.out.println();
    }

    public boolean isFinished() {
        if (hasWon('X')) {
            System.out.println("X wins");
            return true;
        }
        if (hasWon('O')) {
            System.out.println("O wins");
            return true;
        }
        if (isFull()) {
            System.out.println("Draw");
            return true;
        }
        return false;
    }

    boolean isFull() {
        for (int row = 1; row < 4; row++) {
            for (int column = 1; column < 4; column++) {
                if (field[row][column] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    boolean hasWon(char player) {
        for (int x = 1; x < 4; x++) {
            int consecutiveH = 0;
            int consecutiveV = 0;
            int diagonalOne = 0;
            int diagonalTwo = 0;
            for (int y = 1; y < 4; y++) {
                if (this.field[x][y] == player) {
                    consecutiveH++;
                }
                if (this.field[y][x] == player) {
                    consecutiveV++;
                }
                if (this.field[y][y] == player) {
                    diagonalOne++;
                }
                if (this.field[y][4 - y] == player) {
                    diagonalTwo++;
                }

                if (consecutiveH == 3 || consecutiveV == 3 || diagonalOne == 3 || diagonalTwo == 3) {
                    return true;
                }
            }
        }
        return false;
    }

    public char[][] getField() {
        return field;
    }
}
