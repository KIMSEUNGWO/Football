package football.start.allOfFootball.controller;

import football.login.config.auth.PrincipalDetails;
import football.login.config.auth.UserRefreshProvider;
import football.common.domain.Manager;
import football.common.domain.Match;
import football.common.domain.Member;
import football.common.domain.Orders;
import football.common.dto.json.JsonDefault;
import football.common.exception.match.NotExistsMatchException;
import football.start.allOfFootball.dto.matchRecordForm.RecordForm;
import football.start.allOfFootball.dto.matchRecordForm.ScoreResultForm;
import football.start.allOfFootball.enums.matchEnums.RequestTeam;
import football.start.allOfFootball.service.domainService.MatchService;
import football.common.service.MemberService;
import football.start.allOfFootball.service.domainService.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static football.common.consts.Constant.FAIL;
import static football.common.consts.Constant.OK;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/manager")
public class MatchRestController {

    private final MatchService matchService;
    private final MemberService memberService;
    private final ScoreService scoreService;
    private final UserRefreshProvider provider;

    @PostMapping("/team/{matchId}")
    public ResponseEntity<JsonDefault> teamConfirm(@AuthenticationPrincipal PrincipalDetails user,
                                                   @PathVariable Long matchId,
                                                   @RequestBody RequestTeam team) throws NotExistsMatchException {
        Member member = user.getMember();
        Match match = matchService.findByMatchOrRedirect(matchId, null);
        Manager manager = match.getManager();

        if (manager == null || manager.isSameMember(member)) {
            return ResponseEntity.badRequest().body(new JsonDefault(FAIL, "접근권한이 없습니다."));
        }

        matchService.changeTeam(match, team);
        return ResponseEntity.ok(new JsonDefault(OK, "팀이 확정되었습니다."));
    }

    @PostMapping("/apply")
    public ResponseEntity<JsonDefault> managerApply(@AuthenticationPrincipal PrincipalDetails user, @RequestBody Long matchId) throws NotExistsMatchException {
        Member member = user.getMember();
        Match match = matchService.findByMatchOrRedirect(matchId, null);

        if (match.hasManager()) {
            return ResponseEntity.badRequest().body(new JsonDefault(FAIL, "이미 매니저가 배정되었습니다."));
        }
        List<Orders> ordersList = match.getOrdersList();
        for (Orders orders : ordersList) {
            Long orderMemberId = orders.getMember().getMemberId();
            Long myMemberId = member.getMemberId();
            if (orderMemberId.equals(myMemberId)) {
                return ResponseEntity.badRequest().body(new JsonDefault(FAIL, "이미 선수로 신청되어있어요."));
            }
        }

        boolean isAlreadyApply = memberService.isAlreadyApply(member.getOrdersList(), match);
        if (isAlreadyApply) {
            return ResponseEntity.badRequest().body(new JsonDefault(FAIL, "동시간대에 다른 경기신청내역이 존재해요."));
        }

        matchService.saveManager(member, match);
        provider.refresh(user);
        return ResponseEntity.ok(new JsonDefault(OK, "신청이 완료되었습니다."));
    }

    @PostMapping("/end/{matchId}")
    public ResponseEntity<JsonDefault> matchEnd(@PathVariable Long matchId,
                                                @AuthenticationPrincipal PrincipalDetails user) throws NotExistsMatchException {
        Member member = user.getMember();
        Match match = matchService.findByMatchOrRedirect(matchId, null);
        Manager manager = match.getManager();

        if (!member.isManager() || !manager.isSameMember(member)) {
            return ResponseEntity.badRequest().body(new JsonDefault(FAIL, "권한이 없습니다."));
        }

        matchService.matchEnd(match);
        provider.refresh(user);
        return ResponseEntity.ok(new JsonDefault(OK, "종료신청이 완료되었습니다."));
    }

    @Transactional
    @PostMapping("/record/{matchId}")
    public ResponseEntity<JsonDefault> scoreRecord(@PathVariable Long matchId,
                                                   @AuthenticationPrincipal PrincipalDetails user,
                                           @RequestBody ScoreResultForm score) throws NotExistsMatchException {
        Member member = user.getMember();
        Match match = matchService.findByMatchOrRedirect(matchId, null);
        Manager manager = match.getManager();
        if (!member.isManager() || !manager.isSameMember(member)) {
            return ResponseEntity.badRequest().body(new JsonDefault(FAIL, "권한이 없습니다."));
        }

        List<List<RecordForm>> playList = score.getPlayList();

        for (List<RecordForm> play : playList) { // 한 경기씩 for 문
            if (play.size() != 2) {
                return ResponseEntity.badRequest().body(new JsonDefault(FAIL, "점수 데이터가 존재하지 않습니다."));
            }
            boolean saveScore = scoreService.saveScore(match, play);
            if (!saveScore) {
                return ResponseEntity.badRequest().body(new JsonDefault(FAIL, "점수 기록이 실패했습니다. 관리자에게 문의해주세요."));
            }
        }
        scoreService.applyScore(match, playList);
        matchService.matchFinal(match);
        provider.refresh(user);
        return ResponseEntity.ok(new JsonDefault(OK, "기록을 확정했습니다."));
    }
}
