package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.admin.SaveGroundForm;
import football.start.allOfFootball.controller.admin.SearchDto;
import football.start.allOfFootball.controller.admin.SearchFieldForm;

import java.util.List;

public interface AdminService {
    void saveField(SaveGroundForm saveGroundForm);

    List<SearchFieldForm> getSearchResult(SearchDto searchDto);
}
