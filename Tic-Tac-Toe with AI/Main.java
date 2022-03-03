package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Board board = new Board();
            System.out.println("Input command:");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            String[] command = input.split(" ");
            if (incorrectInput(command)) {
                System.out.println("Bad parameters!");
                continue;
            }
            play(command,scanner, board);
        }
    }
    private static void play(String[] command, Scanner scanner, Board board) {

        Player[]opponents = assignPlayers(command, scanner, board);
        board.printBoard();
        while (!board.isFinished()) {
            board.currentTurn(opponents[0], opponents[1]).move();
        }
    }
    private static Player[] assignPlayers(String[] command, Scanner scanner, Board board) {
        Player[] opponents = new Player[2];

        for(int i = 1; i < 3; i++) {
            if (command[i].equals("user")) {
                opponents[i - 1] = new Player(board, scanner);
            } else if (command[i].equals("easy")){
                opponents[i - 1] = new EasyAi(board);
            } else if (command[i].equals("medium")){
                opponents[i - 1] = new MediumAi(board);
            } else {
                opponents[i - 1] = new HardAi(board);
            }
        }
        return opponents;
    }

    private static boolean incorrectInput(String []input) {
        if (input.length != 3) {
            return true;
        }
        if (input[0].equals("start")
                && (input[1].equals("easy") || input[1].equals("medium") || input[1].equals("hard") || input[1].equals("user"))
                && (input[2].equals("easy") || input[2].equals("medium") || input[2].equals("hard") || input[2].equals("user"))) {
            return false;
        }
        return true;
    }
}
