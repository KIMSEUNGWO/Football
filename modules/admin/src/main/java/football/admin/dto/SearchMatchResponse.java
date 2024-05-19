package football.admin.dto;

import football.common.domain.Match;
import football.common.enums.domainenum.LocationEnum;
import football.common.formatter.DateFormatter;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchMatchResponse {

    private Long matchId;
    private String matchDate;
    private String matchTime;
    private LocationEnum matchRegion;
    private String matchTitle;
    private String matchPerson;
    private String matchStatus;


    public SearchMatchResponse(Match match, Integer orderPerson) {
        matchId = match.getMatchId();
        matchDate = DateFormatter.format("yyyy-MM-dd", match.getMatchDate());
        matchTime = DateFormatter.format("HH:mm" , match.getMatchDate());
        matchRegion = match.getField().getFieldLocation();
        matchTitle = match.getField().getFieldTitle();
        matchPerson = orderPerson + " / " + (match.getMaxPerson() * match.getMatchCount());
        matchStatus = match.getMatchStatus().name();
    }

}
