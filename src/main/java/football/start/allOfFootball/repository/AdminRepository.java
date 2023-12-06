package football.start.allOfFootball.repository;

import football.start.allOfFootball.controller.admin.SearchFieldDto;
import football.start.allOfFootball.controller.admin.SearchMatchDto;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.domain.Match;

import java.util.List;
import java.util.Optional;

public interface AdminRepository {

    List<Field> findByAllField(SearchFieldDto searchDto);

    Integer findByMatchCount(Match match);

    List<Match> findByAllMatch(SearchMatchDto searchDto);

}
