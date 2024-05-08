package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.admin.*;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.repository.AdminRepository;
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
    public List<SearchFieldForm> getSearchFieldResult(SearchFieldDto searchDto) {
        List<Field> list = adminRepository.findByAllField(searchDto);

        List<SearchFieldForm> result = new ArrayList<>();
        for (Field field : list) {
            SearchFieldForm form = new SearchFieldForm(field);
            result.add(form);
        }
        return result;
    }

    @Override
    public List<SearchMatchForm> getSearchMatchResult(SearchMatchDto searchDto) {
        List<Match> list = adminRepository.findByAllMatch(searchDto);

        List<SearchMatchForm> result = new ArrayList<>();
        for (Match match : list) {
            Integer orderPerson = adminRepository.findByMatchCount(match);
            SearchMatchForm form = new SearchMatchForm(match, orderPerson);
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
