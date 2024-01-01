package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.enums.LocationEnum;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SearchFieldForm {

    private Long fieldId;
    private LocationEnum region;
    private String fieldName;

    public SearchFieldForm(Field form) {
        fieldId = form.getFieldId();
        region = form.getFieldLocation();
        fieldName = form.getFieldTitle();
    }
}
