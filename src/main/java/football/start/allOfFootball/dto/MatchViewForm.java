package football.start.allOfFootball.dto;

import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.gradeEnums.MatchEnum;
import football.start.allOfFootball.enums.groundEnums.ParkingEnum;
import football.start.allOfFootball.enums.groundEnums.ShowerEnum;
import football.start.allOfFootball.enums.groundEnums.ToiletEnum;
import football.start.allOfFootball.enums.matchEnums.MatchStatus;
import football.start.allOfFootball.formatter.DateFormatter;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchViewForm {

    private Long matchId;

    private List<FieldImage> fieldImages;

    private MatchEnum matchGrade;
    private GenderEnum matchGender;
    private String maxPersonAndMatchCount;

    private String fieldSize;
    private ParkingEnum fieldParking;
    private ShowerEnum fieldShower;
    private ToiletEnum fieldToilet;

    private String fieldInfo;

    private String matchDate;
    private String fieldTitle;
    private String fieldAddress;

    private MatchStatus matchStatus;

    public static MatchViewForm build(Match match) {
        return MatchViewForm.builder()
            .matchId(match.getMatchId())
            .fieldImages(match.getField().getFieldImages())
            .matchGrade(match.getMatchGrade())
            .matchGender(match.getMatchGender())
            .maxPersonAndMatchCount(getPersonAndCount(match.getMaxPerson(), match.getMatchCount()))
            .fieldSize(match.getField().getFieldSize())
            .fieldParking(match.getField().getFieldParking())
            .fieldShower(match.getField().getFieldShower())
            .fieldToilet(match.getField().getFieldToilet())
            .fieldInfo(match.getField().getFieldInformation())
            .matchDate(DateFormatter.dateFormat(match.getMatchDate(), match.getMatchHour()))
            .fieldTitle(match.getField().getFieldTitle())
            .fieldAddress(match.getField().getFieldAddress())
            .matchStatus(match.getMatchStatus())
            .build();
    }

    private static String getPersonAndCount(Integer maxPerson, Integer matchCount) {
        return String.format("%dm vs %dm %d파전", maxPerson, maxPerson, matchCount);
    }


}