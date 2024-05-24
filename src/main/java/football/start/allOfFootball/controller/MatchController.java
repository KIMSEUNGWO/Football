package football.start.allOfFootball.controller;

import football.login.config.auth.PrincipalDetails;
import football.common.domain.Match;
import football.common.domain.Member;
import football.common.exception.match.NotExistsMatchException;
import football.start.allOfFootball.dto.match.MatchCollection;
import football.start.allOfFootball.dto.MatchViewForm;
import football.start.allOfFootball.service.domainService.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                        @AuthenticationPrincipal PrincipalDetails user,
                        Model model) throws NotExistsMatchException {

        Match match = matchService.findByMatch(matchId, "/");

        MatchViewForm matchForm = new MatchViewForm(match); // 기본 데이터
        model.addAttribute("matchForm", matchForm);

        if (user == null) return "match";

        Member member = user.getMember();
        MatchCollection matchData = matchService.getMatchCollection(match, member);
        model.addAttribute("collection", matchData);

        // 회원이 매니저이면
        if (member.isManager()) {
            model.addAttribute("manager", true);
            // 매치에 이미 매니저가 있으면
            if (match.hasManager()) {
                model.addAttribute("managerFull", true);
            }
        }
        return "match";
    }

}
