package com.example.user.reversi;

public class DirectionToSlot {
    private int xDirection;
    private int yDirection;
    private boolean isWantedSlotInDirection;
    private BoardPosition boardPositionOfWantedSlot;

    public DirectionToSlot(int xDirection, int yDirection, boolean isWantedSlotInDirection) {
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        this.isWantedSlotInDirection = isWantedSlotInDirection;
    }

    public DirectionToSlot(int xDirection, int yDirection) {
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        this.isWantedSlotInDirection = false;
    }

    public int getxDirection() {
        return xDirection;
    }

    public void setxDirection(int xDirection) {
        this.xDirection = xDirection;
    }

    public int getyDirection() {
        return yDirection;
    }

    public void setyDirection(int yDirection) {
        this.yDirection = yDirection;
    }

    public boolean isWantedSlotInDirection() {
        return isWantedSlotInDirection;
    }

    public void setWantedSlotInDirection(boolean wantedSlotInDirection) {
        isWantedSlotInDirection = wantedSlotInDirection;
    }

    public BoardPosition getBoardPositionOfWantedSlot() {
        return boardPositionOfWantedSlot;
    }

    public void setBoardPositionOfWantedSlot(BoardPosition boardPositionOfWantedSlot) {
        this.boardPositionOfWantedSlot = boardPositionOfWantedSlot;
    }

    public int getRowOfWantedSlot(){
        return boardPositionOfWantedSlot.getRow();
    }

    public int getColumnOfWantedSlot(){
        return boardPositionOfWantedSlot.getColumn();
    }
}
