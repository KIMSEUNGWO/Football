package football.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import football.common.enums.domainenum.LocationEnum;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchMatchRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate endDate;

    private List<LocationEnum> region;
    private String word;
}
