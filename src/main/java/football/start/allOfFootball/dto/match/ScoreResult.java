package football.start.allOfFootball.dto.match;

import football.start.allOfFootball.enums.TeamEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreResult {

    private TeamEnum myTeam;
    private List<MatchScoreForm> scoreList;
}
