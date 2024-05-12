package football.internal.database.enums.matchEnums;

import java.util.Arrays;
import java.util.List;

import static football.internal.database.enums.matchEnums.GradeEnum.*;

public enum MatchEnum {

    전체(Arrays.asList(루키, 스타터, 비기너, 아마추어, 세미프로, 프로), "제한없음"),

    초급(Arrays.asList(루키, 스타터, 비기너), 루키 + " ~ " + 비기너),
    중급(Arrays.asList(비기너, 아마추어, 세미프로), 비기너 + " ~ " + 세미프로),
    전문가(Arrays.asList(아마추어, 세미프로, 프로), 아마추어 + " ~ " + 프로);

    private List<GradeEnum> gradeList;
    public String matchInfo;

    MatchEnum(List<GradeEnum> gradeList, String matchInfo) {
        this.gradeList = gradeList;
        this.matchInfo = matchInfo;
    }

    public List<GradeEnum> getGradeList() {
        return gradeList;
    }

    public String getMatchInfo() {
        return matchInfo;
    }
}
