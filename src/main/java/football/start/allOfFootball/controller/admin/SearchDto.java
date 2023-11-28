package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.enums.LocationEnum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchDto {

    private List<LocationEnum> region;
    private String word;

}
