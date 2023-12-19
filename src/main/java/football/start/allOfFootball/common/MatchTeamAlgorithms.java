package football.start.allOfFootball.common;

import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.enums.TeamEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

public class MatchTeamAlgorithms {

    private final List<Orders> score;

    public MatchTeamAlgorithms(List<Orders> orders) {
        Collections.sort(orders, (o1, o2) -> o2.getMember().getGrade().getScore() - o1.getMember().getGrade().getScore());
        this.score = orders;
    }

    public Map<TeamEnum, List<Orders>> getResult(int teamCount) {
        List<TeamEnum> team = TeamEnum.getTeam(teamCount);
        int[] teamSum = new int[team.size()];

        Map<TeamEnum, List<Orders>> result = new HashMap<>();
        for (TeamEnum teamEnum : team) {
            result.put(teamEnum, new ArrayList<>());
        }

        for (Orders orders : score) {
            int minIndex = getMinValueIndex(teamSum);
            TeamEnum teamEnum = team.get(minIndex);

            result.get(teamEnum).add(orders);

            teamSum[minIndex] += orders.getMember().getGrade().getScore();
        }
        return result;
    }

    private int getMinValueIndex(int[] teamSum) {
        int index = 0;
        int value = teamSum[0];

        for (int i = 0; i < teamSum.length; i++) {
            if (teamSum[i] < value) {
                index = i;
                value = teamSum[i];
            }
        }
        return index;
    }


}
