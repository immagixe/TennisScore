package com.tennisscoreboard.dao;

import com.tennisscoreboard.models.Match;
import com.tennisscoreboard.models.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
}