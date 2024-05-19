package football.start.allOfFootball.dto.match;

import football.common.enums.gradeEnums.GradeEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchData {

    private GradeEnum grade;
    private Integer percent;
}
