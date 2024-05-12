package football.start.allOfFootball.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import football.internal.database.enums.matchEnums.GenderEnum;
import football.internal.database.enums.LocationEnum;
import football.internal.database.enums.matchEnums.MatchEnum;
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
