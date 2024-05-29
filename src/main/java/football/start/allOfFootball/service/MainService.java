package football.start.allOfFootball.service;

import football.common.domain.Member;
import football.common.domain.Orders;
import football.common.enums.domainenum.TeamEnum;
import football.start.allOfFootball.dto.MainSideInfoForm;
import football.start.allOfFootball.dto.SearchDto;
import football.start.allOfFootball.dto.SearchResultForm;

import java.util.List;
import java.util.Map;

public interface MainService {
    List<SearchResultForm> getSearchResult(SearchDto searchDto);

    MainSideInfoForm getSideInfo(Member member);

    void setTeam(Map<TeamEnum, List<Orders>> result);
}
