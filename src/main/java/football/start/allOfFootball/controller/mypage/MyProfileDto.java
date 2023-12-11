package football.start.allOfFootball.controller.mypage;

import football.start.allOfFootball.enums.gradeEnums.GradeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyProfileDto {

    private String profileImage;
    private String name;
    private String social;
    private String email;

    private String score;
    private String rank;
    private GradeEnum grade;
    private MatchScore matchScore;
}
