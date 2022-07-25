package com.tennisscoreboard.services;

import com.tennisscoreboard.dao.MatchScoreDAO;
import com.tennisscoreboard.models.Match;
import com.tennisscoreboard.models.Player;
import com.tennisscoreboard.models.Score;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MatchesService {

    private static final Map<String, Match> matchesMap = new ConcurrentHashMap<>();

    public MatchesService() {
    }

    public String matchInitialization(MatchScoreDAO matchScoreDAO, Player player1, Player player2) {
        Match match = new Match();
        Score score = new Score();
        match.setScore(score);
        match.setPlayer1(matchScoreDAO.findByNameOrCreateIfDoesNotExist(player1));
        match.setPlayer2(matchScoreDAO.findByNameOrCreateIfDoesNotExist(player2));
        String uuid = UUID.randomUUID().toString();
        matchesMap.put(uuid, match);
        return uuid;
    }

    public Match getCurrentMatch(String uuid) {
        return matchesMap.get(uuid);
    }

    public void addFinishedMatchToDataBase(MatchScoreDAO matchScoreDAO, Match currentMatch, int playerIdWinPoint) {
        Player winner = matchScoreDAO.getPlayerById(playerIdWinPoint);
        currentMatch.setWinner(winner);
        matchScoreDAO.saveMatch(currentMatch);
    }

    public void removeFinishedMatchFromMatchesMap(String uuid) {
        matchesMap.remove(uuid);
    }
}
