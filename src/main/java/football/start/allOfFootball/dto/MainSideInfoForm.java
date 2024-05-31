package football.start.allOfFootball.dto;

import football.common.domain.Match;
import football.common.domain.Member;
import football.common.domain.Orders;
import football.common.enums.gradeEnums.GradeEnum;
import football.common.formatter.DateFormatter;
import football.common.formatter.NumberFormatter;
import lombok.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Getter
@ToString
public class MainSideInfoForm {

    private final String name;
    private final String rank;
    private final String score;
    private final GradeEnum grade;

    private String cash;

    private final Map<String, List<MyScheduleForm>> schedule;

    public MainSideInfoForm(Member member, Long myRank) {
        name = member.getMemberName();
        rank = NumberFormatter.format(myRank);
        score = NumberFormatter.format(member.getMemberScore());
        grade = member.getGrade();
        cash = NumberFormatter.format(member.getMemberCash());
        schedule = new TreeMap<>((o1, o2) -> DateFormatter.compare(o1, o2, "yyyy년 MM월 dd일"));
    }

    public void setMySchedule(Match match) {
        String date = DateFormatter.format("yyyy년 MM월 dd일", match.getMatchDate());

        if (!schedule.containsKey(date)) schedule.put(date, new ArrayList<>());

        schedule.get(date)
                .add(new MyScheduleForm(
                            match.getMatchId(),
                            DateFormatter.format("HH:mm",match.getMatchDate()),
                            match.getField().getFieldLocation(),
                            match.getField().getFieldTitle()
                            ));
    }

}
