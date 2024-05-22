package football.admin.service;

import football.admin.exception.NotExistsFieldException;
import football.admin.repository.FieldRepository;
import football.common.dto.field.EditFieldRequest;
import football.file.service.FileService;
import football.common.dto.field.SaveFieldRequest;
import football.common.domain.Field;
import football.common.domain.FieldImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static football.file.enums.FileUploadType.*;


@Service
@RequiredArgsConstructor
public class AdminFieldService {

    private final FieldRepository fieldRepository;
    private final FileService fileService;

    public Field findByField(Long fieldId) throws NotExistsFieldException {
        Optional<Field> findField = fieldRepository.findByField(fieldId);
        return findField.orElseThrow(NotExistsFieldException::new);
    }

    @Transactional
    public void saveField(SaveFieldRequest saveGroundForm) {

        // field 객체 저장
        Field field = new Field(saveGroundForm);
        fieldRepository.saveField(field);

        // fieldImage 파일로 저장 - DB 저장
        List<MultipartFile> images = saveGroundForm.getImages();
        fileService.saveFile(images, field, FIELD_IMAGE);
    }

    public void editField(Field field, EditFieldRequest form) {
        // field 수정된 내용 저장
        field.setEditField(form);

        String deleteImagesStr = form.getDeleteImages();
        String[] deleteImages = deleteImagesStr.split(",");
        for (String deleteImage : deleteImages) {
            // FieldImage DB에서 삭제
            fieldRepository.deleteByFieldImage(deleteImage);
            // 파일 삭제
            fileService.removeFile(deleteImage, FIELD_IMAGE);
        }

        // 새로 추가될 사진 저장
        List<MultipartFile> saveImages = form.getImages();
        fileService.saveFile(saveImages, field, FIELD_IMAGE);
    }

    public EditFieldRequest getEditFieldRequest(Field field) {
        List<FieldImage> findFieldImage = fieldRepository.findByAllFieldImage(field);
        return new EditFieldRequest(field, findFieldImage);
    }
}
