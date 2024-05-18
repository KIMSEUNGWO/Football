package football.admin.dto;

import football.common.domain.Match;
import football.common.enums.domainenum.LocationEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchMatchResponse {

    private Long matchId;
    private String matchDate;
    private String matchTime;
    private LocationEnum matchRegion;
    private String matchTitle;
    private String matchPerson;
    private String matchStatus;


    public SearchMatchResponse(Match match, Integer orderPerson) {
        matchId = match.getMatchId();
        matchDate = getDateForm(match.getMatchDate());
        matchTime = getTimeForm(match.getMatchDate());
        matchRegion = match.getField().getFieldLocation();
        matchTitle = match.getField().getFieldTitle();
        matchPerson = orderPerson + " / " + (match.getMaxPerson() * match.getMatchCount());
        matchStatus = match.getMatchStatus().name();
    }

    private static String getTimeForm(LocalDateTime time) {
        return timeFormat(time.getHour()) + ":00";
    }

    private static String getDateForm(LocalDateTime matchDate) {
        return timeFormat(matchDate.getYear()) + "-" + timeFormat(matchDate.getMonthValue()) + "-" + timeFormat(matchDate.getDayOfMonth());
    }

    private static String timeFormat(int time) {
        return String.format("%02d", time);
    }

}
