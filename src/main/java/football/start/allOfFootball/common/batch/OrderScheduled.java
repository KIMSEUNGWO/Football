package football.start.allOfFootball.common.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderScheduled {

    // 경기 매치
    @Scheduled(cron = "0 0 0/2 * * ?") // 2시간 간격으로 실행
    public void match() {

    }

    // 결과 집계
    @Scheduled(cron = "0 30 0/2 * * ?") // 2시간 간격으로 30분에 실행
    public void matchResult() {

    }
}
