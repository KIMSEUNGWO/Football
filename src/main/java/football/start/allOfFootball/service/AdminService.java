package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.admin.*;
import football.internal.database.domain.Member;

import java.util.List;

public interface AdminService {

    List<SearchFieldForm> getSearchFieldResult(SearchFieldDto searchDto);

    List<SearchMatchForm> getSearchMatchResult(SearchMatchDto searchDto);

    boolean isAdmin(Member member);
}
