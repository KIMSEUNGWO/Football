package football.start.allOfFootball.common;

import football.common.enums.domainenum.TeamEnum;

import java.util.HashMap;
import java.util.Map;

import static football.common.consts.Constant.LOSE_SCORE;
import static football.common.consts.Constant.WIN_SCORE;

public class ScoreCalculator {

    private final Map<TeamEnum, Integer> total;

    public ScoreCalculator() {
        this.total = new HashMap<>();
    }


    public void put(TeamEnum leftTeam, int leftGoal, TeamEnum rightTeam, int rightGoal) {
        if (leftGoal == rightGoal) return;

        if (leftGoal > rightGoal) {
            total.put(leftTeam, total.getOrDefault(leftTeam, 0) + WIN_SCORE);
            total.put(rightTeam, total.getOrDefault(rightTeam, 0) + LOSE_SCORE);
        }

        if (leftGoal < rightGoal) {
            total.put(leftTeam, total.getOrDefault(leftTeam, 0) + LOSE_SCORE);
            total.put(rightTeam, total.getOrDefault(rightTeam, 0) + WIN_SCORE);
        }
    }

    public int getScore(TeamEnum teamEnum) {
        return total.getOrDefault(teamEnum, 0);
    }
}
