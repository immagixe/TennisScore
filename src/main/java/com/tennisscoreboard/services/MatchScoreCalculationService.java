package com.tennisscoreboard.services;

import com.tennisscoreboard.models.Match;
import org.springframework.stereotype.Component;

@Component
public class MatchScoreCalculationService {

    public MatchScoreCalculationService() {
    }

    public void winPoint(Match currentMatch, int playerIdWinPoint) {

        if (playerIdWinPoint == currentMatch.getPlayer1().getId()) {
            currentMatch.getScore().winPointsPlayer1();
        } else {
            currentMatch.getScore().winPointsPlayer2();
        }
    }
}
