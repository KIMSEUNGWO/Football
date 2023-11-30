package football.start.allOfFootball.repository;

import football.start.allOfFootball.controller.admin.SearchDto;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;

import java.util.List;
import java.util.Optional;

public interface AdminRepository {
    void saveField(Field field);

    List<Field> findByAllField(SearchDto searchDto);

    Optional<Field> findByField(Long fieldId);

    List<FieldImage> findByAllFieldImage(Field field);

    void deleteByFieldImage(String deleteImage);
}
