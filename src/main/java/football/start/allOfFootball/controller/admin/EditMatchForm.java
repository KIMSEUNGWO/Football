package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.gradeEnums.MatchEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditMatchForm {

    private Long matchId;
    private String matchDate;

    private Integer matchHour;
    private Integer matchMaxPerson;
    private Integer matchCount;

    private GenderEnum gender;

    private MatchEnum matchGrade;

    public EditMatchForm(Match form) {
        matchId = form.getMatchId();
        matchDate = getDateForm(form.getMatchDate());
        matchHour = getHour(form.getMatchDate());
        matchMaxPerson = form.getMaxPerson();
        matchCount = form.getMatchCount();
        gender = form.getMatchGender();
        matchGrade = form.getMatchGrade();
    }

    private static Integer getHour(LocalDateTime matchDate) {
        return matchDate.getHour();
    }

    private static String getDateForm(LocalDateTime matchDate) {
        return timeFormat(matchDate.getYear()) + "/" + timeFormat(matchDate.getMonthValue()) + "/" + timeFormat(matchDate.getDayOfMonth());
    }

    private static String timeFormat(int time) {
        return String.format("%02d", time);
    }
}
