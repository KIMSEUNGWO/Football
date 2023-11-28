package football.start.allOfFootball.common.file;

import football.start.allOfFootball.enums.FileUploadType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileUploadDto {

    private Long id;
    private String imageUploadName;
    private String imageStoreName;
    private FileUploadType type;



}
