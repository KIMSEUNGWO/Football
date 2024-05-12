package football.start.allOfFootball.repository;

import football.internal.database.domain.Field;
import football.internal.database.domain.Match;
import football.internal.database.domain.Member;
import football.start.allOfFootball.controller.admin.SearchFieldDto;
import football.start.allOfFootball.controller.admin.SearchMatchDto;

import java.util.List;

public interface AdminRepository {

    List<Field> findByAllField(SearchFieldDto searchDto);

    Integer findByMatchCount(Match match);

    List<Match> findByAllMatch(SearchMatchDto searchDto);

    boolean isAdmin(Member member);
}
