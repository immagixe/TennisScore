package com.tennisscoreboard.models;

import java.util.*;

public class Score {

    private static int playerNumber;
    private final Map<Integer, List<Integer>> playersAndMatchScore;
    private final int countSetsToWin;
    private boolean matchEnd;

    public Score() {
        playerNumber = 0;
        this.playersAndMatchScore = new HashMap<>();
        countSetsToWin = 2;
        matchEnd = false;
    }

    public void initializationPlayersAndScore() {
        List<Integer> pointsGamesSets = new ArrayList<>();
        Collections.addAll(pointsGamesSets, 0, 0, 0);
        playersAndMatchScore.put(++playerNumber, pointsGamesSets);
    }

    public void winPointsPlayer(int winPlayerNumber, int losePlayerNumber) {
        int pointsWinPlayer = playersAndMatchScore.get(winPlayerNumber).get(0);
        int pointsLosePlayer = playersAndMatchScore.get(losePlayerNumber).get(0);

        if (!matchEnd) {
            if (pointsWinPlayer < 30) {
                pointsWinPlayer += 15;
                playersAndMatchScore.get(winPlayerNumber).set(0, pointsWinPlayer);
            } else if (pointsWinPlayer == 30) {
                pointsWinPlayer += 10;
                playersAndMatchScore.get(winPlayerNumber).set(0, pointsWinPlayer);
            } else if (pointsWinPlayer >= 40) {
                pointsWinPlayer += 10;
                playersAndMatchScore.get(winPlayerNumber).set(0, pointsWinPlayer);
                if ((pointsWinPlayer - pointsLosePlayer) > 10) {
                    playersAndMatchScore.get(winPlayerNumber).set(0, 0);
                    playersAndMatchScore.get(losePlayerNumber).set(0, 0);
                    winGamePlayer(winPlayerNumber, losePlayerNumber);
                }
            }
        }
    }

    private void winGamePlayer(int winPlayerNumber, int losePlayerNumber) {
        int gamesOfWinPlayer = playersAndMatchScore.get(winPlayerNumber).get(1);
        int gamesOfLosePlayer = playersAndMatchScore.get(losePlayerNumber).get(1);

        if (gamesOfWinPlayer >= 5 && (gamesOfWinPlayer - gamesOfLosePlayer) >= 1) {
            playersAndMatchScore.get(winPlayerNumber).set(1, 0);
            playersAndMatchScore.get(losePlayerNumber).set(1, 0);
            winSetPlayer(winPlayerNumber);
        } else {
            gamesOfWinPlayer++;
            playersAndMatchScore.get(winPlayerNumber).set(1, gamesOfWinPlayer);
        }
    }

    private void winSetPlayer(int winPlayerNumber) {
        int setsOfWinPlayer = playersAndMatchScore.get(winPlayerNumber).get(2);

        if (setsOfWinPlayer == countSetsToWin-1) {
            setsOfWinPlayer++;
            playersAndMatchScore.get(winPlayerNumber).set(2, setsOfWinPlayer);
            matchEnd = true;
        } else {
            setsOfWinPlayer++;
            playersAndMatchScore.get(winPlayerNumber).set(2, setsOfWinPlayer);
        }
    }

    public int getPointsPlayer(int playerNumber) {
        return playersAndMatchScore.get(playerNumber).get(0);
    }

    public int getGamePlayer(int playerNumber) {
        return playersAndMatchScore.get(playerNumber).get(1);
    }

    public int getSetPlayer(int playerNumber) {
        return playersAndMatchScore.get(playerNumber).get(2);
    }

    public boolean isMatchEnd() {
        return matchEnd;
    }
}

