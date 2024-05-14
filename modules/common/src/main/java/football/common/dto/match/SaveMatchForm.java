package football.common.dto.match;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveMatchForm extends MatchForm {

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate matchDate;
    private Integer matchHour;

}
