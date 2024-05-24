package football.common.domain.score;

import football.common.domain.Match;
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
public class Score {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @ManyToOne
    @JoinColumn(name = "MATCH_ID")
    private Match match;

    // Not Columns

    @OneToMany(mappedBy = "score", fetch = FetchType.LAZY)
    private List<Team> teamList;


}
