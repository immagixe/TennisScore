package com.tennisscoreboard.services;

import com.tennisscoreboard.dao.MatchScoreDAO;
import com.tennisscoreboard.models.Match;
import com.tennisscoreboard.models.Player;
import com.tennisscoreboard.models.Score;
import org.springframework.stereotype.Component;


import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OngoingMatchesService {

    private static final Map<String, Match> matchesMap = new ConcurrentHashMap<>();

    public OngoingMatchesService() {
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
}
