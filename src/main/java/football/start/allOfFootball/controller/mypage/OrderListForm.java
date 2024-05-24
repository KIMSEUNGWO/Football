package football.start.allOfFootball.controller.mypage;

import football.common.domain.Match;
import football.common.domain.Orders;
import football.common.formatter.DateFormatter;
import lombok.*;

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

    public OrderListForm(Orders orders) {
        Match match = orders.getMatch();

        matchId = match.getMatchId();
        matchDate = DateFormatter.format("yyyy년 M월 d일", match.getMatchDate());
        matchHour = DateFormatter.format("HH:mm",match.getMatchDate());
        maxPersonAndCount = getMix(match.getMaxPerson(), match.getMatchCount());
        fieldTitle = match.getField().getFieldTitle();
        matchStatus = match.getMatchStatus().name();
        resultScore = orders.getScore();
    }

    private String getMix(int maxPerson, int matchCount) {
        return String.format("%d vs %d %d파전", maxPerson, maxPerson, matchCount);
    }
}
