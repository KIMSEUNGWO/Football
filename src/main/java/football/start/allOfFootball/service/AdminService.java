package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.admin.*;
import football.start.allOfFootball.domain.Admin;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Member;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<SearchFieldForm> getSearchFieldResult(SearchFieldDto searchDto);

    List<SearchMatchForm> getSearchMatchResult(SearchMatchDto searchDto);

    Optional<Admin> findByMember(Member member);
}
