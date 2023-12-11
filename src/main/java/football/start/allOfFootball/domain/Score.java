package football.start.allOfFootball.domain;

import football.start.allOfFootball.enums.ResultEnum;
import football.start.allOfFootball.enums.TeamEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "SCORE")
@SequenceGenerator(name = "SEQ_SCORE", sequenceName = "SEQ_SCORE_ID", allocationSize = 1)
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCORE")
    private Long scoreId;

    @ManyToOne
    @JoinColumn(name = "matchResultId")
    private MatchResult matchResult;

    @Enumerated(EnumType.STRING)
    private TeamEnum xTeam;
    private int xScore;

    @Enumerated(EnumType.STRING)
    private TeamEnum yTeam;
    private int yScore;

    @Enumerated(EnumType.STRING)
    private TeamEnum winTeam;
}
