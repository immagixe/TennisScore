package com.tennisscoreboard.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name="players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="player_name")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @OneToMany (mappedBy = "player1")
    private List<Match> matchesOfPlayer1;

    @OneToMany (mappedBy = "player2")
    private List<Match> matchesOfPlayer2;

    @OneToMany (mappedBy = "winner")
    private List<Match> matchesOfWinner;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Match> getMatchesOfPlayer1() {
        return matchesOfPlayer1;
    }

    public void setMatchesOfPlayer1(List<Match> matchesOfPlayer1) {
        this.matchesOfPlayer1 = matchesOfPlayer1;
    }

    public List<Match> getMatchesOfPlayer2() {
        return matchesOfPlayer2;
    }

    public void setMatchesOfPlayer2(List<Match> matchesOfPlayer2) {
        this.matchesOfPlayer2 = matchesOfPlayer2;
    }

    public List<Match> getMatchesOfWinner() {
        return matchesOfWinner;
    }

    public void setMatchesOfWinner(List<Match> matchesOfWinner) {
        this.matchesOfWinner = matchesOfWinner;
    }
}
