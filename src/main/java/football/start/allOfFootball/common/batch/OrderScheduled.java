package football.start.allOfFootball.common.batch;

import football.start.allOfFootball.common.MatchTeamAlgorithms;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.enums.TeamEnum;
import football.start.allOfFootball.enums.paymentEnums.RefundEnum;
import football.start.allOfFootball.service.OrderService;
import football.start.allOfFootball.service.RefundService;
import football.start.allOfFootball.service.domainService.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static football.start.allOfFootball.enums.matchEnums.MatchStatus.*;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderScheduled {

    private final MatchService matchService;
    private final OrderService orderService;
    private final RefundService refundService;

    // 경기 매치 전 최소인원 체크 및 알고리즘에 의한 팀 설정
    @Scheduled(cron = "0 30 0/2 * * ?") // 매일 경기시작 90분전에 경기마감처리 ( 4시 경기면 2시 30분에 스케쥴 시작 )
    public void match() {
        log.info("경기 시작 전 스케쥴러 실행");

        log.info("최소인원 체크 시작");
        List<Match> matchList = matchService.getMatchDeadLine();
        if (matchList.isEmpty()) return ;

        List<Match> refundList = matchService.understaffedList(matchList); // 최소 인원 이하인 경우
        for (Match match : refundList) {
            match.setMatchStatus(취소);
            refundService.refund(match, RefundEnum.전체환불);
        }
        log.info("최소인원 체크 종료");

        log.info("MatchStatus '경기시작' 으로 변경 시작");

        for (Match match : matchList) {
            match.setMatchStatus(경기시작전);
            List<Orders> ordersList = match.getOrdersList();

            // team 자동 분배 알고리즘 시작
            MatchTeamAlgorithms setTeam = new MatchTeamAlgorithms(ordersList);
            Map<TeamEnum, List<Orders>> result = setTeam.getResult(match.getMatchCount());

            // 결과 Orders TeamEnum 설정
            orderService.setTeam(result);
        }

        log.info("MatchStatus '경기시작' 으로 변경 종료");

        log.info("경기 시작 전 스케쥴러 종료");
    }

    // 결과 집계
    @Scheduled(cron = "0 30 0/2 * * ?") // 2시간 간격으로 30분에 실행
    public void matchResult() {

    }
}
