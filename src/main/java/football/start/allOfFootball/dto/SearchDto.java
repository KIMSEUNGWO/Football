package football.start.allOfFootball.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.enums.gradeEnums.MatchEnum;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
