package football.start.allOfFootball.controller.admin;

import football.internal.database.domain.Field;
import football.internal.database.domain.FieldImage;
import football.internal.database.enums.LocationEnum;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchFieldForm {

    private Long fieldId;
    private FieldImage fieldImage;
    private LocationEnum fieldRegion;
    private String fieldTitle;
    private String fieldAddress;

    public ViewMatchFieldForm(Field field) {
        fieldId = field.getFieldId();
        fieldImage = field.getFieldImages().get(0);
        fieldRegion = field.getFieldLocation();
        fieldTitle = field.getFieldTitle();
        fieldAddress = field.getFieldAddress();
    }
}
