package football.common.enums.domainenum;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum TeamEnum {

    RED("red"),
    BLUE("blue"),
    YELLOW("yellow");

    private final String className;

    public static List<TeamEnum> getTeam(int teamCount) {
        if (teamCount == 3) {
            return Arrays.asList(RED, BLUE, YELLOW);
        }
        return Arrays.asList(RED, BLUE);
    }

    TeamEnum(String className) {
        this.className = className;
    }
}
