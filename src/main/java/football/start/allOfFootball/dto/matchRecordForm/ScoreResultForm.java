package football.start.allOfFootball.dto.matchRecordForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScoreResultForm {

    private List<List<RecordForm>> playList;
}
