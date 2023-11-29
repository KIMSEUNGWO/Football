package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.admin.EditFieldForm;
import football.start.allOfFootball.controller.admin.SaveFieldForm;
import football.start.allOfFootball.controller.admin.SearchDto;
import football.start.allOfFootball.controller.admin.SearchFieldForm;

import java.util.List;

public interface AdminService {
    void saveField(SaveFieldForm saveGroundForm);

    List<SearchFieldForm> getSearchResult(SearchDto searchDto);

    EditFieldForm findByFieldId(Long fieldId);
}
