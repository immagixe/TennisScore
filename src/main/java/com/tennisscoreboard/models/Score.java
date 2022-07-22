package com.tennisscoreboard.models;

public class Score {

    private int setPlayer1;
    private int setPlayer2;
    private int gamePlayer1;
    private int gamePlayer2;
    private int pointsPlayer1;
    private int pointsPlayer2;
    private boolean matchEnd;

    public Score() {
        this.setPlayer1 = 0;
        this.setPlayer2 = 0;
        this.gamePlayer1 = 0;
        this.gamePlayer2 = 0;
        this.pointsPlayer1 = 0;
        this.pointsPlayer2 = 0;
        matchEnd = false;
    }

    // Player 1 Setters

    public void winPointsPlayer1() {
        if (!matchEnd) {
            if (pointsPlayer1 < 30) {
                pointsPlayer1 += 15;
            } else if (pointsPlayer1 == 30) {
                pointsPlayer1 += 10;
            } else if (pointsPlayer1 >= 40) {
                pointsPlayer1 += 10;
                checkWinGamePlayer1(pointsPlayer1, pointsPlayer2);
            }
        }
    }

    private void checkWinGamePlayer1(int pointsCurrentPlayer, int pointsAnotherPlayer) {
        if ((pointsCurrentPlayer - pointsAnotherPlayer) > 10) {
            pointsPlayer1 = 0;
            pointsPlayer2 = 0;
            winGamePlayer1(gamePlayer1, gamePlayer2);
        }
    }

    private void winGamePlayer1(int gameCurrentPlayer, int gameAnotherPlayer) {
        if (gameCurrentPlayer >= 5 && (gameCurrentPlayer - gameAnotherPlayer) >= 1) {
            gamePlayer1 = 0;
            gamePlayer2 = 0;
            winSetPlayer1(setPlayer1);
        } else {
            gamePlayer1++;
        }
    }

    private void winSetPlayer1(int gameCurrentPlayer) {
        if (gameCurrentPlayer == 1) {
            setPlayer1++;
            matchEnd = true;
        } else {
            setPlayer1++;
        }
    }

    // Player 2 Setters

    public void winPointsPlayer2() {
        if (!matchEnd) {
            if (pointsPlayer2 < 30) {
                pointsPlayer2 += 15;
            } else if (pointsPlayer2 == 30) {
                pointsPlayer2 += 10;
            } else if (pointsPlayer2 >= 40) {
                pointsPlayer2 += 10;
                checkWinGamePlayer2(pointsPlayer2, pointsPlayer1);
            }
        }
    }

    private void checkWinGamePlayer2(int pointsCurrentPlayer, int pointsAnotherPlayer) {
        if ((pointsCurrentPlayer - pointsAnotherPlayer) > 10) {
            pointsPlayer1 = 0;
            pointsPlayer2 = 0;
            winGamePlayer2(gamePlayer2, gamePlayer1);
        }
    }

    private void winGamePlayer2(int gameCurrentPlayer, int gameAnotherPlayer) {
        if (gameCurrentPlayer >= 5 && (gameCurrentPlayer - gameAnotherPlayer) >= 1) {
            gamePlayer1 = 0;
            gamePlayer2 = 0;
            winSetPlayer2(setPlayer2);
        } else {
            gamePlayer2++;
        }
    }

    private void winSetPlayer2(int gameCurrentPlayer) {
        if (gameCurrentPlayer == 1) {
            setPlayer2++;
            matchEnd = true;
        } else {
            setPlayer2++;
        }
    }

    // Getters

    public int getPointsPlayer1() {
        return pointsPlayer1;
    }

    public int getGamePlayer1() {
        return gamePlayer1;
    }

    public int getSetPlayer1() {
        return setPlayer1;
    }

    public int getPointsPlayer2() {
        return pointsPlayer2;
    }

    public int getGamePlayer2() {
        return gamePlayer2;
    }

    public int getSetPlayer2() {
        return setPlayer2;
    }

    public boolean isMatchEnd() {
        return matchEnd;
    }
}
