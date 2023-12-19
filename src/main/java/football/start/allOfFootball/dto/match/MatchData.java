package football.start.allOfFootball.dto.match;

import football.start.allOfFootball.enums.gradeEnums.GradeEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchData {

    private GradeEnum grade;
    private Integer percent;
}
