package football.start.allOfFootball.controller;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.*;
import football.start.allOfFootball.dto.match.MatchData;
import football.start.allOfFootball.dto.MatchViewForm;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Optional;

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

        Optional<Member> byMemberId = memberService.findByMemberId(memberId);
        model.addAttribute("participant", byMemberId.isPresent());
        if (byMemberId.isPresent()) {
            List<MatchData> data = matchService.getMatchData(memberId, match); // 매치 데이터
            model.addAttribute("matchData", data);
        }


        model.addAttribute("matchForm", matchForm);
        return "match";
    }
}
