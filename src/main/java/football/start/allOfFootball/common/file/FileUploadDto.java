package football.start.allOfFootball.common.file;

import football.internal.database.domain.ImageParent;
import football.start.allOfFootball.enums.FileUploadType;
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
