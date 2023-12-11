package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "MATCH_RESULT")
@SequenceGenerator(name = "SEQ_MATCH_RESULT", sequenceName = "SEQ_MATCH_RESULT_ID", allocationSize = 1)
public class MatchResult {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MATCH_RESULT")
    private Long matchResultId;

    @OneToOne
    @JoinColumn(name = "matchId")
    private Match match;

}
