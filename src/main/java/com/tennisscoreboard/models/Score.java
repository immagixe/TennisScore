package com.tennisscoreboard.models;

import org.springframework.stereotype.Component;

public class Score {

    private int setPlayer1;
    private int setPlayer2;
    private int gamePlayer1;
    private int gamePlayer2;
    private int pointsPlayer1;
    private int pointsPlayer2;

    public Score() {
        this.setPlayer1 = 0;
        this.setPlayer2 = 1;
        this.gamePlayer1 = 22;
        this.gamePlayer2 = 33;
        this.pointsPlayer1 = 44;
        this.pointsPlayer2 = 55;
    }

    public int getSetPlayer1() {
        return setPlayer1;
    }

    public void setSetPlayer1(int setPlayer1) {
        this.setPlayer1 = setPlayer1;
    }

    public int getSetPlayer2() {
        return setPlayer2;
    }

    public void setSetPlayer2(int setPlayer2) {
        this.setPlayer2 = setPlayer2;
    }

    public int getGamePlayer1() {
        return gamePlayer1;
    }

    public void setGamePlayer1(int gamePlayer1) {
        this.gamePlayer1 = gamePlayer1;
    }

    public int getGamePlayer2() {
        return gamePlayer2;
    }

    public void setGamePlayer2(int gamePlayer2) {
        this.gamePlayer2 = gamePlayer2;
    }

    public int getPointsPlayer1() {
        return pointsPlayer1;
    }

    public void setPointsPlayer1(int pointsPlayer1) {
        this.pointsPlayer1 = pointsPlayer1;
    }

    public int getPointsPlayer2() {
        return pointsPlayer2;
    }

    public void setPointsPlayer2(int pointsPlayer2) {
        this.pointsPlayer2 = pointsPlayer2;
    }
}
