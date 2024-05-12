package football.start.allOfFootball.controller.mypage;

import football.start.allOfFootball.dto.match.TeamInfo;
import football.internal.database.enums.matchEnums.TeamEnum;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ManagerDataForm {

    private MatchDataForm topInfo;
    private Map<TeamEnum, List<TeamInfo>> teamInfo;
    private boolean isMatchPlaying;
    private boolean isRecordScore;
}
