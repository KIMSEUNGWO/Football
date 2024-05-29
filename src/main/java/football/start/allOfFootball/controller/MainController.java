package football.start.allOfFootball.controller;

import football.common.domain.Match;
import football.common.domain.Member;
import football.common.domain.Orders;
import football.common.enums.Role;
import football.common.enums.domainenum.TeamEnum;
import football.common.enums.gradeEnums.GradeEnum;
import football.common.jpaRepository.JpaMatchRepository;
import football.common.jpaRepository.JpaMemberRepository;
import football.login.config.auth.PrincipalDetails;
import football.start.allOfFootball.dto.MainSideInfoForm;
import football.start.allOfFootball.dto.SearchDto;
import football.start.allOfFootball.dto.SearchResultForm;
import football.start.allOfFootball.service.MainService;
import football.start.allOfFootball.service.OrderService;
import football.start.allOfFootball.service.domainService.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static football.common.enums.matchenum.MatchStatus.경기시작전;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    // testRepository
    private final OrderService orderService;
    private final MatchService matchService;
    private final JpaMatchRepository jpaMatchRepository;
    private final JpaMemberRepository jpaMemberRepository;

    @GetMapping
    public String mainPage(Model model, @AuthenticationPrincipal PrincipalDetails user) {
        System.out.println("user = " + user);
        if (user != null) {
            MainSideInfoForm form = mainService.getSideInfo(user.getMember());
            model.addAttribute("logined", user.getMember().getMemberId());
            model.addAttribute("side", form);
        }
        return "main";
    }

    @ResponseBody
    @PostMapping("/get")
    public List<SearchResultForm> search(@RequestBody SearchDto searchDto) {
        System.out.println("searchDto = " + searchDto);

        List<SearchResultForm> result = mainService.getSearchResult(searchDto);
        for (SearchResultForm searchResultForm : result) {
            System.out.println("searchResultForm = " + searchResultForm);
        }
        return result;
    }


    @Transactional
    @GetMapping("/test/match/{matchId}")
    public String testMatch(@PathVariable Long matchId) {
        Match match = jpaMatchRepository.findById(matchId).get();

        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Member member = Member.builder()
                .createDate(LocalDateTime.now())
                .role(Role.USER)
                .grade(GradeEnum.루키)
                .memberScore(1000)
                .memberCash(200000)
                .memberEmail("테스트")
                .memberName("테스트이름")
                .memberPhone("00000000000")
                .memberRecentlyDate(LocalDateTime.now())
                .build();
            members.add(member);
        }
        jpaMemberRepository.saveAll(members);

        for (Member member : members) {
            Orders orders = Orders.builder()
                .match(match)
                .member(member)
                .amountPayment(10000)
                .build();
            orderService.save(orders, Optional.empty());
        }
        matchService.refreshMatchStatus(match); // MatchStatus 상태 변경

        return "main";
    }

    @Transactional
    @GetMapping("/test/match2/{matchId}")
    public String testMatch2(@PathVariable Long matchId) {
        Match match = jpaMatchRepository.findById(matchId).get();
        match.setMatchStatus(경기시작전);
        List<Orders> ordersList = match.getOrdersList();

        // team 자동 분배 알고리즘 시작
        Map<TeamEnum, List<Orders>> result = getResult(ordersList, match.getMatchCount());

        // 결과 Orders TeamEnum 설정
        mainService.setTeam(result);

        return "main";

    }

    @GetMapping("/a")
    public String testMatch3() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String encode = encoder.encode("1234");
        System.out.println("encode = " + encode);
        return "main";
    }

    public Map<TeamEnum, List<Orders>> getResult(List<Orders> score, int teamCount) {
        score.sort((o1, o2) -> o2.getMember().getGrade().getScore() - o1.getMember().getGrade().getScore());

        List<TeamEnum> team = TeamEnum.getTeam(teamCount);
        int[] teamSum = new int[team.size()];

        Map<TeamEnum, List<Orders>> result = new HashMap<>(team.size());
        for (TeamEnum teamEnum : team) {
            result.put(teamEnum, new ArrayList<>());
        }

        for (Orders orders : score) {
            int minIndex = getMinValueIndex(teamSum);
            TeamEnum teamEnum = team.get(minIndex);

            result.get(teamEnum).add(orders);

            teamSum[minIndex] += orders.getMember().getGrade().getScore();
        }
        return result;
    }

    private int getMinValueIndex(int[] teamSum) {
        int index = 0;
        int value = teamSum[0];

        for (int i = 0; i < teamSum.length; i++) {
            if (teamSum[i] < value) {
                index = i;
                value = teamSum[i];
            }
        }
        return index;
    }
}
