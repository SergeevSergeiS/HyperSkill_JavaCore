package tictactoe;

import java.util.Random;

public class EasyAi extends Player {
    private String name;

    public EasyAi(Board board) {
        super(board);
        this.name = "easy";

    }

    @Override
    public void move() {
        announceMove();
        Random random = new Random();
        while (true) {
            int x = random.nextInt(3) + 1;
            int y = random.nextInt(3) + 1;
            if (super.board.getField()[x][y] ==  ' ') {
                super.board.addMove(x, y);
                break;
            }
        }
    }
    void announceMove() {
        System.out.println("Making move level \"" + this.name + "\"");
    }
}
