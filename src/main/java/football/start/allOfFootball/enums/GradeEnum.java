package football.start.allOfFootball.enums;

import java.util.Arrays;
import java.util.Optional;

public enum GradeEnum {

    L0("루키"),
    L1("스타터"),
    L2("비기너"),
    L3("아마추어"),
    L4("세미프로"),
    L5("프로");

    private String grade;

    GradeEnum(String grade) {
        this.grade = grade;
    }

    public static int getNumber(GradeEnum grade) {
        String number = grade.name().substring(1);
        return Integer.parseInt(number);
    }

    public static GradeEnum getEnum(int number) {
        GradeEnum[] values = GradeEnum.values();
        for (GradeEnum value : values) {
            int number1 = getNumber(value);
            if (number == number1) {
                return value;
            }
        }
        return null;
    }

}
