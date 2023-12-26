package football.start.allOfFootball.controller;

import football.start.allOfFootball.domain.Manager;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.domain.score.Score;
import football.start.allOfFootball.domain.score.Team;
import football.start.allOfFootball.dto.matchRecordForm.RecordForm;
import football.start.allOfFootball.dto.matchRecordForm.ScoreResultForm;
import football.start.allOfFootball.enums.matchEnums.RequestTeam;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.service.domainService.MemberService;
import football.start.allOfFootball.service.domainService.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static football.start.allOfFootball.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchRestController {

    private final MatchService matchService;
    private final MemberService memberService;
    private final ScoreService scoreService;

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

    @PostMapping("/match/end/{matchIdStr}")
    public Map<String, String> matchEnd(@PathVariable String matchIdStr, @SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId) {
        Map<String, String> result = new HashMap<>();
        Long matchId = matchService.numberCheck(matchIdStr);
        Optional<Member> findMember = memberService.findByMemberId(memberId);
        if (findMember.isEmpty()) {
            result.put("result", "NotLogin");
            result.put("message", "로그인이 필요합니다.");
            return result;
        }
        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty() || findMatch.get().getManager() == null) {
            result.put("result", "fail");
            result.put("message", "매치정보가 잘못되었습니다.");
            return result;
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();
        Member member = findMember.get();
        if (member.getManager() == null || !memberId.equals(manager.getMember().getMemberId())) {
            result.put("result", "fail");
            result.put("message", "권한이 없습니다.");
            return result;
        }

        matchService.matchEnd(match);

        result.put("result", "ok");
        result.put("message", "종료신청이 완료되었습니다.");
        return result;
    }

    @Transactional
    @PostMapping("/match/record/{matchIdStr}")
    public Map<String, String> scoreRecord(@PathVariable String matchIdStr,
                                           @SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId,
                                           @RequestBody ScoreResultForm score) {

        Map<String, String> result = new HashMap<>();
        Long matchId = matchService.numberCheck(matchIdStr);
        Optional<Member> findMember = memberService.findByMemberId(memberId);
        if (findMember.isEmpty()) {
            result.put("result", "NotLogin");
            result.put("message", "로그인이 필요합니다.");
            return result;
        }
        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty() || findMatch.get().getManager() == null) {
            result.put("result", "fail");
            result.put("message", "매치정보가 잘못되었습니다.");
            return result;
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();
        Member member = findMember.get();
        if (member.getManager() == null || !memberId.equals(manager.getMember().getMemberId())) {
            result.put("result", "fail");
            result.put("message", "권한이 없습니다.");
            return result;
        }

        List<List<RecordForm>> playList = score.getPlayList();

        for (List<RecordForm> play : playList) { // 한 경기씩 for 문
            if (play.size() != 2) {
                result.put("result", "fail");
                result.put("message", "점수 데이터가 존재하지 않습니다.");
                return result;
            }
            boolean saveScore = scoreService.saveScore(match, play);
            if (!saveScore) {
                result.put("result", "fail");
                result.put("message", "점수 기록이 실패했습니다. 관리자에게 문의해주세요.");
                return result;
            }
        }
        scoreService.applyScore(match, playList);
        matchService.matchFinal(match);
        result.put("result", "ok");
        result.put("message", "기록을 확정했습니다.");
        return result;
    }
}
