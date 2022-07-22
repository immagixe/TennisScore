package com.tennisscoreboard;

import com.tennisscoreboard.models.Match;
import com.tennisscoreboard.models.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Player.class)
                .addAnnotatedClass(Match.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

//            Player player = session.get(Player.class, 4);
//            System.out.println(player);
//            List<Match> matches = player.getMatchesOfPlayer1();
//            System.out.println(matches);

            Match match = session.get(Match.class, 4);

            Player player = match.getPlayer2();

            System.out.println(player);







            List<Player> playerList = session.createQuery("FROM Player WHERE name = 'Maxim'").getResultList();

            System.out.println(playerList.size());
            /*Player playerExist = (Player) session.createQuery("FROM Player WHERE name='4455'").getSingleResult();*/




            //System.out.println(playerExist);





            session.getTransaction().commit();

            System.out.println("Done!");
        } finally {
            session.close();
            sessionFactory.close();
        }

    }
}
