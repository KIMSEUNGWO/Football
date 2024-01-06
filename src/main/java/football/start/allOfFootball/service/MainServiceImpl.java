package football.start.allOfFootball.service;

import football.start.allOfFootball.common.redis.RankService;
import football.start.allOfFootball.controller.admin.SearchMatchForm;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.dto.MainSideInfoForm;
import football.start.allOfFootball.dto.SearchDto;
import football.start.allOfFootball.dto.SearchResultForm;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.repository.MainRepository;
import football.start.allOfFootball.service.domainService.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MainServiceImpl implements MainService{

    private final MainRepository mainRepository;
    private final MemberService memberService;
    private final OrderService orderService;
    private final RankService rankService;
    @Override
    public List<SearchResultForm> getSearchResult(SearchDto searchDto) {
        List<Match> matchList = mainRepository.findByAllMatch(searchDto);

        List<SearchResultForm> list = new ArrayList<>();
        for (Match match : matchList) {
            SearchResultForm searchResultForm = new SearchResultForm(match);
            list.add(searchResultForm);
        }
        return list;
    }

    @Override
    public MainSideInfoForm getSideInfo(Member member) {
        Long myRank = rankService.getRank(member);
        MainSideInfoForm form = new MainSideInfoForm();
        form.setMyInfo(member, myRank);

        List<Orders> ordersList = orderService.findByMatchBefore(member);

        for (Orders orders : ordersList) {
            form.setMySchedule(orders.getMatch());
        }
        return form;
    }
}
