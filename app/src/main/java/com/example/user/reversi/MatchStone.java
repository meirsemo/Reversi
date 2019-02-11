package com.example.user.reversi;

public class MatchStone {
    private Color stoneColor;
    private BoardPosition boardPosition;

    public MatchStone(Color stoneColor, BoardPosition boardPosition) {
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

    public void setBoardPosition(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

}
