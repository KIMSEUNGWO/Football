package football.start.allOfFootball.dto.match;

import football.internal.database.enums.matchEnums.GradeEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchData {

    private GradeEnum grade;
    private Integer percent;
}
