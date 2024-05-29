package football.start.allOfFootball.repository.domainRepository;

import football.common.domain.score.Score;
import football.common.enums.domainenum.TeamEnum;
import football.start.allOfFootball.enums.matchEnums.ResultEnum;
import football.common.jpaRepository.JpaScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ScoreRepository {

    private final JpaScoreRepository jpaScoreRepository;

    public ResultEnum getResultScore(TeamEnum myTeam, TeamEnum winTeam) {
        // 내 팀과 무관한 경기일경우 null
        if (winTeam == null) return ResultEnum.무;
        return (myTeam == winTeam) ? ResultEnum.승 : ResultEnum.패;
    }

    public void saveAllScore(List<Score> scoreList) {
        jpaScoreRepository.saveAll(scoreList);
    }
}
