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
    public void saveMatch(Match match) {
        Session session = sessionFactory.getCurrentSession();
        session.save(match);
    }

    @Transactional(readOnly = true)
    public List<Match> getMatches(int firstResult, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Match ORDER BY id DESC";
        Query query = session.createQuery(hql);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        List<Match> matches = query.list();
        return matches;
    }
    @Transactional(readOnly = true)
    public List<Match> getMatchesFilterByPlayerName(int firstResult, int pageSize, String filterName) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Match WHERE player1.name= :name or player2.name= :name";
        Query query = session.createQuery(hql).setParameter("name", filterName);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        List<Match> matches = query.list();
        return matches;
    }

    @Transactional
    public int getLastPageNumber(int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COUNT(*) FROM Match";
        Query countQuery = session.createQuery(hql);
        Long countMatchesInDB = (Long) countQuery.uniqueResult();
        return (int) ((countMatchesInDB / pageSize) + 1);
    }

    @Transactional
    public int getLastPageNumberWithFilterByPlayerName(int pageSize, String filterName) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COUNT(*) FROM Match WHERE player1.name= :name or player2.name= :name";
        Query countQuery = session.createQuery(hql).setParameter("name", filterName);
        Long countMatchesInDB = (Long) countQuery.uniqueResult();
        return (int) ((countMatchesInDB / pageSize) + 1);
    }
}
