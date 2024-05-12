package football.start.allOfFootball.dto;

import football.internal.database.domain.Match;
import football.internal.database.domain.Member;
import football.start.allOfFootball.enums.gradeEnums.GradeEnum;
import football.common.formatter.DateFormatter;
import football.common.formatter.NumberFormatter;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@ToString
public class MainSideInfoForm {

    private String name;
    private String rank;
    private String score;
    private GradeEnum grade;

    private String cash;

    private Map<String, List<MyScheduleForm>> schedule;

    public MainSideInfoForm() {
        schedule = new ConcurrentHashMap<>();
    }

    public void setMyInfo(Member member, Long myRank) {
        name = member.getMemberName();
        rank = NumberFormatter.format(myRank);
        score = NumberFormatter.format(member.getMemberScore());
        grade = member.getGrade();
        cash = NumberFormatter.format(member.getMemberCash());
    }

    public void setMySchedule(Match match) {
        String date = DateFormatter.format("yyyy년 MM월 dd일", match.getMatchDate());
        MyScheduleForm myScheduleForm = MyScheduleForm.build(match);

        if (!schedule.containsKey(date)) {
            schedule.put(date, new ArrayList<>());
        }
        List<MyScheduleForm> list = schedule.get(date);
        list.add(myScheduleForm);
    }

}
