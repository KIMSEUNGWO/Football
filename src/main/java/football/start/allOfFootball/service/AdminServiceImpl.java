package football.start.allOfFootball.service;

import football.start.allOfFootball.common.file.FileService;
import football.start.allOfFootball.common.file.FileUploadDto;
import football.start.allOfFootball.controller.admin.SaveGroundForm;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.enums.FileUploadType;
import football.start.allOfFootball.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final FileService fileService;


    @Override
    public void saveField(SaveGroundForm saveGroundForm) {

        // field 객체 저장
        Field field = Field.build(saveGroundForm);
        adminRepository.saveField(field);

        // fieldImage 파일로 저장 - DB 저장
        List<MultipartFile> images = saveGroundForm.getImages();
        int res = fileService.saveFile(images, field.getFieldId(), FileUploadType.FIELD_IMAGE);

    }
}
