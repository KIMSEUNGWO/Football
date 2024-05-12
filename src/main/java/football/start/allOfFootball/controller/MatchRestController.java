package football.start.allOfFootball.controller;

import football.start.allOfFootball.customAnnotation.SessionLogin;
import football.internal.database.domain.Manager;
import football.internal.database.domain.Match;
import football.internal.database.domain.Member;
import football.internal.database.domain.Orders;
import football.common.dto.JsonDefault;
import football.start.allOfFootball.dto.matchRecordForm.RecordForm;
import football.start.allOfFootball.dto.matchRecordForm.ScoreResultForm;
import football.start.allOfFootball.enums.matchEnums.RequestTeam;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.service.domainService.MemberService;
import football.start.allOfFootball.service.domainService.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/manager")
public class MatchRestController {

    private final MatchService matchService;
    private final MemberService memberService;
    private final ScoreService scoreService;

    @PostMapping("/team/{matchId}")
    public ResponseEntity<JsonDefault> teamConfirm(@SessionLogin Member member,
                                      @PathVariable Long matchId,
                                      @RequestBody RequestTeam team) {

        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty()) {
            return ResponseEntity.badRequest().body(new JsonDefault("fail", "매치정보가 잘못되었습니다."));
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();

        if (manager == null || manager.isSameMember(member)) {
            return ResponseEntity.badRequest().body(new JsonDefault("fail", "접근권한이 없습니다."));
        }

        matchService.changeTeam(match, team);
        return ResponseEntity.ok(new JsonDefault("ok", "팀이 확정되었습니다."));
    }

    @PostMapping("/apply")
    public ResponseEntity<JsonDefault> managerApply(@SessionLogin Member member, @RequestBody Long matchId) {

        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty()) {
            return ResponseEntity.badRequest().body(new JsonDefault("fail", "매치정보가 잘못되었습니다."));
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();

        if (manager != null) {
            return ResponseEntity.badRequest().body(new JsonDefault("fail", "이미 매니저가 배정되었습니다."));
        }
        List<Orders> ordersList = match.getOrdersList();
        for (Orders orders : ordersList) {
            Long orderMemberId = orders.getMember().getMemberId();
            Long myMemberId = member.getMemberId();
            if (orderMemberId.equals(myMemberId)) {
                return ResponseEntity.badRequest().body(new JsonDefault("fail", "이미 선수로 신청되어있어요."));
            }
        }

        boolean isAlreadyApply = memberService.isAlreadyApply(member.getOrdersList(), match);
        if (isAlreadyApply) {
            return ResponseEntity.badRequest().body(new JsonDefault("fail", "동시간대에 다른 경기신청내역이 존재해요."));
        }

        matchService.saveManager(member, match);

        return ResponseEntity.ok(new JsonDefault("ok", "신청이 완료되었습니다."));
    }

    @PostMapping("/end/{matchId}")
    public ResponseEntity<JsonDefault> matchEnd(@PathVariable Long matchId,
                                        @SessionLogin Member member) {

        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty() || findMatch.get().getManager() == null) {
            return ResponseEntity.badRequest().body(new JsonDefault("fail", "매치정보가 잘못되었습니다."));
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();

        if (member.getManager() == null || manager.isSameMember(member)) {
            return ResponseEntity.badRequest().body(new JsonDefault("fail", "권한이 없습니다."));
        }

        matchService.matchEnd(match);

        return ResponseEntity.ok(new JsonDefault("ok", "종료신청이 완료되었습니다."));
    }

    @Transactional
    @PostMapping("/record/{matchId}")
    public ResponseEntity<JsonDefault> scoreRecord(@PathVariable Long matchId,
                                           @SessionLogin Member member,
                                           @RequestBody ScoreResultForm score) {

        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty() || findMatch.get().getManager() == null) {
            return ResponseEntity.badRequest().body(new JsonDefault("fail", "매치정보가 잘못되었습니다."));
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();
        if (member.getManager() == null || !manager.isSameMember(member)) {
            return ResponseEntity.badRequest().body(new JsonDefault("fail", "권한이 없습니다."));
        }

        List<List<RecordForm>> playList = score.getPlayList();

        for (List<RecordForm> play : playList) { // 한 경기씩 for 문
            if (play.size() != 2) {
                return ResponseEntity.badRequest().body(new JsonDefault("fail", "점수 데이터가 존재하지 않습니다."));
            }
            boolean saveScore = scoreService.saveScore(match, play);
            if (!saveScore) {
                return ResponseEntity.badRequest().body(new JsonDefault("fail", "점수 기록이 실패했습니다. 관리자에게 문의해주세요."));
            }
        }
        scoreService.applyScore(match, playList);
        matchService.matchFinal(match);

        return ResponseEntity.ok(new JsonDefault("ok", "기록을 확정했습니다."));
    }
}
