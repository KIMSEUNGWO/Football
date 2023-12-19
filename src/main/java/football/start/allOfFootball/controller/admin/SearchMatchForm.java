package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.LocationEnum;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchMatchForm {

    private Long matchId;
    private String matchDate;
    private String matchTime;
    private LocationEnum matchRegion;
    private String matchTitle;
    private String matchPerson;


    public static SearchMatchForm build(Match match, Integer orderPerson) {
        return SearchMatchForm.builder()
            .matchId(match.getMatchId())
            .matchDate(getDateForm(match.getMatchDate()))
            .matchTime(getTimeForm(match.getMatchDate()))
            .matchRegion(match.getField().getFieldLocation())
            .matchTitle(match.getField().getFieldTitle())
            .matchPerson(orderPerson + " / " + (match.getMaxPerson() * match.getMatchCount()))
            .build();
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
