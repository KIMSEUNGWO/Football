package football.start.allOfFootball.dto.matchRecordForm;

import football.internal.database.enums.matchEnums.TeamEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecordForm {

    private TeamEnum team;
    private List<Player> goalList;
}
