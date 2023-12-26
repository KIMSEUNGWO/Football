package football.start.allOfFootball.dto.match;

import football.start.allOfFootball.enums.matchEnums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchScoreForm {

    private MatchTeamForm leftTeam;
    private MatchTeamForm rightTeam;
    private ResultEnum resultEnum;
}
