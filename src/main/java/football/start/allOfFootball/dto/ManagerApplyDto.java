package football.start.allOfFootball.dto;

import football.common.enums.domainenum.LocationEnum;
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
