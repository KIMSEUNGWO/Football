package football.start.allOfFootball.repository.domainRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.common.consts.Constant;
import football.common.domain.*;
import football.common.jpaRepository.JpaOrderRepository;
import football.start.allOfFootball.controller.mypage.MatchDataForm;
import football.start.allOfFootball.dto.match.MatchData;
import football.start.allOfFootball.dto.match.MatchDataCalculator;
import football.start.allOfFootball.dto.match.TeamInfo;
import football.common.enums.domainenum.TeamEnum;
import football.common.enums.gradeEnums.GradeEnum;
import football.start.allOfFootball.enums.matchEnums.TeamConfirm;
import football.common.jpaRepository.JpaMatchRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static football.common.domain.QMatch.match;
import static football.common.enums.matchenum.MatchStatus.*;

@Repository
@Slf4j
public class MatchRepository {

    private final JpaMatchRepository jpaMatchRepository;
    private final JpaOrderRepository jpaOrderRepository;
    private final JPAQueryFactory query;

    public MatchRepository(JpaMatchRepository jpaMatchRepository, JpaOrderRepository jpaOrderRepository, EntityManager em) {
        this.jpaMatchRepository = jpaMatchRepository;
        this.jpaOrderRepository = jpaOrderRepository;
        this.query = new JPAQueryFactory(em);
    }

    public Optional<Match> findByMatch(Long matchId) {
        return jpaMatchRepository.findById(matchId);
    }

    public Optional<Orders> isContainsMember(List<Orders> ordersList, Member member) {

        for (Orders orders : ordersList) {
            Member inMember = orders.getMember();
            if (inMember.equals(member)) {
                return Optional.of(orders);
            }
        }
        return Optional.empty();
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
        if (match.getMatchStatus().isPlayBefore()) {
            return null;
        }
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
                profileName = Constant.BASE_IMG;
            }
            TeamInfo teamInfo = TeamInfo.builder()
                .orderId(x.getOrdersId())
                .profileImage(profileName)
                .memberName(x.getMember().getMemberName())
                .memberGrade(x.getMember().getGrade())
                .build();

            List<TeamInfo> teamInfoList = result.get(x.getTeam());
            teamInfoList.add(teamInfo);
        }
        return result;
    }

    public void changeTeam(List<Orders> ordersList, TeamConfirm teamConfirm) {
        TeamEnum teamColor = teamConfirm.getTeamColor();
        List<Long> player = teamConfirm.getPlayer();

        for (Orders orders : ordersList) {
            if (player.contains(orders.getOrdersId())) {
                orders.setTeam(teamColor);
            }
        }
    }

    public List<Match> findAllMatchBefore(Manager manager) {
        return query.selectFrom(match)
            .where(match.manager.eq(manager).and(match.matchStatus.notIn(종료,취소)))
            .orderBy(match.matchDate.asc())
            .fetch();
    }

    public MatchDataForm getMatchDataForm(Match match, List<Orders> ordersList) {
        return MatchDataForm.builder()
            .matchId(match.getMatchId())
            .matchTime(getTime(match.getMatchDate()))
            .maxPersonAndCount(getPersonAndCount(match))
            .fieldTitle(match.getField().getFieldTitle())
            .nowPerson(ordersList.size() + " / " + (match.getMaxPerson() * match.getMatchCount()))
            .matchStatus(match.getMatchStatus())
            .build();
    }

    private String getPersonAndCount(Match match) {
        int person = match.getMaxPerson();
        int count = match.getMatchCount();
        return person + " vs " + person + " " + count + "파전";
    }

    private String getTime(LocalDateTime matchDate) {
        int hour = matchDate.getHour();
        return String.format("%02d:00", hour);
    }

    public boolean existsByMatchId(Long matchId) {
        return jpaMatchRepository.existsById(matchId);
    }

    public boolean existsByMatch(Match match, Member member) {
        return jpaOrderRepository.existsByMatchAndMember(match, member);
    }
}
