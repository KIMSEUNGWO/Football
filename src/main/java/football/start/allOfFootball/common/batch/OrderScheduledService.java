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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static football.start.allOfFootball.enums.matchEnums.MatchStatus.경기시작전;
import static football.start.allOfFootball.enums.matchEnums.MatchStatus.취소;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderScheduledService {

    private final RefundService refundService;
    private final MatchService matchService;
    private final OrderService orderService;

    public void minCheck(List<Match> matchList) {
        List<Match> refundList = matchService.understaffedList(matchList); // 최소 인원 이하인 경우
        for (Match match : refundList) {
            match.setMatchStatus(취소);
            refundService.refund(match, RefundEnum.전체환불);
        }
    }

    public void matchStart(List<Match> matchList) {
        for (Match match : matchList) {
            match.setMatchStatus(경기시작전);
            List<Orders> ordersList = match.getOrdersList();

            // team 자동 분배 알고리즘 시작
            MatchTeamAlgorithms setTeam = new MatchTeamAlgorithms(ordersList);
            Map<TeamEnum, List<Orders>> result = setTeam.getResult(match.getMatchCount());

            // 결과 Orders TeamEnum 설정
            orderService.setTeam(result);
        }
    }
}
