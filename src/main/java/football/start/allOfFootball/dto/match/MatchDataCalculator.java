package football.start.allOfFootball.dto.match;

import football.internal.database.enums.matchEnums.GradeEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MatchDataCalculator {

    private final Map<GradeEnum, Integer> total;
    private final int nowPerson;

    public MatchDataCalculator(int nowPerson) {
        this.nowPerson = nowPerson;
        this.total = new ConcurrentHashMap<>();
    }

    public void put(GradeEnum gradeEnum) {
        total.put(gradeEnum, total.getOrDefault(gradeEnum, 0) + 1);
    }

    public int calculate(GradeEnum gradeEnum) {
        int count = total.getOrDefault(gradeEnum, 0);

        double temp = ( (count * 1.0) / (nowPerson * 1.0) ) * 100;
        long round = Math.round(temp);
        return (int) round;

    }
}
