package football.start.allOfFootball.controller;

import football.common.domain.*;
import football.common.exception.match.NotExistsMatchException;
import football.login.config.auth.PrincipalDetails;
import football.start.allOfFootball.dto.CouponListForm;
import football.start.allOfFootball.dto.OrderForm;
import football.start.allOfFootball.dto.OrderPostForm;
import football.start.allOfFootball.exception.MatchFullException;
import football.start.allOfFootball.exception.OrderException;
import football.start.allOfFootball.mapper.Mapper;
import football.start.allOfFootball.service.OrderService;
import football.start.allOfFootball.service.domainService.CashService;
import football.start.allOfFootball.service.domainService.CouponListService;
import football.start.allOfFootball.service.domainService.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final MatchService matchService;
    private final CouponListService couponListService;
    private final CashService cashService;


    @GetMapping("/order/{matchId:[0-9]}")
    public String order(@PathVariable Long matchId,
                        @AuthenticationPrincipal PrincipalDetails user,
                        Model model) throws NotExistsMatchException {

        Match match = matchService.findByMatchOrRedirect(matchId, "/");

        Member member = user.getMember();

        List<CouponListForm> couponList = couponListService.getCouponList(member);

        OrderForm orderForm = Mapper.toOrderForm(member, match, couponList);
        model.addAttribute("orderForm", orderForm);

        return "order";
    }

    @PostMapping("/order/{matchId:[0-9]}")
    public String orderPost(@PathVariable Long matchId,
                            @AuthenticationPrincipal PrincipalDetails user,
                            @ModelAttribute OrderPostForm form) throws NotExistsMatchException, OrderException {
        orderService.requestDataValid(form, matchId);

        Match match = matchService.findByMatchOrRedirect(matchId, "/");
        if (match.isFull()) throw new MatchFullException();

        Member member = user.getMember();

        Optional<CouponList> couponList = couponListService.findByCouponListId(form.getCouponNum());

        int price = cashService.calculate(match, member, couponList);

        Orders orders = Orders.builder()
                            .match(match)
                            .member(member)
                            .amountPayment(price)
                            .build();

        orderService.save(orders, couponList); // order 저장
        matchService.refreshMatchStatus(match); // MatchStatus 상태 변경

        log.info("Orders 정상 처리");

        return "redirect:/match/" + matchId;
    }
}
