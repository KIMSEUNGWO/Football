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

import java.util.ArrayList;
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

        List<SearchFieldResponse> result = new ArrayList<>(list.size());
        for (Field field : list) {
            SearchFieldResponse form = new SearchFieldResponse(field);
            result.add(form);
        }
        return result;
    }

    @Override
    public List<SearchMatchResponse> getSearchMatchResult(SearchMatchRequest searchDto) {
        List<Match> list = adminRepository.findByAllMatch(searchDto);

        List<SearchMatchResponse> result = new ArrayList<>(list.size());
        for (Match match : list) {
            Integer orderPerson = adminRepository.findByMatchCount(match);
            SearchMatchResponse form = new SearchMatchResponse(match, orderPerson);
            result.add(form);
        }
        return result;
    }

    @Override
    public boolean isAdmin(Member member) {
        if (member == null) return false;
        return adminRepository.isAdmin(member);
    }
}
