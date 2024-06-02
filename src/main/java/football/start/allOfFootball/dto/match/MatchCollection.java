package football.start.allOfFootball.dto.match;

import football.common.enums.domainenum.TeamEnum;

import java.util.List;
import java.util.Map;

public record MatchCollection(
                                ScoreResult scoreResult,
                                List<MatchData> matchData,
                                Map<TeamEnum, List<TeamInfo>> teamInfo) {

}
