package football.start.allOfFootball.dto;

import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.LocationEnum;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class MyScheduleForm {

    private String hour;
    private LocationEnum region;
    private String fieldTitle;

    public static MyScheduleForm build(Match match) {
        return MyScheduleForm.builder()
            .hour(getHourForm(match.getMatchHour()))
            .region(match.getField().getFieldLocation())
            .fieldTitle(match.getField().getFieldTitle())
            .build();
    }

    private static String getHourForm(Integer matchHour) {
        return String.format("%02d:00",matchHour);
    }
}
