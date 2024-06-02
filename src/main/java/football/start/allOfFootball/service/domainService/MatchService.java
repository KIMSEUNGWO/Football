package football.start.allOfFootball.service.domainService;

import football.common.domain.*;
import football.common.exception.match.NotExistsMatchException;
import football.start.allOfFootball.dto.match.*;
import football.start.allOfFootball.enums.matchEnums.RequestTeam;
import football.start.allOfFootball.enums.matchEnums.TeamConfirm;
import football.start.allOfFootball.repository.domainRepository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static football.common.enums.matchenum.MatchStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MatchService {

    private final MatchRepository matchRepository;
    private final ScoreService scoreService;

    public Match findByMatchOrRedirect(Long matchId, String redirectURI) throws NotExistsMatchException {
        return matchRepository.findByMatch(matchId)
            .orElseThrow(() -> new NotExistsMatchException(redirectURI));
    }

    public boolean distinctCheck(Match match, Member member) {
        return matchRepository.existsByMatch(match, member);
    }

    public void refreshMatchStatus(Match match) {
        int max = match.getMaxPerson() * match.getMatchCount(); // 참여할 수 있는 최대 인원 수

        int nowPerson = match.getOrdersList().size() + 1; // + 1 인거 주의해서 사용할 것 (본인포함이기때문에 +1임)

        int line = (int) (max * 0.8);
        if (nowPerson >= max) {
            match.setMatchStatus(마감);
        } else if (nowPerson >= line) {
            match.setMatchStatus(마감임박);
        }

    }

    public MatchCollection getMatchCollection(Match match, Member member) {
        List<Orders> ordersList = match.getOrdersList();

        return new MatchCollection(
            matchRepository.isContainsMember(ordersList, member)
                .map(orders -> scoreService.getScore(match, orders))
                .orElse(null), // 경기결과
            matchRepository.getMatchData(match, ordersList), // 매치 데이터
            matchRepository.getTeamInfo(match, ordersList) // 참가자
        );
    }

    public void changeTeam(Match match, RequestTeam team) {
        List<Orders> ordersList = match.getOrdersList();

        List<TeamConfirm> changeList = team.getTeam();
        for (TeamConfirm teamConfirm : changeList) {
            matchRepository.changeTeam(ordersList, teamConfirm);
        }

        match.setMatchStatus(경기중);
    }

    public void saveManager(Member member, Match match) {
        Manager manager = member.getManager();
        match.setManager(manager);
    }


    public boolean existsByMatchId(Long matchId) {
        return matchRepository.existsByMatchId(matchId);
    }
}
