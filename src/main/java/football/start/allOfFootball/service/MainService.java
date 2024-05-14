package football.start.allOfFootball.service;

import football.common.domain.Member;
import football.start.allOfFootball.dto.MainSideInfoForm;
import football.start.allOfFootball.dto.SearchDto;
import football.start.allOfFootball.dto.SearchResultForm;

import java.util.List;

public interface MainService {
    List<SearchResultForm> getSearchResult(SearchDto searchDto);

    MainSideInfoForm getSideInfo(Member member);
}
