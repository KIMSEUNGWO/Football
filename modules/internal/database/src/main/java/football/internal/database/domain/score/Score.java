package football.internal.database.domain.score;

import football.internal.database.domain.Match;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCORE")
@SequenceGenerator(name = "SEQ_SCORE", sequenceName = "SEQ_SCORE_ID", allocationSize = 1)
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCORE")
    private Long scoreId;

    @ManyToOne
    @JoinColumn(name = "matchId")
    private Match match;

    // Not Columns

    @OneToMany(mappedBy = "score")
    private List<Team> teamList;


}
