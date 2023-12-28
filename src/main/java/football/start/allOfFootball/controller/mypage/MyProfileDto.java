package football.start.allOfFootball.controller.mypage;

import football.start.allOfFootball.domain.Social;
import football.start.allOfFootball.enums.SocialEnum;
import football.start.allOfFootball.enums.gradeEnums.GradeEnum;
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
