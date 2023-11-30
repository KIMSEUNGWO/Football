package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.admin.EditFieldForm;
import football.start.allOfFootball.controller.admin.SaveFieldForm;
import football.start.allOfFootball.controller.admin.SearchDto;
import football.start.allOfFootball.controller.admin.SearchFieldForm;
import football.start.allOfFootball.domain.Field;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    void saveField(SaveFieldForm saveGroundForm);

    List<SearchFieldForm> getSearchResult(SearchDto searchDto);

    EditFieldForm findByFieldId(Long fieldId);

    Optional<Field> findByField(Long fieldId);

    void editField(Field field, EditFieldForm editFieldForm);
}
