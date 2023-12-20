package football.start.allOfFootball.dto.match;

import football.start.allOfFootball.enums.TeamEnum;
import football.start.allOfFootball.enums.gradeEnums.GradeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TeamInfo {

    private String profileImage;
    private String memberName;
    private GradeEnum memberGrade;
}
