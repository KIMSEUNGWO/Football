package football.start.allOfFootball.controller;

import football.common.domain.*;
import football.common.common.alert.AlertUtils;
import football.common.exception.match.NotExistsMatchException;
import football.login.config.auth.PrincipalDetails;
import football.start.allOfFootball.dto.CouponListForm;
import football.start.allOfFootball.dto.OrderForm;
import football.start.allOfFootball.dto.OrderPostForm;
import football.start.allOfFootball.mapper.Mapper;
import football.start.allOfFootball.service.OrderService;
import football.start.allOfFootball.service.domainService.CashService;
import football.start.allOfFootball.service.domainService.CouponListService;
import football.start.allOfFootball.service.domainService.MatchService;
import football.start.allOfFootball.exception.NotEnoughCashException;
import jakarta.servlet.http.HttpServletResponse;
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


    @GetMapping("/order/{matchId}")
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

    @PostMapping("/order/{matchId}")
    public String orderPost(@PathVariable Long matchId,
                            @AuthenticationPrincipal PrincipalDetails user,
                            HttpServletResponse response,
                            @ModelAttribute OrderPostForm form) throws NotExistsMatchException, NotEnoughCashException {

        if (form.getPolicy() == null) {
            return AlertUtils.alertAndMove(response, "모든 약관에 동의해주세요.", "/order/" + matchId);
        }
        Member member = user.getMember();

        Match match = matchService.findByMatchOrRedirect(matchId, "/");

        if (match.isFull()) {
            return AlertUtils.alertAndMove(response, "모집이 마감되었습니다.", "/");
        }


        Optional<CouponList> couponList = couponListService.findByCouponListId(form.getCouponNum());

        int price = cashService.calculate(matchId, match, member, couponList);

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
