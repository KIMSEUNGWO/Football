package football.start.allOfFootball.dto;

import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.enums.gradeEnums.MatchEnum;
import football.start.allOfFootball.enums.matchEnums.MatchStatus;
import lombok.*;

import java.time.LocalDateTime;

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
    private MatchStatus matchStatus;

    public SearchResultForm(Match form) {
        matchId = form.getMatchId();
        matchHour = getTimeForm(form.getMatchDate());
        matchRegion = form.getField().getFieldLocation();
        matchTitle = form.getField().getFieldTitle();
        matchGender = form.getMatchGender();
        matchGrade = form.getMatchGrade();
        matchMaxPerson = getMaxPersonForm(form.getMaxPerson());
        matchStatus = form.getMatchStatus();
    }

    private static String getMaxPersonForm(Integer maxPerson) {
        return maxPerson + " vs " + maxPerson;
    }

    private static String getTimeForm(LocalDateTime date) {
        return String.format("%02d:00", date.getHour());
    }
}
