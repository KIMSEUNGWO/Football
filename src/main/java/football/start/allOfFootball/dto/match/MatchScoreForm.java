package football.start.allOfFootball.dto.match;

import football.common.enums.domainenum.TeamEnum;
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

    private TeamEnum leftTeam;
    private int leftScore;
    private TeamEnum rightTeam;
    private int rightScore;
    private ResultEnum resultEnum;
}
