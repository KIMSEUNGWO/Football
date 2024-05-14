package football.start.allOfFootball.controller.mypage;

import football.common.domain.Social;
import football.common.enums.gradeEnums.GradeEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyProfileDto {

    private String profileImage;
    private String name;
    private Social social;
    private String email;

    private String score;
    private String rank;
    private GradeEnum grade;
    private MatchScore matchScore;

    private String cash;
}
