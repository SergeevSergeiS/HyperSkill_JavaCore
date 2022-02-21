package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] input = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        printer(input);
        boolean correctInput = false;
        boolean xWins = false;
        boolean oWins = false;
        boolean currentTurn = true;
        int counter = 0;
        while (!correctInput && counter != 9) {
            try {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                if (x < 1 || x > 3 || y < 1 || y > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (input[(x - 1) * 3 + (y - 1)] == 'X' || input[(x - 1) * 3 + (y - 1)] == 'O') {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    correctInput = true;
                    input[(x - 1) * 3 + (y - 1)] = currentTurn ? 'X' : 'O';
                    currentTurn = !currentTurn;
                    counter++;
                }
            } catch (InputMismatchException ex) {
                System.out.println("You should enter numbers!");
            }
            printer(input);
            if (checker(input, 0, 1, 2)||checker(input, 0, 4, 8)||
                    checker(input, 0, 3, 6)) {
                if (input[0] == 'X') {
                    xWins = true;
                    break;
                } else if (input[0] == 'O') {
                    oWins = true;
                    break;
                }
            }
            if (checker(input, 3, 4, 5)||checker(input, 1, 4, 7)||
                    checker(input, 2, 4, 6)) {
                if (input[4] == 'X') {
                    xWins = true;
                    break;
                } else if (input[4] == 'O') {
                    oWins = true;
                    break;
                }
            }
            if (checker(input, 6, 7, 8)||checker(input, 2, 5, 8)) {
                if (input[8] == 'X') {
                    xWins = true;
                    break;
                } else if (input[8] == 'O') {
                    oWins = true;
                    break;
                }
            }
            correctInput = false;
        }
        if (counter == 9 && !xWins && !oWins) {
            System.out.println("Draw");
        } else {
            System.out.println(xWins ? "X wins" : "O wins");
        }
    }

    static void printer(char[] input) {
        System.out.println("---------");
        for (int i = 0; i < 9; i += 3) {
            System.out.println(String.format("| %c %c %c |", input[i], input[i + 1], input[i + 2]));
        }
        System.out.println("---------");
    }

    static boolean checker(char[] input, int pos1, int pos2, int pos3) {
        return input[pos1] == input[pos2] && input[pos2] == input[pos3];
    }
}
