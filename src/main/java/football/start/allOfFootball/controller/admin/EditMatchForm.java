package football.start.allOfFootball.controller.admin;

import football.internal.database.domain.Match;
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

    private String matchHour;
    private Integer matchMaxPerson;
    private Integer matchCount;

    private GenderEnum gender;

    private MatchEnum matchGrade;

    private boolean isExit;

    public EditMatchForm(Match form) {
        matchId = form.getMatchId();
        matchDate = getDateForm(form.getMatchDate());
        matchHour = timeFormat(form.getMatchDate().getHour());
        matchMaxPerson = form.getMaxPerson();
        matchCount = form.getMatchCount();
        gender = form.getMatchGender();
        matchGrade = form.getMatchGrade();
        isExit = isExit(form.getMatchDate());
    }

    private boolean isExit(LocalDateTime matchDate) {
        LocalDateTime now = LocalDateTime.now();
        return matchDate.isBefore(now);
    }

    private static String getDateForm(LocalDateTime matchDate) {
        return timeFormat(matchDate.getYear()) + "/" + timeFormat(matchDate.getMonthValue()) + "/" + timeFormat(matchDate.getDayOfMonth());
    }

    private static String timeFormat(int time) {
        return String.format("%02d", time);
    }
}
