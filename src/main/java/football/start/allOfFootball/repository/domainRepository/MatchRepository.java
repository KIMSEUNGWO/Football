package football.start.allOfFootball.repository.domainRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.domain.Profile;
import football.start.allOfFootball.dto.match.MatchData;
import football.start.allOfFootball.dto.match.MatchDataCalculator;
import football.start.allOfFootball.dto.match.TeamInfo;
import football.start.allOfFootball.enums.TeamEnum;
import football.start.allOfFootball.enums.gradeEnums.GradeEnum;
import football.start.allOfFootball.jpaRepository.JpaMatchRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static football.start.allOfFootball.domain.QMatch.match;

@Repository
@Slf4j
public class MatchRepository {

    private final JpaMatchRepository jpaMatchRepository;
    private final JPAQueryFactory query;

    public MatchRepository(JpaMatchRepository jpaMatchRepository, EntityManager em) {
        this.jpaMatchRepository = jpaMatchRepository;
        this.query = new JPAQueryFactory(em);
    }

    public Optional<Match> findByMatch(Long matchId) {
        return jpaMatchRepository.findById(matchId);
    }

    public void saveMatch(Match saveMatch) {
        jpaMatchRepository.save(saveMatch);
    }

    public boolean isContainsMember(List<Orders> ordersList, Long memberId) {
        for (Orders orders : ordersList) {
            Long memberId1 = orders.getMember().getMemberId();
            if (memberId1 == memberId) {
                return true;
            }
        }
        return false;
    }

    public List<Match> getMatchDeadLine() {
        LocalDateTime now = LocalDateTime.now().plusMinutes(80);
        LocalDateTime after = LocalDateTime.now().plusMinutes(100); // 1시간 30분 전 마감처리 시작 +- 10분

        return query.selectFrom(match)
            .where(match.matchDate.between(now, after))
            .fetch();
    }

    public List<MatchData> getMatchData(Match match, List<Orders> ordersList) {

        int person = ordersList.size();
        MatchDataCalculator cal = new MatchDataCalculator(person);

        for (Orders orders : ordersList) {
            GradeEnum memberGrade = orders.getMember().getGrade();
            cal.put(memberGrade);
        }
        List<GradeEnum> gradeList = match.getMatchGrade().getGradeList();
        return gradeList.stream().map(x -> MatchData.builder().grade(x).percent(cal.calculate(x)).build()).collect(Collectors.toList());
    }

    public Map<TeamEnum, List<TeamInfo>> getTeamInfo(Match match, List<Orders> ordersList) {
        for (Orders orders : ordersList) {
            if (orders.getTeam() == null) return null;
        }
        int teamCount = match.getMatchCount();
        List<TeamEnum> team = TeamEnum.getTeam(teamCount);
        ordersList.sort((o1, o2) -> o2.getMember().getMemberScore() - o1.getMember().getMemberScore()); // 점수가 높은순

        Map<TeamEnum, List<TeamInfo>> result = new LinkedHashMap<>(); // Map 순서보장(RED, BLUE, YELLOW 순)을 위해 LinkedHashMap 사용
        for (TeamEnum teamEnum : team) {
            result.put(teamEnum, new ArrayList<>());
        }
        for (Orders x : ordersList) {
            Profile profile = x.getMember().getProfile();
            String profileName = null;
            if (profile == null) {
                profileName = "base.jpeg";
            }
            TeamInfo teamInfo = TeamInfo.builder()
                .profileImage(profileName)
                .memberName(x.getMember().getMemberName())
                .memberGrade(x.getMember().getGrade())
                .build();

            List<TeamInfo> teamInfoList = result.get(x.getTeam());
            teamInfoList.add(teamInfo);
        }
        return result;
    }
}
