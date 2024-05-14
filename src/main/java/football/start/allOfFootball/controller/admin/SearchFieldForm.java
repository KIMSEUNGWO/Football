package football.start.allOfFootball.controller.admin;

import football.common.domain.Field;
import football.common.enums.domainenum.LocationEnum;
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
