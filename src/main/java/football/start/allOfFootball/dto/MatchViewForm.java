package football.start.allOfFootball.dto;

import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.domain.Manager;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.gradeEnums.MatchEnum;
import football.start.allOfFootball.enums.groundEnums.ParkingEnum;
import football.start.allOfFootball.enums.groundEnums.ShowerEnum;
import football.start.allOfFootball.enums.groundEnums.ToiletEnum;
import football.start.allOfFootball.enums.matchEnums.MatchStatus;
import football.common.formatter.DateFormatter;
import football.common.formatter.NumberFormatter;
import lombok.*;

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
    private String managerName;

    private String fieldSize;
    private ParkingEnum fieldParking;
    private ShowerEnum fieldShower;
    private ToiletEnum fieldToilet;

    private String fieldInfo;

    private String matchDate;
    private String fieldTitle;
    private String fieldAddress;

    private String price;
    private MatchStatus matchStatus;

    public MatchViewForm(Match match) {
        matchId = match.getMatchId();
        fieldImages = match.getField().getFieldImages();
        matchGrade = match.getMatchGrade();
        matchGender = match.getMatchGender();
        maxPersonAndMatchCount = getPersonAndCount(match.getMaxPerson(), match.getMatchCount());
        managerName = getManager(match.getManager());
        fieldSize = match.getField().getFieldSize();
        fieldParking = match.getField().getFieldParking();
        fieldShower = match.getField().getFieldShower();
        fieldToilet = match.getField().getFieldToilet();
        fieldInfo = match.getField().getFieldInformation();
        matchDate = DateFormatter.format("M월 d일 EEEE HH:mm", match.getMatchDate());
        fieldTitle = match.getField().getFieldTitle();
        fieldAddress = match.getField().getFieldAddress();
        price = NumberFormatter.format(match.getPrice());
        matchStatus = match.getMatchStatus();
    }

    private static String getManager(Manager manager) {
        if (manager == null) {
            return null;
        }
        return manager.getMember().getMemberName();
    }

    private static String getPersonAndCount(Integer maxPerson, Integer matchCount) {
        return String.format("%dm vs %dm %d파전", maxPerson, maxPerson, matchCount);
    }


}
