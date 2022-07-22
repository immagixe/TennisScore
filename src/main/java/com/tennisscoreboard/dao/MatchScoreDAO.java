package com.tennisscoreboard.dao;

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

//    @Transactional
//    public Player isPlayerAlreadyCreated(Player player) {
//        Session session = sessionFactory.getCurrentSession();
//        String playerName = player.getName();
//
//        String hql = "FROM Player WHERE name = :name";
//        List<Player> playerList = session.createQuery(hql).setParameter("name", playerName).getResultList();
//        int countPlayers = playerList.size();
//
//        if (countPlayers != 0) {
//            return playerList.get(0);
//        } else {
//            session.save(player);
//            return player;
//        }
//    }
}

