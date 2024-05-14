package football.common.dto.match;

import football.common.domain.Match;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditMatchForm extends MatchForm {

    private Long matchId;
    private String matchDate;

    private String matchHour;

    private boolean isExit;

    public EditMatchForm(Match form) {
        super(form.getMaxPerson(), form.getMatchCount(), form.getMatchGender(), form.getMatchGrade());
        matchId = form.getMatchId();
        matchDate = getDateForm(form.getMatchDate());
        matchHour = timeFormat(form.getMatchDate().getHour());
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
