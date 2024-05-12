package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.enums.LocationEnum;
import football.common.formatter.DateFormatter;
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
    private String fieldAddress;
    private String createDate;

    public SearchFieldForm(Field form) {
        fieldId = form.getFieldId();
        region = form.getFieldLocation();
        fieldName = form.getFieldTitle();
        fieldAddress = form.getFieldAddress();
        createDate = DateFormatter.format("yyyy-MM-dd", form.getCreateDate());
    }
}
