package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.controller.admin.EditMatchForm;
import football.start.allOfFootball.controller.admin.SaveMatchForm;
import football.start.allOfFootball.domain.*;
import football.start.allOfFootball.dto.match.MatchCollection;
import football.start.allOfFootball.dto.match.MatchData;
import football.start.allOfFootball.dto.match.TeamInfo;
import football.start.allOfFootball.enums.TeamEnum;
import football.start.allOfFootball.enums.matchEnums.MatchStatus;
import football.start.allOfFootball.enums.matchEnums.RequestTeam;
import football.start.allOfFootball.enums.matchEnums.TeamConfirm;
import football.start.allOfFootball.repository.domainRepository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static football.start.allOfFootball.enums.matchEnums.MatchStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MatchService {

    private final MatchRepository matchRepository;

    public Optional<Match> findByMatch(Long matchId) {
        if (matchId == null) {
            return Optional.empty();
        }
        return matchRepository.findByMatch(matchId);
    }

    public void saveMatch(Field field, SaveMatchForm saveMatchForm) {
        Match saveMatch = Match.build(field, saveMatchForm);
        matchRepository.saveMatch(saveMatch);
    }

    public void editMatch(Match match, EditMatchForm editMatchForm) {
        match.setEditMatch(editMatchForm);
    }

    public List<Orders> getOrders(Match match) {
        return match.getOrdersList();
    }

    public boolean distinctCheck(Match match, Long memberId) {
        List<Orders> ordersList = match.getOrdersList();
        return ordersList.stream().filter(x -> x.getMember().getMemberId() == memberId).findFirst().isPresent();
    }

    public void refreshMatchStatus(Match match) {
        int max = match.getMaxPerson() * match.getMatchCount(); // 참여할 수 있는 최대 인원 수

        int nowPerson = match.getOrdersList().size() + 1; // + 1 인거 주의해서 사용할 것

        int line = (int) (max * 0.8);
        if (max == nowPerson) {
            match.setMatchStatus(마감);
            return;
        }
        if (nowPerson >= line) {
            match.setMatchStatus(MatchStatus.마감임박);
        }

    }

    public boolean maxCheck(Match match) {
        int max = match.getMaxPerson() * match.getMatchCount(); // 참여할 수 있는 최대 인원 수

        int nowPerson = match.getOrdersList().size();

        return max <= nowPerson;
    }


    public List<Match> getMatchDeadLine() {
        return matchRepository.getMatchDeadLine();
    }

    public List<Match> understaffedList(List<Match> matchList) {
        List<Match> refundMatchList = new ArrayList<>();
        List<Match> removeList = new ArrayList<>();

        for (Match match : matchList) {
            MatchStatus status = match.getMatchStatus();
            if (status != 모집중 && status != 마감임박) continue;

            List<Orders> ordersList = match.getOrdersList();
            int maxPerson = match.getMaxPerson();

            if (ordersList.size() <= maxPerson) {
                refundMatchList.add(match);
                removeList.add(match);
            }
        }

        for (Match match : removeList) {
            matchList.remove(match);
        }

        return refundMatchList;
    }

    public MatchCollection getMatchCollection(Match match, Long memberId) {
        List<Orders> ordersList = match.getOrdersList();
        boolean isPresent = matchRepository.isContainsMember(ordersList, memberId);
        if (!isPresent) return null;

        List<MatchData> data = matchRepository.getMatchData(match, ordersList); // 매치 데이터
        Map<TeamEnum, List<TeamInfo>> teamInfo = matchRepository.getTeamInfo(match, ordersList); // 참가자

        return MatchCollection.builder()
            .matchData(data)
            .teamInfo(teamInfo)
            .build();
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

    public Long numberCheck(String matchIdStr) {
        try {
            return Long.parseLong(matchIdStr.replace("\"", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public List<Match> findAllMatchBefore(Manager manager) {
        return matchRepository.findAllMatchBefore(manager);
    }
}
