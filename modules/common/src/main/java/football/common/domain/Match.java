package football.common.domain;

import football.common.domain.score.Score;
import football.common.dto.match.EditMatchRequest;
import football.common.dto.match.SaveMatchRequest;
import football.common.enums.gradeEnums.MatchEnum;
import football.common.enums.matchenum.GenderEnum;
import football.common.enums.matchenum.MatchStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static football.common.consts.Constant.PRICE;

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

    private int price;
    // Not Columns

    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
    private List<Orders> ordersList;

    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
    private List<Score> scoreList;

    public static Match build(Field field, SaveMatchRequest form) {
        return Match.builder()
            .field(field)
            .matchDate(LocalDateTime.of(form.getMatchDate(), LocalTime.of(form.getMatchHour(), 0)))
            .matchCount(form.getMatchCount())
            .matchGender(form.getGender())
            .maxPerson(form.getMatchMaxPerson())
            .matchGrade(form.getMatchGrade())
            .matchStatus(MatchStatus.모집중)
            .price(PRICE)
            .build();
    }

    public void setEditMatch(EditMatchRequest editMatchForm) {
        matchDate = getLocalDate(editMatchForm.getMatchDate(), editMatchForm.getMatchHour());
        matchCount = editMatchForm.getMatchCount();
        matchGender = editMatchForm.getGender();
        maxPerson = editMatchForm.getMatchMaxPerson();
        matchGrade = editMatchForm.getMatchGrade();
    }

    private LocalDateTime getLocalDate(String matchDate, String matchHourStr) {
        int matchHour = Integer.parseInt(matchHourStr);
        String[] split = matchDate.split("/");
        return LocalDateTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), matchHour, 0);
    }
}
