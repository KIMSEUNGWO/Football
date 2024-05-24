package football.common.domain.score;

import football.common.enums.domainenum.TeamEnum;
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
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "SCORE_ID")
    private Score score;

    @Enumerated(EnumType.STRING)
    private TeamEnum teamEnum;

    // Not Columns
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Goal> goalList;
}
