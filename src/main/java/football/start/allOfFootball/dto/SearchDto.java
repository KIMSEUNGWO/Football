package football.start.allOfFootball.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import football.common.enums.matchenum.GenderEnum;
import football.common.enums.domainenum.LocationEnum;
import football.common.enums.gradeEnums.MatchEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyy/MM/dd")
    private LocalDate matchDate;
    private LocationEnum region;
    private GenderEnum gender;
    private MatchEnum grade;
    private String word;

}
