package football.batch.repository;

import football.common.domain.*;
import football.common.enums.domainenum.TeamEnum;
import football.common.enums.matchenum.MatchStatus;
import football.common.jpaRepository.JpaMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static football.common.enums.matchenum.MatchStatus.마감임박;
import static football.common.enums.matchenum.MatchStatus.모집중;

@Repository
@RequiredArgsConstructor
public class ScheduledRepository {

    private final JpaMatchRepository jpaMatchRepository;

    public List<Match> getMatchDeadLine() {
        LocalDateTime start = LocalDateTime.now().plusMinutes(80);
        LocalDateTime end = start.plusMinutes(20); // 1시간 30분 전 마감처리 시작 +- 10분
        return jpaMatchRepository.findAllByMatchDateBetween(start, end);
    }

    public List<Match> understaffedList(List<Match> matchList) {
        List<Match> refundMatchList = new ArrayList<>();

        for (Match match : matchList) {
            MatchStatus status = match.getMatchStatus();
            if (status != 모집중 && status != 마감임박) continue;

            List<Orders> ordersList = match.getOrdersList();
            int maxPerson = match.getMaxPerson();

            if (ordersList.size() <= maxPerson) {
                refundMatchList.add(match);
            }
        }

        for (Match match : refundMatchList) {
            matchList.remove(match);
        }

        return refundMatchList;
    }

    public void setTeam(Map<TeamEnum, List<Orders>> result) {
        for (TeamEnum teamEnum : result.keySet()) {
            List<Orders> ordersList = result.get(teamEnum);
            for (Orders orders : ordersList) {
                orders.setTeam(teamEnum);
            }
        }
    }
}
