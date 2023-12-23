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

    @ResponseBody
    @PostMapping("/match/team/confirm")
    public Map<String, String> teamConfirm(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, @RequestBody RequestTeam team) {
        Map<String, String> result = new HashMap<>();

        Optional<Match> findMatch = matchService.findByMatch(team.getMatchId());
        if (findMatch.isEmpty()) {
            result.put("result", "fail");
            result.put("message", "매치정보가 잘못되었습니다.");
            return result;
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();

        if (manager == null || !manager.getMember().getMemberId().equals(memberId)) {
            result.put("result", "fail");
            result.put("message", "접근권한이 없습니다.");
            return result;
        }

        matchService.changeTeam(match, team);
        result.put("result", "ok");
        result.put("message", "팀이 확정되었습니다.");
        return result;
    }


    @ResponseBody
    @PostMapping("/manager/apply")
    public Map<String, String> managerApply(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, @RequestBody String matchIdStr) {
        Map<String, String> result = new HashMap<>();
        Long matchId = matchService.numberCheck(matchIdStr);
        Optional<Member> findMember = memberService.findByMemberId(memberId);
        if (findMember.isEmpty()) {
            result.put("result", "NotLogin");
            result.put("message", "로그인이 필요합니다.");
            return result;
        }
        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty()) {
            result.put("result", "fail");
            result.put("message", "매치정보가 잘못되었습니다.");
            return result;
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();
        Member member = findMember.get();
        if (manager != null) {
            result.put("result", "fail");
            result.put("message", "이미 매니저가 배정되었어요.");
            return result;
        }
        List<Orders> ordersList = match.getOrdersList();
        for (Orders orders : ordersList) {
            Long orderMemberId = orders.getMember().getMemberId();
            Long myMemberId = member.getMemberId();
            if (orderMemberId.equals(myMemberId)) {
                result.put("result", "fail");
                result.put("message", "이미 선수로 신청되어있어요.");
                return result;
            }
        }

        boolean isAlreadyApply = memberService.isAlreadyApply(member.getOrdersList(), match);
        if (isAlreadyApply) {
            result.put("result", "fail");
            result.put("message", "동시간대에 다른 경기신청내역이 존재해요.");
            return result;
        }

        matchService.saveManager(member, match);

        result.put("result", "ok");
        result.put("message", "신청이 완료되었습니다.");
        return result;
    }
}
