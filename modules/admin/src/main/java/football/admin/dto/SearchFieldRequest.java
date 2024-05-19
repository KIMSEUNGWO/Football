package football.admin.dto;

import football.common.enums.domainenum.LocationEnum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchFieldRequest {

    private List<LocationEnum> region;
    private String word;

}
