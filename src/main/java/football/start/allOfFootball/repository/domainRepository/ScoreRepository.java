package football.start.allOfFootball.repository.domainRepository;

import football.common.domain.Orders;
import football.common.domain.score.Goal;
import football.common.domain.score.Score;
import football.common.domain.score.Team;
import football.start.allOfFootball.dto.match.MatchGoalForm;
import football.start.allOfFootball.dto.match.MatchTeamForm;
import football.common.enums.domainenum.TeamEnum;
import football.start.allOfFootball.enums.matchEnums.ResultEnum;
import football.common.jpaRepository.JpaGoalRepository;
import football.common.jpaRepository.JpaScoreRepository;
import football.common.jpaRepository.JpaTeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ScoreRepository {

    private final JpaScoreRepository jpaScoreRepository;
    private final JpaTeamRepository jpaTeamRepository;
    private final JpaGoalRepository jpaGoalRepository;

    public boolean isNumber(String orderId) {
        try {
            Long.parseLong(orderId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void saveScore(Score saveScore) {
        jpaScoreRepository.save(saveScore);
    }

    public void saveTeam(Team saveTeam) {
        jpaTeamRepository.save(saveTeam);
    }

    public void saveGoal(Goal saveGoal) {
        jpaGoalRepository.save(saveGoal);
    }

    public MatchTeamForm getTeamForm(Team team) {

        TeamEnum teamEnum = team.getTeamEnum();
        List<Goal> goalList = team.getGoalList();

        List<MatchGoalForm> goalForm = new ArrayList<>();

        for (Goal goal : goalList) {
            Orders orders1 = goal.getOrders();
            Integer time = goal.getTime();
            MatchGoalForm build = MatchGoalForm.builder().name(orders1.getMember().getMemberName()).time(time).build();
            goalForm.add(build);
        }

        return MatchTeamForm.builder().teamEnum(teamEnum).goalList(goalForm).build();
    }

    public ResultEnum getResultScore(TeamEnum myTeam, MatchTeamForm leftTeamForm, MatchTeamForm rightTeamForm) {
        // 내 팀과 무관한 경기일경우 null
        if (myTeam != leftTeamForm.getTeamEnum() && myTeam != rightTeamForm.getTeamEnum()) {
            return null;
        }

        TeamEnum leftTeam = leftTeamForm.getTeamEnum();
        int leftScore = leftTeamForm.getGoalList().size();
        TeamEnum rightTeam = rightTeamForm.getTeamEnum();
        int rightScore = rightTeamForm.getGoalList().size();

        if (leftScore == rightScore) return ResultEnum.무; // 동점이면 무승부
        if (leftScore > rightScore) { // 왼쪽팀이 점수가 높을때
            if (leftTeam == myTeam) return ResultEnum.승; // 내 팀이 왼쪽팀이면 승
            return ResultEnum.패; // 아니면 패
        }
        // 오른쪽팀이 점수간 높을 때
        if (rightTeam == myTeam) return ResultEnum.승; // 우리팀이면 승
        return ResultEnum.패; // 아니면 패
    }
}
