package com.example.user.reversi;

public class Player {
    private String playerName;
    private int score = 0;
    private boolean isAI = false;
    private Color color;

    public Player(String playerName, int score, boolean isAI, Color color) {
        this.playerName = playerName;
        this.score = score;
        this.isAI = isAI;
        this.color = color;
    }

    public Player(String playerName, boolean isAI, Color color) {
        this.playerName = playerName;
        this.score = 0;
        this.isAI = isAI;
        this.color = color;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}