package football.start.allOfFootball.controller;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.dto.MatchViewForm;
import football.start.allOfFootball.service.domainService.MatchService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MatchController {

    private final MatchService matchService;


    @GetMapping("/match/{matchId}")
    public String match(@PathVariable Long matchId, HttpServletResponse response, Model model) {
        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 경기입니다.", "/");
        }
        Match match = findMatch.get();
        MatchViewForm matchForm = MatchViewForm.build(match);
        model.addAttribute("matchForm", matchForm);
        return "match";
    }
}
