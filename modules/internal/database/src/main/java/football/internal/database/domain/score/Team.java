package football.internal.database.domain.score;

import football.start.allOfFootball.enums.TeamEnum;
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
@Table(name = "TEAM")
@SequenceGenerator(name = "SEQ_TEAM", sequenceName = "SEQ_TEAM_ID", allocationSize = 1)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TEAM")
    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "scoreId")
    private Score score;

    @Enumerated(EnumType.STRING)
    private TeamEnum teamEnum;

    // Not Columns
    @OneToMany(mappedBy = "team")
    private List<Goal> goalList;
}
