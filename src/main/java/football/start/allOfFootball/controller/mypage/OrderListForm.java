package football.start.allOfFootball.controller.mypage;

import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.formatter.DateFormatter;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class OrderListForm {

    private Long matchId;
    private String matchDate;
    private String matchHour;
    private String maxPersonAndCount;
    private String fieldTitle;
    private String matchStatus;
    private Integer resultScore;

    public void build(Orders orders) {
        Match match = orders.getMatch();

        matchId = match.getMatchId();
        matchDate = DateFormatter.format("yyyy년 M월 d일", match.getMatchDate());
        matchHour = getHourForm(match.getMatchDate());
        maxPersonAndCount = getMix(match.getMaxPerson(), match.getMatchCount());
        fieldTitle = match.getField().getFieldTitle();
        matchStatus = match.getMatchStatus().name();
        resultScore = orders.getScore();
    }

    private static String getMix(Integer maxPerson, Integer matchCount) {
        return maxPerson + " vs " + maxPerson + " " + matchCount + "파전";
    }

    private static String getHourForm(LocalDateTime matchHour) {
        return String.format("%02d:00", matchHour.getHour());
    }
}
