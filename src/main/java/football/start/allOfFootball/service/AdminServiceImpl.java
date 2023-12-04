package football.start.allOfFootball.service;

import football.start.allOfFootball.common.file.FileService;
import football.start.allOfFootball.controller.admin.*;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.FileUploadType;
import football.start.allOfFootball.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final FileService fileService;


    @Override
    public void saveField(SaveFieldForm saveGroundForm) {

        // field 객체 저장
        Field field = Field.build(saveGroundForm);
        adminRepository.saveField(field);

        // fieldImage 파일로 저장 - DB 저장
        List<MultipartFile> images = saveGroundForm.getImages();
        int res = fileService.saveFile(images, field.getFieldId(), FileUploadType.FIELD_IMAGE);

    }

    @Override
    public EditFieldForm findByFieldId(Long fieldId) {

        Optional<Field> findField = adminRepository.findByField(fieldId);
        if (findField.isEmpty()) {
            return null;
        }
        Field field = findField.get();

        List<FieldImage> findFieldImage = adminRepository.findByAllFieldImage(field);

        return EditFieldForm.build(field, findFieldImage);

    }

    @Override
    public Optional<Field> findByField(Long fieldId) {
        return adminRepository.findByField(fieldId);
    }

    @Override
    public void editField(Field field, EditFieldForm form) {
        // field 수정된 내용 저장
        field.setEditField(form);

        String deleteImagesStr = form.getDeleteImages();
        String[] deleteImages = deleteImagesStr.split(",");
        for (String deleteImage : deleteImages) {
            // FieldImage DB에서 삭제
            adminRepository.deleteByFieldImage(deleteImage);
            // 파일 삭제
            fileService.removeFile(deleteImage, FileUploadType.FIELD_IMAGE);
        }

        // 새로 추가될 사진 저장
        List<MultipartFile> saveImages = form.getImages();
        fileService.saveFile(saveImages, field.getFieldId(), FileUploadType.FIELD_IMAGE);

    }

    @Override
    public List<SearchFieldForm> getSearchFieldResult(SearchFieldDto searchDto) {

        List<Field> list = adminRepository.findByAllField(searchDto);
        return list.stream().map(x -> SearchFieldForm.build(x)).collect(Collectors.toList());
    }

    @Override
    public List<SearchMatchForm> getSearchMatchResult(SearchMatchDto searchDto) {
        List<Match> list = adminRepository.findByAllMatch(searchDto);
        return list.stream().map( x -> SearchMatchForm.build(x, adminRepository.findByMatchCount(x))).collect(Collectors.toList());
    }

    @Override
    public void matchTest(Match match) {
        adminRepository.matchTest(match);
    }

}
