package football.common.dto.match;

import football.common.enums.gradeEnums.MatchEnum;
import football.common.enums.matchenum.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class MatchForm {

    private Integer matchMaxPerson;
    private Integer matchCount;
    private GenderEnum gender;
    private MatchEnum matchGrade;
}
