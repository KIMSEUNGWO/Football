package football.start.allOfFootball.dto.match;

import football.internal.database.enums.matchEnums.GradeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class TeamInfo {

    private Long orderId;
    private String profileImage;
    private String memberName;
    private GradeEnum memberGrade;
}
