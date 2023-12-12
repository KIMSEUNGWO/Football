package football.start.allOfFootball.dto;

import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.enums.gradeEnums.GradeEnum;
import football.start.allOfFootball.formatter.DateFormatter;
import football.start.allOfFootball.formatter.NumberFormatter;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
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

    public void setMyInfo(Member member) {
        name = member.getMemberName();
        rank = "구현중";
        score = NumberFormatter.format(member.getMemberScore());
        grade = member.getGrade();
        cash = NumberFormatter.format(member.getMemberCash());
    }

    public void setMySchedule(Match match) {
        String date = DateFormatter.sideMenuDateForm(match.getMatchDate());
        MyScheduleForm myScheduleForm = MyScheduleForm.build(match);

        if (!schedule.containsKey(date)) {
            schedule.put(date, new ArrayList<>());
        }
        List<MyScheduleForm> list = schedule.get(date);
        list.add(myScheduleForm);
    }

}
