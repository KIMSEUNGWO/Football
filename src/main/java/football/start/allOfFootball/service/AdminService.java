package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.admin.*;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.Match;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<SearchFieldForm> getSearchFieldResult(SearchFieldDto searchDto);

    List<SearchMatchForm> getSearchMatchResult(SearchMatchDto searchDto);

}
