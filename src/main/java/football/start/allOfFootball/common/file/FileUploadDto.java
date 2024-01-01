package football.start.allOfFootball.common.file;

import football.start.allOfFootball.dto.ImageParent;
import football.start.allOfFootball.enums.FileUploadType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class FileUploadDto {

    private ImageParent parent;
    private String imageUploadName;
    private String imageStoreName;
    private FileUploadType type;



}
