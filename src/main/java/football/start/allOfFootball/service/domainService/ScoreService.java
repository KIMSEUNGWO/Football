package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.common.ScoreCalculator;
import football.common.domain.Match;
import football.common.domain.Member;
import football.common.domain.Orders;
import football.common.domain.score.Score;
import football.start.allOfFootball.dto.match.MatchScoreForm;
import football.start.allOfFootball.dto.match.ScoreResult;
import football.start.allOfFootball.dto.matchRecordForm.RecordForm;
import football.common.enums.domainenum.TeamEnum;
import football.common.enums.matchenum.MatchStatus;
import football.start.allOfFootball.enums.matchEnums.ResultEnum;
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


    @Transactional
    public boolean saveScore(Match match, List<RecordForm> recordList) {

        List<Score> scoreList = recordList.stream().map(record -> {
            TeamEnum leftTeam = record.getLeftTeam();
            Integer leftScore = record.getLeftScore();
            TeamEnum rightTeam = record.getRightTeam();
            Integer rightScore = record.getRightScore();

            TeamEnum winTeam = null;
            if (leftScore > rightScore) winTeam = leftTeam;
            else if (leftScore < rightScore) winTeam = rightTeam;
            return Score.builder()
                .match(match)
                .team1(leftTeam)
                .team1Score(leftScore)
                .team2(rightTeam)
                .team2Score(rightScore)
                .winTeam(winTeam)
                .build();
        }).toList();

        scoreRepository.saveAllScore(scoreList);
        return true;
    }

    public void applyScore(Match match, List<RecordForm> playList) {
        ScoreCalculator calculator = new ScoreCalculator();

        for (RecordForm record : playList) {
            TeamEnum leftTeam = record.getLeftTeam();
            Integer leftScore = record.getLeftScore();
            TeamEnum rightTeam = record.getRightTeam();
            Integer rightScore = record.getRightScore();

            calculator.put(leftTeam, leftScore, rightTeam, rightScore);
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
        List<MatchScoreForm> result = new ArrayList<>();
        TeamEnum myTeam = orders.getTeam();
        if (match.getMatchStatus() == MatchStatus.기록중) return ScoreResult.builder().myTeam(myTeam).scoreList(new ArrayList<>()).build();
        if (match.getMatchStatus() != MatchStatus.종료) return null;

        List<Score> scoreList = match.getScoreList();
        System.out.println("scoreList = " + scoreList);
        if (scoreList.isEmpty()) return ScoreResult.builder().myTeam(myTeam).build();;

        for (Score score : scoreList) {

            TeamEnum leftTeam = score.getTeam1();
            int leftScore = score.getTeam1Score();
            TeamEnum rightTeam = score.getTeam2();
            int rightScore = score.getTeam2Score();
            TeamEnum winTeam = score.getWinTeam();

            ResultEnum resultScore = scoreRepository.getResultScore(myTeam, winTeam);

            MatchScoreForm build = MatchScoreForm.builder()
                .leftTeam(leftTeam)
                .leftScore(leftScore)
                .rightTeam(rightTeam)
                .rightScore(rightScore)
                .resultEnum(resultScore)
                .build();

            result.add(build);
        }

        return ScoreResult.builder().myTeam(myTeam).scoreList(result).build();
    }
}
