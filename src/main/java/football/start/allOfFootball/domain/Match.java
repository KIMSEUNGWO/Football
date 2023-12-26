package football.start.allOfFootball.domain;

import football.start.allOfFootball.controller.admin.EditMatchForm;
import football.start.allOfFootball.controller.admin.SaveMatchForm;
import football.start.allOfFootball.domain.score.Score;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.gradeEnums.MatchEnum;
import football.start.allOfFootball.enums.matchEnums.MatchStatus;
import jakarta.persistence.*;
import lombok.*;
import org.apache.ibatis.annotations.One;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "MATCH")
@SequenceGenerator(name = "SEQ_MATCH", sequenceName = "SEQ_MATCH_ID", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MATCH")
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "fieldID")
    private Field field;

    @ManyToOne
    @JoinColumn(name = "managerId")
    private Manager manager;

    private LocalDateTime matchDate; // 경기날짜

    private Integer matchCount; // 3파전 또는 2파전

    @Enumerated(EnumType.STRING)
    private GenderEnum matchGender;

    private Integer maxPerson; // 6 vs 6 일때 maxPerson = 6

    @Enumerated(EnumType.STRING)
    private MatchEnum matchGrade;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    // Not Columns

    @OneToMany(mappedBy = "match")
    private List<Orders> ordersList;

    @OneToMany(mappedBy = "match")
    private List<Score> scoreList;

    public static Match build(Field field, SaveMatchForm form) {
        return Match.builder()
            .field(field)
            .matchDate(getDate(form.getMatchDate(), form.getMatchHour()))
            .matchCount(form.getMatchCount())
            .matchGender(form.getGender())
            .maxPerson(form.getMatchMaxPerson())
            .matchGrade(form.getMatchGrade())
            .matchStatus(MatchStatus.모집중)
            .build();
    }

    private static LocalDateTime getDate(LocalDate matchDate, Integer matchHour) {
        int year = matchDate.getYear();
        int month = matchDate.getMonthValue();
        int day = matchDate.getDayOfMonth();
        return LocalDateTime.of(year, month, day, matchHour, 0);
    }

    public void setEditMatch(EditMatchForm editMatchForm) {
        matchDate = getLocalDate(editMatchForm.getMatchDate(), editMatchForm.getMatchHour());
        matchCount = editMatchForm.getMatchCount();
        matchGender = editMatchForm.getGender();
        maxPerson = editMatchForm.getMatchMaxPerson();
        matchGrade = editMatchForm.getMatchGrade();
    }

    private LocalDateTime getLocalDate(String matchDate, int matchHour) {
        String[] split = matchDate.split("/");
        return LocalDateTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), matchHour, 0);
    }
}
