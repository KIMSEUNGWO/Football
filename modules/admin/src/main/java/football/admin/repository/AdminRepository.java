package football.admin.repository;

import football.admin.dto.SearchFieldRequest;
import football.admin.dto.SearchMatchRequest;
import football.common.domain.Field;
import football.common.domain.Match;
import football.common.domain.Member;

import java.util.List;

public interface AdminRepository {

    List<Field> findByAllField(SearchFieldRequest searchDto);

    Integer findByMatchCount(Match match);

    List<Match> findByAllMatch(SearchMatchRequest searchDto);

    boolean isAdmin(Member member);
}
