package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.enums.GenderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;

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

    private Integer gradeLeft;
    private Integer gradeRight;

}
