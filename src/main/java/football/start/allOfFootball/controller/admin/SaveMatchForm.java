package football.start.allOfFootball.controller.admin;

import football.internal.database.enums.matchEnums.GenderEnum;
import football.internal.database.enums.matchEnums.MatchEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveMatchForm {

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate matchDate;

    private Integer matchHour;
    private Integer matchMaxPerson;
    private Integer matchCount;

    private GenderEnum gender;

    private MatchEnum matchGrade;

}
