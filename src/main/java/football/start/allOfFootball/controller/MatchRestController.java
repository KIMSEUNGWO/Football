package football.start.allOfFootball.controller;

import football.start.allOfFootball.customAnnotation.SessionLogin;
import football.start.allOfFootball.domain.Manager;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.dto.json.JsonDefault;
import football.start.allOfFootball.dto.matchRecordForm.RecordForm;
import football.start.allOfFootball.dto.matchRecordForm.ScoreResultForm;
import football.start.allOfFootball.enums.matchEnums.RequestTeam;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.service.domainService.MemberService;
import football.start.allOfFootball.service.domainService.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
            return new ResponseEntity<>(new JsonDefault("fail", "매치정보가 잘못되었습니다."), HttpStatus.BAD_REQUEST);
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();

        if (manager == null || !manager.getMember().equals(member)) {
            return new ResponseEntity<>(new JsonDefault("fail", "접근권한이 없습니다."), HttpStatus.BAD_REQUEST);
        }

        matchService.changeTeam(match, team);
        return new ResponseEntity<>(new JsonDefault("ok", "팀이 확정되었습니다."), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/apply/{matchId}")
    public ResponseEntity<JsonDefault> managerApply(@SessionLogin Member member, @RequestBody Long matchId) {

        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty()) {
            return new ResponseEntity<>(new JsonDefault("fail", "매치정보가 잘못되었습니다."), HttpStatus.BAD_REQUEST);
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();

        if (manager != null) {
            return new ResponseEntity<>(new JsonDefault("fail", "이미 매니저가 배정되었어요."), HttpStatus.BAD_REQUEST);
        }
        List<Orders> ordersList = match.getOrdersList();
        for (Orders orders : ordersList) {
            Long orderMemberId = orders.getMember().getMemberId();
            Long myMemberId = member.getMemberId();
            if (orderMemberId.equals(myMemberId)) {
                return new ResponseEntity<>(new JsonDefault("fail", "이미 선수로 신청되어있어요."), HttpStatus.BAD_REQUEST);
            }
        }

        boolean isAlreadyApply = memberService.isAlreadyApply(member.getOrdersList(), match);
        if (isAlreadyApply) {
            return new ResponseEntity<>(new JsonDefault("fail", "동시간대에 다른 경기신청내역이 존재해요."), HttpStatus.BAD_REQUEST);
        }

        matchService.saveManager(member, match);

        return new ResponseEntity<>(new JsonDefault("ok", "신청이 완료되었습니다."), HttpStatus.OK);
    }

    @PostMapping("/end/{matchId}")
    public ResponseEntity<JsonDefault> matchEnd(@PathVariable Long matchId,
                                        @SessionLogin Member member) {

        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty() || findMatch.get().getManager() == null) {
            return new ResponseEntity<>(new JsonDefault("fail", "매치정보가 잘못되었습니다."), HttpStatus.BAD_REQUEST);
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();

        if (member.getManager() == null || !member.equals(manager.getMember())) {
            return new ResponseEntity<>(new JsonDefault("fail", "권한이 없습니다."), HttpStatus.BAD_REQUEST);
        }

        matchService.matchEnd(match);

        return new ResponseEntity<>(new JsonDefault("ok", "종료신청이 완료되었습니다."), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/record/{matchId}")
    public ResponseEntity<JsonDefault> scoreRecord(@PathVariable Long matchId,
                                           @SessionLogin Member member,
                                           @RequestBody ScoreResultForm score) {

        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty() || findMatch.get().getManager() == null) {
            return new ResponseEntity<>(new JsonDefault("fail", "매치정보가 잘못되었습니다."), HttpStatus.BAD_REQUEST);
        }
        Match match = findMatch.get();
        Manager manager = match.getManager();
        if (member.getManager() == null || !member.equals(manager.getMember())) {
            return new ResponseEntity<>(new JsonDefault("fail", "권한이 없습니다."), HttpStatus.BAD_REQUEST);
        }

        List<List<RecordForm>> playList = score.getPlayList();

        for (List<RecordForm> play : playList) { // 한 경기씩 for 문
            if (play.size() != 2) {
                return new ResponseEntity<>(new JsonDefault("fail", "점수 데이터가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
            }
            boolean saveScore = scoreService.saveScore(match, play);
            if (!saveScore) {
                return new ResponseEntity<>(new JsonDefault("fail", "점수 기록이 실패했습니다. 관리자에게 문의해주세요."), HttpStatus.BAD_REQUEST);
            }
        }
        scoreService.applyScore(match, playList);
        matchService.matchFinal(match);

        return new ResponseEntity<>(new JsonDefault("ok", "기록을 확정했습니다."), HttpStatus.OK);
    }
}
