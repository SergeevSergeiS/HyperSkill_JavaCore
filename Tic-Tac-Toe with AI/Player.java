package tictactoe;

import java.util.Scanner;

public class Player {
    Board board;
    private Scanner scanner;

    public Player(Board board) {
        this.board = board;
    }

    public Player(Board board, Scanner scanner){
        this.board = board;
        this.scanner = scanner;
    }

    public void move() {
        while (true) {
            System.out.println("Enter the coordinates: ");
            String[] parts = scanner.nextLine().split(" ");
            try {
                int x = Integer.valueOf(parts[0]);
                int y = Integer.valueOf(parts[1]);
                if (x < 1 || x > 3 || y < 1 || y > 3) {
                    System.out.println("Coordinates should be from 1 to 3! \n");
                    continue;
                }
                if (this.board.getField()[x][y] != ' ') {
                    System.out.println("This cell is occupied! Choose another one! \n");
                    continue;
                }
                this.board.addMove(x, y);
                break;
            } catch (Exception e) {
                System.out.println("You should enter numbers! Like this: \nX Y\n");
            }
        }
    }

}
