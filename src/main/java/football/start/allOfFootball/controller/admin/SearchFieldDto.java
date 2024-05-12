package football.start.allOfFootball.controller.admin;

import football.internal.database.enums.LocationEnum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchFieldDto {

    private List<LocationEnum> region;
    private String word;

}
