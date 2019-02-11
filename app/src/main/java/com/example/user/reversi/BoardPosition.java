package com.example.user.reversi;

public class BoardPosition {
    private int column;
    private int row;

    public BoardPosition(int row, int column) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isSamePosition(BoardPosition bPosition){
        if((bPosition.row == this.row) && (bPosition.column == this.column))
            return true;
        else
            return false;
    }
}
