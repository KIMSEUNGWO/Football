package football.start.allOfFootball.controller;

import football.internal.database.domain.Match;
import football.internal.database.domain.Member;
import football.start.allOfFootball.customAnnotation.SessionLogin;
import football.start.allOfFootball.dto.match.MatchCollection;
import football.start.allOfFootball.dto.MatchViewForm;
import football.start.allOfFootball.service.domainService.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MatchController {

    private final MatchService matchService;

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
