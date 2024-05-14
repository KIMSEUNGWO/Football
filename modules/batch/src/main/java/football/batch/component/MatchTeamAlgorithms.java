package football.batch.component;

import football.common.domain.Orders;
import football.common.enums.domainenum.TeamEnum;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MatchTeamAlgorithms {


    public Map<TeamEnum, List<Orders>> getResult(List<Orders> score, int teamCount) {
        score.sort((o1, o2) -> o2.getMember().getGrade().getScore() - o1.getMember().getGrade().getScore());

        List<TeamEnum> team = TeamEnum.getTeam(teamCount);
        int[] teamSum = new int[team.size()];

        Map<TeamEnum, List<Orders>> result = new HashMap<>(team.size());
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
