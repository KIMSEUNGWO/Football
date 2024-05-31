package football.start.allOfFootball.controller.mypage;

import football.common.domain.Social;
import football.common.enums.gradeEnums.GradeEnum;

public record MyProfileDto(String profileImage,
                           String name,
                           Social social,
                           String email,
                           String score,
                           String rank,
                           GradeEnum grade,
                           MatchScore matchScore,
                           String cash) {

}
