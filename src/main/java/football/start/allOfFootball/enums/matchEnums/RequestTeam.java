package football.start.allOfFootball.enums.matchEnums;

import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RequestTeam {

    private Long matchId;
    private List<TeamConfirm> team;
}
