package football.start.allOfFootball.common.batch;

import football.redis.service.RankService;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.jpaRepository.JpaMemberRepository;
import football.start.allOfFootball.service.domainService.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderScheduled {

    private final MatchService matchService;
    private final JpaMemberRepository jpaMemberRepository;
    private final RankService rankService;

    private final OrderScheduledService scheduledService;

    // 경기 매치 전 최소인원 체크 및 알고리즘에 의한 팀 설정
    @Scheduled(cron = "0 30 0/2 * * ?") // 매일 경기시작 90분전에 경기마감처리 ( 4시 경기면 2시 30분에 스케쥴 시작 )
    public void match() {
        log.info("경기 시작 전 스케쥴러 실행");

        List<Match> matchList = matchService.getMatchDeadLine();
        if (matchList.isEmpty()) {
            log.info("경기 정보 없음");
            return;
        }

        log.info("최소인원 체크 시작");
        scheduledService.minCheck(matchList);
        log.info("최소인원 체크 종료");


        log.info("MatchStatus '경기시작' 으로 변경 시작");
        scheduledService.matchStart(matchList);
        log.info("MatchStatus '경기시작' 으로 변경 종료");


        log.info("경기 시작 전 스케쥴러 종료");
    }

    // 결과 집계
    @Scheduled(cron = "0 30 0/2 * * ?") // 2시간 간격으로 30분에 실행 ( 경기 종료 후 30분 후 랭킹최신화 )
    public void matchResult() {
        List<Member> memberAll = jpaMemberRepository.findAll();
        for (Member member : memberAll) {
            Long memberId = member.getMemberId();
            int score = member.getMemberScore();

            rankService.updateRank(memberId, score);
        }
    }
}
