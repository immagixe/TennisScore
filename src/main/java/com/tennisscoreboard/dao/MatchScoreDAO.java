package com.tennisscoreboard.dao;

import com.tennisscoreboard.models.Match;
import com.tennisscoreboard.models.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MatchScoreDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public MatchScoreDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Player findByNameOrCreateIfDoesNotExist(Player player) {
        Session session = sessionFactory.getCurrentSession();
        String playerName = player.getName();
        String hql = "FROM Player WHERE name = :name";
        List<Player> playerList = session.createQuery(hql).setParameter("name", playerName).getResultList();
        int countPlayers = playerList.size();
        if (countPlayers != 0) {
            return playerList.get(0);
        } else {
            session.save(player);
            return player;
        }
    }

    @Transactional(readOnly = true)
    public Player getPlayerById(int playerId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Player.class, playerId);
    }

    @Transactional
    public void setWinnerOfMatch(Match match, Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.save(match);
    }

    @Transactional
    public void saveMatch(Match match) {
        Session session = sessionFactory.getCurrentSession();
        session.save(match);
    }

    @Transactional(readOnly = true)
    public List<Match> getAllMatches1() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Match";
        List<Match> matches = session.createQuery(hql).getResultList();
        return matches;
    }

    @Transactional(readOnly = true)
    public List<Match> getMatches(int firstResult, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Match");
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        List<Match> matches = query.list();
        return matches;
    }

    @Transactional
    public Long getCountPages() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COUNT(*) FROM Match";
        Query countQuery = session.createQuery(hql);
        Long countResults = (Long) countQuery.uniqueResult();
        return countResults;
    }

    @Transactional
    public int getLastPageNumber() {
        int pageSize = 10;
        return (int) (getCountPages() / pageSize) + 1;
    }
}

//        Session session = sessionFactory.getCurrentSession();
//        int pageSize = 10;
//        String countQ = "SELECT COUNT(*) FROM Match";
//        Query countQuery = session.createQuery(countQ);
//        Long countResults = (Long) countQuery.uniqueResult();
//        int lastPageNumber = (int) (Math.ceil(countResults / pageSize));
//
//        Query selectQuery = session.createQuery("FROM Match");
//        selectQuery.setFirstResult((lastPageNumber - 1) * pageSize);
//        selectQuery.setMaxResults(pageSize);
//        List<Match> matchList = selectQuery.list();
//
//        return matchList;
