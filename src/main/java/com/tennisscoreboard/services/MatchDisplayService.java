package com.tennisscoreboard.services;

import com.tennisscoreboard.dao.MatchScoreDAO;
import com.tennisscoreboard.models.Match;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MatchDisplayService {

    int pageSize = 10;

    public MatchDisplayService() {
    }

    public List<Match> getPageWithMatches(MatchScoreDAO matchScoreDAO, int pageNumber) {
        int firstResult = ((pageNumber - 1) * pageSize);
        return matchScoreDAO.getMatches(firstResult, pageSize);
    }

    public int getLastPageNumber (MatchScoreDAO matchScoreDAO) {
        return matchScoreDAO.getLastPageNumber(pageSize);
    }
}
