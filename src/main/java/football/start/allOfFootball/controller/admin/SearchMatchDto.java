package football.start.allOfFootball.controller.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import football.internal.database.enums.LocationEnum;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchMatchDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate endDate;

    private List<LocationEnum> region;
    private String word;
}
