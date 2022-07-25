package com.tennisscoreboard.controllers;

import com.tennisscoreboard.dao.MatchScoreDAO;
import com.tennisscoreboard.models.Match;
import com.tennisscoreboard.models.Player;
import com.tennisscoreboard.services.MatchScoreCalculationService;
import com.tennisscoreboard.services.MatchesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MatchScoreController {

    private final MatchScoreDAO matchScoreDAO;
    private final MatchesService matchesService;
    private final MatchScoreCalculationService matchScoreCalculationService;

    public MatchScoreController(MatchScoreDAO matchScoreDAO,
                                MatchesService matchesService,
                                MatchScoreCalculationService matchScoreCalculationService) {
        this.matchScoreDAO = matchScoreDAO;
        this.matchesService = matchesService;
        this.matchScoreCalculationService = matchScoreCalculationService;
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
                                             MatchesService matchesService, Model model) {
        if (bindingResult1.hasErrors() || bindingResult2.hasErrors()) {
            return "new_match";
        }
        model.addAttribute("uuid", matchesService.matchInitialization(matchScoreDAO, player1, player2));
        return "redirect:/match-score";
    }

    @GetMapping("/match-score")
    public String showMatchScoreTable(@RequestParam(value = "uuid", required = false) String uuid,
                                      Model model) {
        model.addAttribute("uuid", uuid);
        model.addAttribute("currentMatch", matchesService.getCurrentMatch(uuid));

        return "match_score";
    }

    @PostMapping("/match-score")
    public String updateScoreBoard(@RequestParam(value = "uuid", required = false) String uuid,
                                   @RequestParam(value ="playerIdWinPoint", required = false) int playerIdWinPoint,
                                   Model model) {
        Match currentMatch = matchesService.getCurrentMatch(uuid);
        matchScoreCalculationService.winPoint(currentMatch, playerIdWinPoint);
        model.addAttribute("currentMatch", matchesService.getCurrentMatch(uuid));

        if (currentMatch.getScore().isMatchEnd()) {
            matchesService.addFinishedMatchToDataBase(matchScoreDAO, currentMatch, playerIdWinPoint, uuid);
        }


        System.out.println(matchesService.getSize());


        System.out.println("Name " + playerIdWinPoint);
        model.addAttribute("uuid", uuid);




        return "match_score";
    }

    @GetMapping("/matches")
    public String showMatches(@RequestParam (value = "page", required = false) int pageNumber,
                                  Model model) {

        model.addAttribute("matches", matchesService.getPageWithMatches(matchScoreDAO, pageNumber));

        model.addAttribute("countPages", matchScoreDAO.getCountPages());
        model.addAttribute("lastPageNumber", matchScoreDAO.getLastPageNumber());



        System.out.println("PRIVEt");
        return "matches";
    }
}
