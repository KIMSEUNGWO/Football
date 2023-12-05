package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.GradeEnum;
import jakarta.validation.constraints.NegativeOrZero;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditMatchForm {

    private String matchDate;

    private Integer matchHour;
    private Integer matchMaxPerson;
    private Integer matchCount;

    private GenderEnum gender;

    private Integer gradeLeft;
    private Integer gradeRight;

    public static EditMatchForm build(Match form) {
        return EditMatchForm.builder()
            .matchDate(getDateForm(form.getMatchDate()))
            .matchHour(form.getMatchHour())
            .matchMaxPerson(form.getMaxPerson())
            .matchCount(form.getMatchCount())
            .gender(form.getMatchGender())
            .gradeLeft(GradeEnum.getNumber(form.getMinGrade()))
            .gradeRight(GradeEnum.getNumber(form.getMaxGrade()))
            .build();
    }

    private static String getDateForm(LocalDate matchDate) {
        return timeFormat(matchDate.getYear()) + "/" + timeFormat(matchDate.getMonthValue()) + "/" + timeFormat(matchDate.getDayOfMonth());
    }

    private static String timeFormat(int time) {
        return String.format("%02d", time);
    }
}
