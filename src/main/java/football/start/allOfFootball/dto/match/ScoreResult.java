package football.start.allOfFootball.dto.match;

import football.common.enums.domainenum.TeamEnum;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ScoreResult {

    private TeamEnum myTeam;
    private List<MatchScoreForm> scoreList;
}
