package football.start.allOfFootball.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.catalina.LifecycleState;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
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
}
