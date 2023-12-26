package football.start.allOfFootball.common;

import football.start.allOfFootball.dto.match.MatchTeamForm;
import football.start.allOfFootball.enums.TeamEnum;

import java.util.HashMap;
import java.util.Map;

public class ScoreCalculator {

    private final Map<TeamEnum, Integer> total;

    public ScoreCalculator() {
        this.total = new HashMap<>();
    }


    public void put(TeamEnum leftTeam, int leftGoal, TeamEnum rightTeam, int rightGoal) {
        System.out.println(leftGoal + " : " + rightGoal);
        if (leftGoal == rightGoal) return;

        if (leftGoal > rightGoal) {
            total.put(leftTeam, total.getOrDefault(leftTeam, 0) + 5);
            total.put(rightTeam, total.getOrDefault(rightTeam, 0) - 5);
            System.out.println(leftTeam.name() + " +5");
            System.out.println(rightTeam.name() + " -5");
        }

        if (leftGoal < rightGoal) {
            total.put(leftTeam, total.getOrDefault(leftTeam, 0) - 5);
            total.put(rightTeam, total.getOrDefault(rightTeam, 0) + 5);
            System.out.println(leftTeam.name() + " -5");
            System.out.println(rightTeam.name() + " +5");
        }
    }

    public int getScore(TeamEnum teamEnum) {
        return total.getOrDefault(teamEnum, 0);
    }
}
