package football.admin.dto;

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
public class SearchFieldResponse {

    private Long fieldId;
    private LocationEnum region;
    private String fieldName;
    private String fieldAddress;
    private String createDate;

    public SearchFieldResponse(Field form) {
        fieldId = form.getFieldId();
        region = form.getFieldLocation();
        fieldName = form.getFieldTitle();
        fieldAddress = form.getFieldAddress();
        createDate = DateFormatter.format("yyyy-MM-dd", form.getCreateDate());
    }
}
