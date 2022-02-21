package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] input = scanner.nextLine().toCharArray();
        System.out.println("---------");
        for (int i = 0; i < 9; i += 3) {
            System.out.println(String.format("| %c %c %c |", input[i], input[i+1], input[i+2]));
        }
        System.out.println("---------");
        boolean xWins = false;
        boolean oWins = false;
        int countX = 0;
        int countO = 0;
        if (input[0] == input[1] && input[1] == input[2]||input[0] == input[4] && input[4] == input[8]||
                input[0] == input[3] && input[3] == input[6]) {
            if (input[0] == 'X') {
                xWins = true;
            } else if (input[0] == 'O') {
                oWins = true;
            }
        }
        if (input[4] == input[3] && input[3] == input[5]||input[4] == input[1] && input[1] == input[7]||
                input[4] == input[2] && input[2] == input[6]) {
            if (input[4] == 'X') {
                xWins = true;
            } else if (input[4] == 'O') {
                oWins = true;
            }
        }
        if (input[8] == input[6] && input[6] == input[7]||input[8] == input[5] && input[5] == input[2]) {
            if (input[8] == 'X') {
                xWins = true;
            } else if (input[8] == 'O') {
                oWins = true;
            }
        }
        for (char element : input) {
            if (element == 'X') {
                countX++;
            } else if (element == 'O') {
                countO++;
            }
        }
        if (xWins && oWins || Math.abs(countO - countX) > 1) {
            System.out.println("Impossible");
        } else if (xWins) {
            System.out.println("X wins");
        } else if (oWins) {
            System.out.println("O wins");
        } else {
            if (countO + countX < 9) {
                System.out.println("Game not finished");
            } else {
                System.out.println("Draw");
            }
        }
    }
}
