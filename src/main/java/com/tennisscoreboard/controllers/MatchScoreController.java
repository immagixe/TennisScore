package com.tennisscoreboard.controllers;

import com.tennisscoreboard.dao.MatchScoreDAO;
import com.tennisscoreboard.models.Match;
import com.tennisscoreboard.models.Player;
import com.tennisscoreboard.services.OngoingMatchesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MatchScoreController {

    private final MatchScoreDAO matchScoreDAO;
    private final OngoingMatchesService ongoingMatchesService;

    public MatchScoreController(MatchScoreDAO matchScoreDAO, OngoingMatchesService ongoingMatchesService) {
        this.matchScoreDAO = matchScoreDAO;
        this.ongoingMatchesService = ongoingMatchesService;
    }

    @GetMapping("/new-match")
    public String newMatch(Model model) {
        model.addAttribute("player1", new Player());
        model.addAttribute("player2", new Player());
        return "new_match";
    }

    @PostMapping("/new-match")
    public String createPlayersAndStartMatch(@ModelAttribute("player1") @Valid Player player1, BindingResult bindingResult1,
                                             @ModelAttribute("player2") @Valid Player player2, BindingResult bindingResult2,
                                             OngoingMatchesService ongoingMatchesService,
                                             Model model) {
        if (bindingResult1.hasErrors() || bindingResult2.hasErrors()) {
            return "new_match";
        }
        model.addAttribute("uuid", ongoingMatchesService.matchInitialization(matchScoreDAO, player1, player2));
        return "redirect:/match-score";
    }

    @GetMapping("/match-score")
    public String showMatchScoreTable(@RequestParam(value = "uuid", required = false) String uuid,
                                      Model model) {
        model.addAttribute("uuid", uuid);
        Match currentMatch = ongoingMatchesService.getCurrentMatch(uuid);

        Player player1 = ongoingMatchesService.getPlayer(currentMatch, OngoingMatchesService.PlayerNumber.PLAYER1);
        Player player2 = ongoingMatchesService.getPlayer(currentMatch, OngoingMatchesService.PlayerNumber.PLAYER2);

        model.addAttribute("player1", player1);
        model.addAttribute("player2", player2);

        model.addAttribute("setPlayer1", currentMatch.getScore().getSetPlayer1());
        model.addAttribute("setPlayer2", currentMatch.getScore().getSetPlayer2());
        model.addAttribute("gamePlayer1", currentMatch.getScore().getGamePlayer1());
        model.addAttribute("gamePlayer2", currentMatch.getScore().getGamePlayer2());
        model.addAttribute("pointsPlayer1", currentMatch.getScore().getPointsPlayer1());
        model.addAttribute("pointsPlayer2", currentMatch.getScore().getPointsPlayer2());



//        System.out.println(ongoingMatchesService.getCurrentMatch(uuid).getPlayer1().getName());
//        System.out.println("uuid: " + model.getAttribute("uuid"));
        return "match_score";
    }

    @PostMapping("/match-score")
    public String updateScoreBoard(@RequestParam("uuid") String uuid,
                                   @RequestParam("playerIdWinPoint") int playerIdWinPoint,
                                   Model model) {


        System.out.println("Name " + playerIdWinPoint);
        model.addAttribute("uuid", uuid);

        System.out.println("POST ZAPROS2");


        return "redirect:/match-score";
    }
}