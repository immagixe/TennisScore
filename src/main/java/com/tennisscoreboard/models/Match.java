package com.tennisscoreboard.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="player1", referencedColumnName = "id")
    private Player player1;

    @ManyToOne
    @JoinColumn(name="player2", referencedColumnName = "id")
    private Player player2;

    @ManyToOne
    @JoinColumn(name="winner", referencedColumnName = "id")
    private Player winner;

    @Transient
    private Score score;



    public Match() {
    }

    public Match(Player player1, Player player2, Player winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
        score.initializationPlayersAndScore();
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
        score.initializationPlayersAndScore();
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", winner=" + winner +
                ", score=" + score +
                '}';
    }
}
