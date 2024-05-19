package football.admin.service;

import football.admin.dto.SearchFieldRequest;
import football.admin.dto.SearchFieldResponse;
import football.admin.dto.SearchMatchRequest;
import football.admin.dto.SearchMatchResponse;
import football.common.domain.Member;

import java.util.List;

public interface AdminService {

    List<SearchFieldResponse> getSearchFieldResult(SearchFieldRequest searchDto);

    List<SearchMatchResponse> getSearchMatchResult(SearchMatchRequest searchDto);

    boolean isAdmin(Member member);
}
