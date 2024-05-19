package football.common.enums.gradeEnums;

import lombok.Getter;

@Getter
public enum GradeEnum {

    루키(1),
    스타터(2),
    비기너(3),
    아마추어(4),
    세미프로(5),
    프로(6);

    private int score;

    GradeEnum(int score) {
        this.score = score;
    }
}
