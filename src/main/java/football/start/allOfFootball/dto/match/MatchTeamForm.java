package football.start.allOfFootball.dto.match;

import football.common.enums.domainenum.TeamEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchTeamForm {

    private TeamEnum teamEnum;
    private List<MatchGoalForm> goalList;
}
