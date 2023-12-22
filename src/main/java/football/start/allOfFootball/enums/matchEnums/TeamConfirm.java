package football.start.allOfFootball.enums.matchEnums;

import football.start.allOfFootball.enums.TeamEnum;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamConfirm {

    private TeamEnum teamColor;
    private List<Long> player;
}