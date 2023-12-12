package football.start.allOfFootball.dto;

import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.enums.gradeEnums.MatchEnum;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResultForm {

    private Long matchId;
    private String matchHour;
    private LocationEnum matchRegion;
    private String matchTitle;
    private GenderEnum matchGender;
    private MatchEnum matchGrade;
    private String matchMaxPerson;

    public static SearchResultForm build(Match form) {
        return SearchResultForm.builder()
            .matchId(form.getMatchId())
            .matchHour(getTimeForm(form.getMatchHour()))
            .matchRegion(form.getField().getFieldLocation())
            .matchTitle(form.getField().getFieldTitle())
            .matchGender(form.getMatchGender())
            .matchGrade(form.getMatchGrade())
            .matchMaxPerson(getMaxPersonForm(form.getMaxPerson()))
            .build();
    }

    private static String getMaxPersonForm(Integer maxPerson) {
        return maxPerson + " vs " + maxPerson;
    }

    private static String getTimeForm(Integer matchHour) {
        return String.format("%02d:00", matchHour);
    }
}
