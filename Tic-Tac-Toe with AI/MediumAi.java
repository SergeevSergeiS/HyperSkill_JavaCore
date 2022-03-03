package tictactoe;

public class MediumAi extends EasyAi{
    private String name;

    public MediumAi(Board board) {
        super(board);
        this.name = "medium";
    }
    public void move() {
        if (oneAway()) {
            return;
        } else {
            super.move();
        }
    }
    @Override
    void announceMove() {
        System.out.println("Making move level \"" + this.name + "\"");
    }

    public boolean oneAway() {
        char playingAs = super.board.toPlace();
        char opponentIs = 'X';
        if (playingAs == 'X') {
            opponentIs = 'O';
        }
        if (checkHorizontal(playingAs)) {
            return true;
        }
        if (checkVertical(playingAs)) {
            return true;
        }
        if (checkDiagonals(playingAs)) {
            return true;
        }
        if (checkHorizontal(opponentIs)) {
            return true;
        }
        if (checkVertical(opponentIs)) {
            return true;
        }
        if (checkDiagonals(opponentIs)) {
            return true;
        }
        return false;
    }
    private boolean checkHorizontal(char player) {
        for (int x = 1; x < 4; x++) {
            int xToFill = -1;
            int yToFill = -1;
            boolean fillable = false;
            int count = 0;
            for (int y = 1; y < 4; y++) {
                if (super.board.getField()[x][y] == player) {
                    count++;
                }
                if (super.board.getField()[x][y] == ' ') {
                    fillable = true;
                    xToFill = x;
                    yToFill = y;
                }
                if (count == 2 && fillable) {
                    System.out.println("Making move level \"" + this.name + "\"");
                    super.board.addMove(xToFill, yToFill);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkVertical(char player) {
        for (int y = 1; y < 4; y++) {
            int xToFill = -1;
            int yToFill = -1;
            boolean fillable = false;
            int count = 0;
            for (int x = 1; x < 4; x++) {
                if (super.board.getField()[x][y] == player) {
                    count++;
                }
                if (super.board.getField()[x][y] == ' ') {
                    fillable = true;
                    xToFill = x;
                    yToFill = y;
                }
                if (count == 2 && fillable) {
                    System.out.println("Making move level \"" + this.name + "\"");
                    super.board.addMove(xToFill, yToFill);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonals(char player) {
        int xToFill = -1;
        int yToFill = -1;
        boolean fillable = false;
        int count = 0;
        for (int i = 1; i < 4; i++) {
            if (super.board.getField()[i][i] == player) {
                count++;
            }
            if (super.board.getField()[i][i] == ' ') {
                fillable = true;
                xToFill = i;
                yToFill = i;
            }
            if (count == 2 && fillable) {
                System.out.println("Making move level \"" + this.name + "\"");
                super.board.addMove(xToFill, yToFill);
                return true;
            }
        }
        xToFill = -1;
        yToFill = -1;
        fillable = false;
        count = 0;
        for (int i = 1; i < 4; i++) {
            if (super.board.getField()[i][4 - i] == player) {
                count++;
            }
            if (super.board.getField()[i][4 - i] == ' ') {
                fillable = true;
                xToFill = i;
                yToFill = 4 - i;
            }
            if (count == 2 && fillable) {
                System.out.println("Making move level \"" + this.name + "\"");
                super.board.addMove(xToFill, yToFill);
                return true;
            }
        }


        return false;
    }


}
