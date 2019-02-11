package com.example.user.reversi;

public class MatchBoardSlot {
    private Color stoneColor;
    private BoardPosition boardPosition;

    public MatchBoardSlot(Color stoneColor, BoardPosition boardPosition) {
        this.stoneColor = stoneColor;
        this.boardPosition = boardPosition;
    }

    public Color getStoneColor() {
        return stoneColor;
    }

    public void setStoneColor(Color stoneColor) {
        this.stoneColor = stoneColor;
    }

    public BoardPosition getBoardPosition() {
        return boardPosition;
    }

    public int getBoardPositionRow(){
        return boardPosition.getRow();
    }

    public int getBoardPositionColumn(){
        return boardPosition.getColumn();
    }

}