package football.start.allOfFootball.controller;

import football.start.allOfFootball.SessionConst;
import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.CouponList;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.dto.CouponListForm;
import football.start.allOfFootball.dto.OrderForm;
import football.start.allOfFootball.service.domainService.CouponListService;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Optional;

import static football.start.allOfFootball.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final MatchService matchService;
    private final MemberService memberService;
    private final CouponListService couponListService;


    @GetMapping("/order/{matchId}")
    public String order(@PathVariable Long matchId, @SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, Model model, HttpServletResponse response) {
        if (matchId == null || memberId == null) {
            return AlertUtils.alertAndBack(response, "비정상적인 접근입니다.");
        }

        Optional<Match> findMatch = matchService.findByMatch(matchId);
        Optional<Member> findMember = memberService.findByMemberId(memberId);
        if (findMatch.isEmpty() || findMember.isEmpty()) {
            return AlertUtils.alertAndBack(response, "경기 정보가 없습니다.");
        }

        Match match = findMatch.get();
        Member member = findMember.get();
        List<CouponListForm> couponList = couponListService.getCouponList(member);

        OrderForm orderForm = OrderForm.build(member, match, couponList);
        model.addAttribute("orderForm", orderForm);

        return "order";
    }
}
