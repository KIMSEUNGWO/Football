package football.start.allOfFootball.dto.match;

import football.start.allOfFootball.enums.TeamEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
public class MatchCollection {

    private ScoreResult scoreResult;
    private List<MatchData> matchData;
    private Map<TeamEnum, List<TeamInfo>> teamInfo;
}
