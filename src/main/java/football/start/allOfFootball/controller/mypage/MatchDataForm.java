package football.start.allOfFootball.controller.mypage;

import football.common.enums.matchenum.MatchStatus;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchDataForm {

    private Long matchId;
    private String matchTime; // 18:00
    private String maxPersonAndCount; // 6 vs 6 3파전
    private String fieldTitle;
    private String nowPerson; // 7 / 18
    private MatchStatus matchStatus;
}
