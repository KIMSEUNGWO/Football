package football.admin.service;

import football.admin.dto.SearchFieldRequest;
import football.admin.dto.SearchFieldResponse;
import football.admin.dto.SearchMatchRequest;
import football.admin.dto.SearchMatchResponse;
import football.common.domain.Field;
import football.common.domain.Match;
import football.common.domain.Member;
import football.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public List<SearchFieldResponse> getSearchFieldResult(SearchFieldRequest searchDto) {
        List<Field> list = adminRepository.findByAllField(searchDto);
        return list.stream().map(SearchFieldResponse::new).toList();
    }

    @Override
    public List<SearchMatchResponse> getSearchMatchResult(SearchMatchRequest searchDto) {
        List<Match> list = adminRepository.findByAllMatch(searchDto);
        return list.stream()
            .map(match -> new SearchMatchResponse(match, adminRepository.findByMatchCount(match)))
            .toList();
    }

    @Override
    public boolean isAdmin(Member member) {
        return (member != null && adminRepository.isAdmin(member));
    }
}
