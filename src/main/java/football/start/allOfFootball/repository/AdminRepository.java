package football.start.allOfFootball.repository;

import football.start.allOfFootball.controller.admin.SearchFieldDto;
import football.start.allOfFootball.controller.admin.SearchMatchDto;
import football.start.allOfFootball.domain.*;

import java.util.List;

public interface AdminRepository {

    List<Field> findByAllField(SearchFieldDto searchDto);

    Integer findByMatchCount(Match match);

    List<Match> findByAllMatch(SearchMatchDto searchDto);

    boolean isAdmin(Member member);
}
