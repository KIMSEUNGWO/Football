package football.common.dto.field;

import football.common.domain.Field;
import football.common.domain.FieldImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditFieldRequest extends FieldForm{

    private List<MultipartFile> images; // 새로 추가될 사진목록
    private List<FieldImage> savedImages; // 이미 DB에 저장되어있는 사진목록
    private String deleteImages;
    private String fieldDetails;

    public EditFieldRequest(Field field, List<FieldImage> imageList) {
        super(field);
        savedImages = imageList;
        fieldDetails = field.getFieldInformation();
    }
}
