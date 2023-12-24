package football.start.allOfFootball.controller;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.*;
import football.start.allOfFootball.dto.match.MatchCollection;
import football.start.allOfFootball.dto.MatchViewForm;
import football.start.allOfFootball.enums.matchEnums.RequestTeam;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static football.start.allOfFootball.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MatchController {

    private final MatchService matchService;
    private final MemberService memberService;


    @GetMapping("/match/{matchId}")
    public String match(@PathVariable Long matchId,
                        @SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId,
                        HttpServletResponse response, Model model) {
        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 경기입니다.", "/");
        }
        Match match = findMatch.get();
        MatchViewForm matchForm = MatchViewForm.build(match); // 기본 데이터
        model.addAttribute("matchForm", matchForm);

        Optional<Member> byMemberId = memberService.findByMemberId(memberId);
        if (byMemberId.isEmpty()) return "match";

        MatchCollection matchData = matchService.getMatchCollection(match, memberId);
        model.addAttribute("collection", matchData);

        // 회원이 매니저이면
        if (byMemberId.get().getManager() != null) {
            model.addAttribute("manager", true);
            // 매치에 이미 매니저가 있으면
            if (match.getManager() != null) {
                model.addAttribute("managerFull", true);
            }
        }
        return "match";
    }
}
