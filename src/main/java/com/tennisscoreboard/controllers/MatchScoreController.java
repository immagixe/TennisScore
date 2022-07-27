package com.tennisscoreboard.controllers;

import com.tennisscoreboard.dao.MatchScoreDAO;
import com.tennisscoreboard.models.Match;
import com.tennisscoreboard.models.Player;
import com.tennisscoreboard.services.MatchDisplayService;
import com.tennisscoreboard.services.MatchScoreCalculationService;
import com.tennisscoreboard.services.MatchesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class MatchScoreController {

    private final MatchScoreDAO matchScoreDAO;
    private final MatchesService matchesService;
    private final MatchScoreCalculationService matchScoreCalculationService;
    private final MatchDisplayService matchDisplayService;

    public MatchScoreController(MatchScoreDAO matchScoreDAO,
                                MatchesService matchesService,
                                MatchScoreCalculationService matchScoreCalculationService,
                                MatchDisplayService matchDisplayService) {
        this.matchScoreDAO = matchScoreDAO;
        this.matchesService = matchesService;
        this.matchScoreCalculationService = matchScoreCalculationService;
        this.matchDisplayService = matchDisplayService;
    }

    @GetMapping("/new-match")
    public String newMatch(Model model) {
        model.addAttribute("player1", new Player());
        model.addAttribute("player2", new Player());
        return "new_match";
    }

    @PostMapping("/new-match")
    public String createPlayersAndStartMatch(@ModelAttribute("player1") @Valid Player player1,
                                             BindingResult bindingResult1,
                                             @ModelAttribute("player2") @Valid Player player2,
                                             BindingResult bindingResult2,
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
                                   @RequestParam(value = "playerIdWinPoint", required = false) int playerIdWinPoint,
                                   Model model) {
        Match currentMatch = matchesService.getCurrentMatch(uuid);

        matchScoreCalculationService.winPoint(currentMatch, playerIdWinPoint);
        model.addAttribute("uuid", uuid);
        model.addAttribute("currentMatch", matchesService.getCurrentMatch(uuid));

        if (currentMatch.getScore().isMatchEnd()) {
            matchesService.addFinishedMatchToDataBase(matchScoreDAO, currentMatch, playerIdWinPoint);
            matchesService.removeFinishedMatchFromMatchesMap(uuid);
        }

        return "match_score";
    }

    @GetMapping("/matches")
    public String showMatches(@RequestParam(value = "page", required = false) Integer pageNumber,
                              @RequestParam(value = "playername", required = false) String playerName,
                              Model model) {
        if (pageNumber == null && Objects.equals(playerName, "")) {
            return "redirect:/matches?page=1";
        }

        if (pageNumber == null) {
            pageNumber = 1;
        }

        model.addAttribute("matches",
                matchDisplayService.getPageWithMatches(matchScoreDAO, pageNumber, playerName));
        model.addAttribute("playername", playerName);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("lastPageNumber", matchDisplayService.getLastPageNumber(matchScoreDAO, playerName));

        return "matches";
    }
}
