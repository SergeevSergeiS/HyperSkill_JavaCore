package minesweeper;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MinesweeperBoard {
    private Scanner scanner = new Scanner(System.in);
    private int numberOfMines = 0;
    private Cell[][] field = new Cell[9][9];

    public MinesweeperBoard() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = new Cell(false, false);
            }
        }

        System.out.print("How many mines do you want on the field? ");
        numberOfMines = scanner.nextInt();
        int setUpMines = 0;
        while (setUpMines < numberOfMines) {
            int x = ThreadLocalRandom.current().nextInt(0, field.length);
            int y = ThreadLocalRandom.current().nextInt(0, field[0].length);
            if (!(field[x][y].hasMine())) {
                field[x][y].setMine();
                setUpMines++;
            }
        }
    }


    private int getMinesAroundCell(int row, int col) {
        int count = 0;

        for(int i = row - 1; i <= row + 1; i++) {
            for (int j= col - 1; j <= col + 1; j++) {
                if(i >= 0 && i < field.length && j >= 0 && j < field[0].length) {
                    if(field[i][j].hasMine()) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private char[][] getPrintedBoard() {
        char[][] result = new char[field.length][field[0].length];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].hasMine()) {
                    result[i][j] = 'X';
                } else {
                    int count = getMinesAroundCell(i, j);
                    if (count == 0) {
                        result[i][j] = '.';
                    } else {
                        result[i][j] = Character.forDigit(count, 10);
                    }
                }
            }
        }

        return result;
    }

    public void printBoard() {
        System.out.print(" │");
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1);
        }
        System.out.print("│\n—│");
        for (int i = 0; i < field.length; i++) {
            System.out.print("—");
        }
        System.out.println("│");

        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1);
            System.out.print("│");
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].hasMine()) {
                    System.out.print("X");
                } else if (field[i][j].isOpened()) {
                    int count = getMinesAroundCell(i, j);
                    if (count == 0) {
                        System.out.print("/");
                    } else {
                        System.out.print(count);
                    }
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("│");
        }

        System.out.print("—│");
        for (int i = 0; i < field.length; i++) {
            System.out.print("—");
        }
        System.out.print("|\n\n");
    }

    public void printBoardWithNumbers() {
        System.out.print(" │");
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1);
        }
        System.out.print("│\n—│");
        for (int i = 0; i < field.length; i++) {
            System.out.print("—");
        }
        System.out.println("│");

        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1);
            System.out.print("│");
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].isFlagged()) {
                    System.out.print("*");
                } else if (field[i][j].hasMine()) {
                    System.out.print(".");
                } else if (field[i][j].isOpened()) {
                    int count = getMinesAroundCell(i, j);
                    if (count == 0) {
                        System.out.print("/");
                    } else {
                        System.out.print(count);
                    }
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("│");
        }

        System.out.print("—│");
        for (int i = 0; i < field.length; i++) {
            System.out.print("—");
        }
        System.out.print("|\n\n");
    }

    public boolean checkVictory() {
        boolean isFirst = true;
        boolean isSecond = true;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].hasMine() && !field[i][j].isFlagged()) {
                    isFirst = false;
                } else if (!field[i][j].hasMine() && field[i][j].isFlagged()) {
                    isFirst = false;
                }
            }
        }

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (!field[i][j].isOpened() && !field[i][j].hasMine()) {
                    isSecond = false;
                }
            }
        }

        return isFirst | isSecond;
    }

    private void openCell(int row, int col) {
        if (getMinesAroundCell(row, col) == 0) {
            field[row][col].setOpened();
            for(int i = row - 1; i <= row + 1; i++) {
                for (int j= col - 1; j <= col + 1; j++) {
                    if(i >= 0 && i < field.length && j >= 0 && j < field[0].length) {
                        if (!field[i][j].isOpened()) {
                            if (field[row][col].isFlagged()) {
                                field[row][col].setFlagged();
                            }

                            if (field[i][j].isFlagged()) {
                                field[i][j].setFlagged();
                            }

                            openCell(i, j);
                        }
                    }
                }
            }
        } else {
            field[row][col].setOpened();
        }
    }

    public void playGame() {
        printBoardWithNumbers();
        int row;
        int col;
        String choice;
        while (true) {
            System.out.print("Set/delete mines marks (x and y coordinates): ");
            col = scanner.nextInt();
            row = scanner.nextInt();
            choice = scanner.next();
            char[][] board = getPrintedBoard();
            if (row > field.length || col > field[0].length || col < 1 || row < 1) {
                System.out.println("Invalid coordinates!");
                continue;
            } else if (board[row - 1][col - 1] != 'X' && board[row - 1][col - 1] != '.' ) {
                if (!field[row - 1][col - 1].isOpened()) {
                    break;
                } else {
                    System.out.println("There is a number here!");
                    continue;
                }
            }

            if (!choice.equals("mine") && !choice.equals("free")) {
                System.out.println("Invalid option!");
            } else {
                break;
            }
        }

        if (choice.equals("mine")) {
            field[row - 1][col - 1].setFlagged();
        } else {
            if (field[row - 1][col - 1].hasMine()) {
                printBoard();
                System.out.println("You stepped on a mine and failed!");
                return;
            } else {
                openCell(row - 1, col - 1);
            }
        }

        if (checkVictory()) {
            printBoardWithNumbers();
            System.out.println("Congratulations! You found all mines!");
        } else {
            playGame();
        }
    }
}
