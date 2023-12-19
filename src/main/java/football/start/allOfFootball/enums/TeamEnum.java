package football.start.allOfFootball.enums;

import org.apache.catalina.LifecycleState;

import java.util.Arrays;
import java.util.List;

public enum TeamEnum {

    RED,
    BLUE,
    YELLOW;

    public static List<TeamEnum> getTeam(int teamCount) {
        if (teamCount == 3) {
            return Arrays.asList(RED, BLUE, YELLOW);
        }
        return Arrays.asList(RED, BLUE);
    }
}
