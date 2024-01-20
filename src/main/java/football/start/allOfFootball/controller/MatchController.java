package football.start.allOfFootball.controller;

import football.start.allOfFootball.common.MatchTeamAlgorithms;
import football.start.allOfFootball.customAnnotation.SessionLogin;
import football.start.allOfFootball.domain.*;
import football.start.allOfFootball.dto.match.MatchCollection;
import football.start.allOfFootball.dto.MatchViewForm;
import football.start.allOfFootball.enums.TeamEnum;
import football.start.allOfFootball.service.OrderService;
import football.start.allOfFootball.service.domainService.MatchService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static football.start.allOfFootball.enums.matchEnums.MatchStatus.경기시작전;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MatchController {

    private final MatchService matchService;

    private final OrderService orderService;

    @GetMapping("/match/{matchId}")
    public String match(@PathVariable Long matchId,
                        @SessionLogin Member member,
                        Model model) {
        Match match = matchService.findByMatch(matchId).get();

        MatchViewForm matchForm = new MatchViewForm(match); // 기본 데이터
        model.addAttribute("matchForm", matchForm);

        MatchCollection matchData = matchService.getMatchCollection(match, member);
        model.addAttribute("collection", matchData);

        // 회원이 매니저이면
        if (member.getManager() != null) {
            model.addAttribute("manager", true);
            // 매치에 이미 매니저가 있으면
            if (match.getManager() != null) {
                model.addAttribute("managerFull", true);
            }
        }
        return "match";
    }

}
