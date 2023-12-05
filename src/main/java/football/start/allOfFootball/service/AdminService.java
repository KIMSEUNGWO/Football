package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.admin.*;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.Match;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    void saveField(SaveFieldForm saveGroundForm);

    List<SearchFieldForm> getSearchFieldResult(SearchFieldDto searchDto);

    EditFieldForm findByFieldId(Long fieldId);

    Optional<Field> findByField(Long fieldId);

    void editField(Field field, EditFieldForm editFieldForm);

    List<SearchMatchForm> getSearchMatchResult(SearchMatchDto searchDto);

    void saveMatch(Field field, SaveMatchForm saveMatchForm);

    Optional<Match> findByMatch(Long matchId);

    void editMatch(Match match, EditMatchForm editMatchForm);
}
