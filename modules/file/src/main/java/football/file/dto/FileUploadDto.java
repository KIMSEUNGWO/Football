package football.file.dto;

import football.common.domain.ImageParent;
import football.file.enums.FileUploadType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FileUploadDto {

    private ImageParent parent;
    private String imageUploadName;
    private String imageStoreName;
    private FileUploadType type;



}
