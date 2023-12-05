package football.start.allOfFootball.repository;

import football.start.allOfFootball.controller.admin.SearchFieldDto;
import football.start.allOfFootball.controller.admin.SearchMatchDto;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.domain.Match;

import java.util.List;
import java.util.Optional;

public interface AdminRepository {
    void saveField(Field field);

    List<Field> findByAllField(SearchFieldDto searchDto);

    Optional<Field> findByField(Long fieldId);

    List<FieldImage> findByAllFieldImage(Field field);

    void deleteByFieldImage(String deleteImage);

    Integer findByMatchCount(Match match);

    List<Match> findByAllMatch(SearchMatchDto searchDto);

    void saveMatch(Match saveMatch);

    Optional<Match> findByMatch(Long matchId);
}
