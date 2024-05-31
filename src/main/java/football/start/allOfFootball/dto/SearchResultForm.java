package football.start.allOfFootball.dto;

import football.common.enums.matchenum.*;
import football.common.enums.domainenum.LocationEnum;
import football.common.enums.gradeEnums.MatchEnum;

public record SearchResultForm(Long matchId,
                               String matchHour,
                               LocationEnum matchRegion,
                               String matchTitle,
                               GenderEnum matchGender,
                               MatchEnum matchGrade,
                               String matchMaxPerson,
                               MatchStatus matchStatus) {
}
