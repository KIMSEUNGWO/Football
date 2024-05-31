package football.start.allOfFootball.service;

import football.common.enums.domainenum.TeamEnum;
import football.redis.service.RankService;
import football.common.domain.Member;
import football.common.domain.Orders;
import football.start.allOfFootball.dto.MainSideInfoForm;
import football.start.allOfFootball.dto.SearchDto;
import football.start.allOfFootball.dto.SearchResultForm;
import football.common.domain.Match;
import football.start.allOfFootball.mapper.Mapper;
import football.start.allOfFootball.repository.MainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MainServiceImpl implements MainService{

    private final MainRepository mainRepository;
    private final OrderService orderService;
    private final RankService rankService;

    @Override
    public List<SearchResultForm> getSearchResult(SearchDto searchDto) {
        List<Match> matchList = mainRepository.findByAllMatch(searchDto);
        return matchList.stream().map(Mapper::toSearchResultForm).toList();
    }

    @Override
    public MainSideInfoForm getSideInfo(Member member) {
        Long myRank = rankService.getRank(member.getMemberId(), member.getMemberScore());
        MainSideInfoForm form = new MainSideInfoForm(member, myRank);

        List<Orders> ordersList = orderService.findByMatchBefore(member);
        ordersList.forEach(orders -> form.setMySchedule(orders.getMatch()));
        return form;
    }

    @Override
    public void setTeam(Map<TeamEnum, List<Orders>> result) {
        for (TeamEnum teamEnum : result.keySet()) {
            List<Orders> ordersList = result.get(teamEnum);
            for (Orders orders : ordersList) {
                orders.setTeam(teamEnum);
            }
        }
    }
}
