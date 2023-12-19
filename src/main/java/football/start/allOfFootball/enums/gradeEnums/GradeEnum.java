package football.start.allOfFootball.enums.gradeEnums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GradeEnum {

    루키(1),
    스타터(2),
    비기너(3),
    아마추어(4),
    세미프로(5),
    프로(6);

    private int score;


}
