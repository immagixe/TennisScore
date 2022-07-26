package com.tennisscoreboard.services;

import com.tennisscoreboard.dao.MatchScoreDAO;
import com.tennisscoreboard.models.Match;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchDisplayService {

    int pageSize = 10;

    public MatchDisplayService() {
    }

    public List<Match> getPageWithMatches(MatchScoreDAO matchScoreDAO, Integer pageNumber, String playerName) {
        int firstResult = (pageNumber - 1) * pageSize;

        if (playerName == null) {
            return matchScoreDAO.getMatches(firstResult, pageSize);
        } else {
            return matchScoreDAO.getMatchesFilterByPlayerName(firstResult, pageSize, playerName);
        }
    }

    public int getLastPageNumber(MatchScoreDAO matchScoreDAO, String playerName) {
        if (playerName == null) {
            return matchScoreDAO.getLastPageNumber(pageSize);
        } else {
            return matchScoreDAO.getLastPageNumberWithFilterByPlayerName(pageSize, playerName);
        }
    }
}
