package football.start.allOfFootball.controller;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.*;
import football.start.allOfFootball.dto.CouponListForm;
import football.start.allOfFootball.dto.OrderForm;
import football.start.allOfFootball.dto.OrderPostForm;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.gradeEnums.MatchEnum;
import football.start.allOfFootball.service.OrderService;
import football.start.allOfFootball.service.domainService.CouponListService;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static football.start.allOfFootball.SessionConst.LOGIN_MEMBER;
import static football.start.allOfFootball.SessionConst.REDIRECT_URL;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final MatchService matchService;
    private final MemberService memberService;
    private final CouponListService couponListService;


    @GetMapping("/order/{matchId}")
    public String order(@PathVariable Long matchId, @SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, Model model, HttpServletRequest request) {

        Match match = matchService.findByMatch(matchId).get();
        Member member = memberService.findByMemberId(memberId).get();

        List<CouponListForm> couponList = couponListService.getCouponList(member);

        OrderForm orderForm = new OrderForm(member, match, couponList);
        model.addAttribute("orderForm", orderForm);

        return "order";
    }

    @PostMapping("/order/{matchId}")
    public String orderPost(@PathVariable Long matchId, @SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, HttpServletResponse response, @ModelAttribute OrderPostForm form) {

        if (form.getPolicy() == null) {
            return AlertUtils.alertAndBack(response, "모든 약관에 동의해주세요.");
        }

        Match match = matchService.findByMatch(matchId).get();
        Member member = memberService.findByMemberId(memberId).get();

        List<Orders> ordersList = member.getOrdersList();

        Manager manager = member.getManager();
        boolean isAlreadyApply = memberService.isAlreadyApply(ordersList, match, manager);


        Optional<CouponList> couponList = couponListService.findByCouponListId(form.getCouponNum());

        int cash = member.getMemberCash();
        int price = orderService.calculate(couponList);

        if (cash < price) {
            return AlertUtils.alertAndMove(response, "잔액이 부족합니다.", "/cash/charge");
        }

        Orders orders = Orders.builder()
                            .match(match)
                            .member(member)
                            .payment(price)
                            .build();
        orderService.save(orders, member, couponList, price); // order 저장
        matchService.refreshMatchStatus(match); // MatchStatus 상태 변경
        log.info("Orders 정상 처리");

        return "redirect:/mypage/order";
    }
}
