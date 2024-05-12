package football.start.allOfFootball.dto;

import football.internal.database.enums.LocationEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManagerApplyDto {

    private String name;
    private LocationEnum region;
}
