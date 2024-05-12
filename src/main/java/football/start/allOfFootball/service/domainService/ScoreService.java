package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.common.ScoreCalculator;
import football.internal.database.domain.Match;
import football.internal.database.domain.Member;
import football.internal.database.domain.Orders;
import football.internal.database.domain.score.Goal;
import football.internal.database.domain.score.Score;
import football.internal.database.domain.score.Team;
import football.start.allOfFootball.dto.match.MatchScoreForm;
import football.start.allOfFootball.dto.match.MatchTeamForm;
import football.start.allOfFootball.dto.match.ScoreResult;
import football.start.allOfFootball.dto.matchRecordForm.Player;
import football.start.allOfFootball.dto.matchRecordForm.RecordForm;
import football.start.allOfFootball.enums.TeamEnum;
import football.start.allOfFootball.enums.matchEnums.MatchStatus;
import football.start.allOfFootball.enums.matchEnums.ResultEnum;
import football.start.allOfFootball.repository.domainRepository.OrderRepository;
import football.start.allOfFootball.repository.domainRepository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final OrderRepository orderRepository;


    @Transactional
    public boolean saveScore(Match match, List<RecordForm> recordList) {
        List<Orders> ordersList = match.getOrdersList();
        List<Long> orderIdList = ordersList.stream().map(Orders::getOrdersId).toList();

        Score saveScore = Score.builder().match(match).build();
        scoreRepository.saveScore(saveScore);

        for (RecordForm form : recordList) {
            TeamEnum team = form.getTeam();
            Team saveTeam = Team.builder().score(saveScore).teamEnum(team).build();
            scoreRepository.saveTeam(saveTeam);

            List<Player> goalList = form.getGoalList();

            for (Player player : goalList) {
                boolean orderIdIsNumber = scoreRepository.isNumber(player.getOrderId());
                boolean timeIsNumber = scoreRepository.isNumber(player.getTime());
                if (!orderIdIsNumber || !timeIsNumber) return false;

                Long playerOrderId = Long.parseLong(player.getOrderId());
                Integer playerTime = Integer.parseInt(player.getTime());
                Optional<Orders> findOrders = orderRepository.findById(playerOrderId);
                if (findOrders.isEmpty()) return false;

                Orders orders = findOrders.get();
                boolean contains = orderIdList.contains(playerOrderId);
                if (!contains) return false;

                Goal saveGoal = Goal.builder().team(saveTeam).orders(orders).time(playerTime).build();
                scoreRepository.saveGoal(saveGoal);
            }
        }
        return true;
    }

    public void applyScore(Match match, List<List<RecordForm>> playList) {
        ScoreCalculator calculator = new ScoreCalculator();

        for (List<RecordForm> recordForms : playList) {
            RecordForm leftForm = recordForms.get(0);
            RecordForm rightForm = recordForms.get(1);

            TeamEnum leftTeam = leftForm.getTeam();
            int leftGoal = leftForm.getGoalList().size();
            TeamEnum rightTeam = rightForm.getTeam();
            int rightGoal = rightForm.getGoalList().size();

            calculator.put(leftTeam, leftGoal, rightTeam, rightGoal);
        }

        List<Orders> ordersList = match.getOrdersList();

        for (Orders orders : ordersList) {
            TeamEnum team = orders.getTeam();
            int score = calculator.getScore(team);
            Member member = orders.getMember();

            orders.setScore(score);
            member.addScore(score);
        }
    }

    public ScoreResult getScore(Match match, Orders orders) {
        List<MatchScoreForm> result = new LinkedList<>();
        TeamEnum myTeam = orders.getTeam();
        if (match.getMatchStatus() == MatchStatus.기록중) return ScoreResult.builder().myTeam(myTeam).scoreList(Collections.emptyList()).build();
        if (match.getMatchStatus() != MatchStatus.종료) return null;

        List<Score> scoreList = match.getScoreList();
        if (scoreList.isEmpty()) return ScoreResult.builder().myTeam(myTeam).build();;

        for (Score score : scoreList) {
            List<Team> teamList = score.getTeamList();

            Team leftTeam = teamList.get(0);
            Team rightTeam = teamList.get(1);

            MatchTeamForm leftTeamForm = scoreRepository.getTeamForm(leftTeam);
            MatchTeamForm rightTeamForm = scoreRepository.getTeamForm(rightTeam);
            ResultEnum resultScore = scoreRepository.getResultScore(myTeam, leftTeamForm, rightTeamForm);

            MatchScoreForm build = MatchScoreForm.builder().leftTeam(leftTeamForm).rightTeam(rightTeamForm).resultEnum(resultScore).build();

            result.add(build);
        }

        return ScoreResult.builder().myTeam(myTeam).scoreList(result).build();
    }
}
