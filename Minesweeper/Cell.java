package minesweeper;

public class Cell {
    private boolean hasMine;
    private boolean isOpened;
    private boolean isFlagged;

    public Cell(boolean hasMine, boolean isFlagged) {
        this.hasMine = hasMine;
        this.isFlagged = isFlagged;
        this.isOpened = false;
    }

    public boolean hasMine() {
        return hasMine;
    }

    public void setMine() {
        this.hasMine = true;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged() {
        isFlagged = !isFlagged;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened() {
        isOpened = true;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "hasMine=" + hasMine +
                ", isFlagged=" + isFlagged +
                '}';
    }
}
