package football.batch.service;

import football.batch.component.MatchTeamAlgorithms;
import football.batch.repository.ScheduledRepository;
import football.common.domain.Match;
import football.common.domain.Orders;
import football.common.enums.domainenum.TeamEnum;
import football.payment.service.RefundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static football.common.enums.matchenum.MatchStatus.*;
import static football.common.enums.paymentEnums.RefundEnum.전체환불;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderScheduledService {

    private final ScheduledRepository scheduledRepository;
    private final RefundService refundService;
    private final MatchTeamAlgorithms algorithms;

    public void minCheck(List<Match> matchList) {
        List<Match> refundList = scheduledRepository.understaffedList(matchList); // 최소 인원 이하인 경우
        for (Match match : refundList) {
            match.setMatchStatus(취소);
            refundService.refund(match, 전체환불);
        }
    }

    public void matchStart(List<Match> matchList) {
        for (Match match : matchList) {
            match.setMatchStatus(경기시작전);
            List<Orders> ordersList = match.getOrdersList();

            // team 자동 분배 알고리즘 시작
            Map<TeamEnum, List<Orders>> result = algorithms.getResult(ordersList, match.getMatchCount());

            // 결과 Orders TeamEnum 설정
            scheduledRepository.setTeam(result);
        }
    }

    public List<Match> getMatchDeadLine() {
        return scheduledRepository.getMatchDeadLine();
    }
}
