package football.start.allOfFootball.repository;

import football.start.allOfFootball.controller.admin.SearchDto;
import football.start.allOfFootball.domain.Field;

import java.util.List;

public interface AdminRepository {
    void saveField(Field field);

    List<Field> findByAllField(SearchDto searchDto);
}
