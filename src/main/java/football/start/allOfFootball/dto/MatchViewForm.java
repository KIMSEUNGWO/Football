package football.start.allOfFootball.dto;

import football.common.domain.FieldImage;
import football.common.enums.matchenum.*;
import football.common.enums.gradeEnums.MatchEnum;
import football.common.enums.groundEnums.*;

import java.util.List;

public record MatchViewForm(
                            Long matchId,
                            List<FieldImage> fieldImages,
                            MatchEnum matchGrade,
                            GenderEnum matchGender,
                            String maxPersonAndMatchCount,
                            String managerName,
                            String fieldSize,
                            ParkingEnum fieldParking,
                            ShowerEnum fieldShower,
                            ToiletEnum fieldToilet,
                            String fieldInfo,
                            String matchDate,
                            String fieldTitle,
                            String fieldAddress,
                            String price,
                            MatchStatus matchStatus) {
}
