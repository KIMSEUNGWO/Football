package football.start.allOfFootball.common.file;


import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.enums.FileUploadType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static football.start.allOfFootball.enums.FileUploadType.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class TypeConvert {

    private final FileRepository fileRepository;

    public int saveFile(FileUploadDto fileUploadDto) {
        FileUploadType type = fileUploadDto.getType();

        if (type.equals(FIELD_IMAGE)) {
            return fileRepository.saveFieldImage(fileUploadDto);
        }
        if (type.equals(PROFILE)) {
            return fileRepository.saveProfile(fileUploadDto);
        }
        log.error("TypeConvert TypeMissMatch error = {}", type.name());
        return 0;
    }

}
