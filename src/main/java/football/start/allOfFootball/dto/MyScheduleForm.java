package football.start.allOfFootball.dto;

import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.LocationEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class MyScheduleForm {

    private Long matchId;
    private String hour;
    private LocationEnum region;
    private String fieldTitle;

    public static MyScheduleForm build(Match match) {
        return MyScheduleForm.builder()
            .matchId(match.getMatchId())
            .hour(getHourForm(match.getMatchDate()))
            .region(match.getField().getFieldLocation())
            .fieldTitle(match.getField().getFieldTitle())
            .build();
    }

    private static String getHourForm(LocalDateTime matchDate) {
        return String.format("%02d:00",matchDate.getHour());
    }
}
