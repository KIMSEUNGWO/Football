package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.enums.LocationEnum;
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

    public static ViewMatchFieldForm build(Field field) {
        return ViewMatchFieldForm.builder()
            .fieldId(field.getFieldId())
            .fieldImage(field.getFieldImages().get(0))
            .fieldRegion(field.getFieldLocation())
            .fieldTitle(field.getFieldTitle())
            .fieldAddress(field.getFieldAddress())
            .build();
    }
}
