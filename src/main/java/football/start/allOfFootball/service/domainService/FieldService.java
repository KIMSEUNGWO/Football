package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.common.file.FileService;
import football.start.allOfFootball.controller.admin.EditFieldForm;
import football.start.allOfFootball.controller.admin.SaveFieldForm;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.enums.FileUploadType;
import football.start.allOfFootball.repository.domainRepository.FieldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FieldService {

    private final FieldRepository fieldRepository;
    private final FileService fileService;

    public Optional<Field> findByField(Long fieldId) {
        return fieldRepository.findByField(fieldId);
    }

    public void saveField(SaveFieldForm saveGroundForm) {

        // field 객체 저장
        Field field = Field.build(saveGroundForm);
        fieldRepository.saveField(field);

        // fieldImage 파일로 저장 - DB 저장
        List<MultipartFile> images = saveGroundForm.getImages();
        int res = fileService.saveFile(images, field.getFieldId(), FileUploadType.FIELD_IMAGE);
    }
    public EditFieldForm findByFieldId(Long fieldId) {

        Optional<Field> findField = fieldRepository.findByField(fieldId);
        if (findField.isEmpty()) {
            return null;
        }
        Field field = findField.get();

        List<FieldImage> findFieldImage = fieldRepository.findByAllFieldImage(field);

        return EditFieldForm.build(field, findFieldImage);
    }

    public void editField(Field field, EditFieldForm form) {
        // field 수정된 내용 저장
        field.setEditField(form);

        String deleteImagesStr = form.getDeleteImages();
        String[] deleteImages = deleteImagesStr.split(",");
        for (String deleteImage : deleteImages) {
            // FieldImage DB에서 삭제
            fieldRepository.deleteByFieldImage(deleteImage);
            // 파일 삭제
            fileService.removeFile(deleteImage, FileUploadType.FIELD_IMAGE);
        }

        // 새로 추가될 사진 저장
        List<MultipartFile> saveImages = form.getImages();
        fileService.saveFile(saveImages, field.getFieldId(), FileUploadType.FIELD_IMAGE);
    }

}
