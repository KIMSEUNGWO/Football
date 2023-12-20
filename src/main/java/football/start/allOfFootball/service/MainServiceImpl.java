package football.start.allOfFootball.service;

import football.start.allOfFootball.common.redis.RankService;
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

        return matchList.stream().map(x -> SearchResultForm.build(x)).collect(Collectors.toList());
    }

    @Override
    public MainSideInfoForm getSideInfo(Long memberId) {
        Optional<Member> byMemberId = memberService.findByMemberId(memberId);
        if (byMemberId.isEmpty()) return null;

        Member member = byMemberId.get();
        Long myRank = rankService.getRank(member);
        MainSideInfoForm form = new MainSideInfoForm();
        form.setMyInfo(member, myRank);

        List<Orders> ordersList = orderService.findByMatchBefore(member);
        if (ordersList.isEmpty()) return form;

        ordersList.forEach(x -> form.setMySchedule(x.getMatch()));

        return form;
    }
}
