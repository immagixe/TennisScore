package com.tennisscoreboard.services;

import com.tennisscoreboard.models.Match;
import org.springframework.stereotype.Component;

@Component
public class MatchScoreCalculationService {

    public MatchScoreCalculationService() {
    }

    public void winPoint(Match currentMatch, int playerIdWinPoint) {
        if (playerIdWinPoint == currentMatch.getPlayer1().getId()) {
            currentMatch.getScore().winPointsPlayer(1,2);
        } else {
            currentMatch.getScore().winPointsPlayer(2,1);
        }
    }
}
