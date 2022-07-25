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

    public void addFinishedMatchToDataBase(MatchScoreDAO matchScoreDAO, Match currentMatch, int playerIdWinPoint, String uuid) {
        Player winner = matchScoreDAO.getPlayerById(playerIdWinPoint);
        currentMatch.setWinner(winner);
        matchScoreDAO.saveMatch(currentMatch);
        matchesMap.remove(uuid);
    }

    public Match getCurrentMatch(String uuid) {
        return matchesMap.get(uuid);
    }

    public List<Match> getPageWithMatches(MatchScoreDAO matchScoreDAO, int pageNumber) {
        int pageSize = 10;
        int firstResult = ((pageNumber - 1) * pageSize);
        return matchScoreDAO.getMatches(firstResult, pageSize);
    }

    public int getSize() {
        return matchesMap.size();
    }
}
