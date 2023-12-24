package football.start.allOfFootball.enums.matchEnums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MatchStatus {

    모집중("신청하기", "on"),
    마감임박("마감임박", "on-2"),
    마감("마감", "off"),
    경기시작전("경기시작전", "off"),
    경기중("경기중", "off"),
    기록중("점수기록중", "off"),
    종료("종료","off"),
    취소("경기취소", "off");

    private String status;
    private String className;

}
