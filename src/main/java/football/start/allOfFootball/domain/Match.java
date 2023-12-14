package football.start.allOfFootball.domain;

import football.start.allOfFootball.controller.admin.EditMatchForm;
import football.start.allOfFootball.controller.admin.SaveMatchForm;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.gradeEnums.MatchEnum;
import football.start.allOfFootball.enums.matchEnums.MatchStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    private LocalDate matchDate; // 경기날짜
    private Integer matchHour; // 경기시작시간

    private Integer matchCount; // 3파전 또는 2파전

    @Enumerated(EnumType.STRING)
    private GenderEnum matchGender;

    private Integer maxPerson; // 6 vs 6 일때 maxPerson = 6

    @Enumerated(EnumType.STRING)
    private MatchEnum matchGrade;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    @OneToMany(mappedBy = "match")
    private List<Orders> ordersList;

    public static Match build(Field field, SaveMatchForm form) {
        return Match.builder()
            .field(field)
            .matchDate(form.getMatchDate())
            .matchHour(form.getMatchHour())
            .matchCount(form.getMatchCount())
            .matchGender(form.getGender())
            .maxPerson(form.getMatchMaxPerson())
            .matchGrade(form.getMatchGrade())
            .matchStatus(MatchStatus.모집중)
            .build();
    }

    public void setEditMatch(EditMatchForm editMatchForm) {
        matchDate = getLocalDate(editMatchForm.getMatchDate());
        matchHour = editMatchForm.getMatchHour();
        matchCount = editMatchForm.getMatchCount();
        matchGender = editMatchForm.getGender();
        maxPerson = editMatchForm.getMatchMaxPerson();
        matchGrade = editMatchForm.getMatchGrade();
    }

    private LocalDate getLocalDate(String matchDate) {
        String[] split = matchDate.split("/");
        return LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }
}
