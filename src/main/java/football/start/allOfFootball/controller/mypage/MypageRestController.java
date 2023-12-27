package football.start.allOfFootball.controller.mypage;

import football.start.allOfFootball.SessionConst;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.dto.ChangePasswordForm;
import football.start.allOfFootball.service.MypageService;
import football.start.allOfFootball.service.OrderService;
import football.start.allOfFootball.service.domainService.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static football.start.allOfFootball.SessionConst.LOGIN_MEMBER;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MypageRestController {

    private final MypageService mypageService;
    private final MemberService memberService;
    private final OrderService orderService;


    @PostMapping("/mypage/order/get")
    public List<OrderListForm> orderList(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId,
                                         @RequestBody OrderDateForm form) {
        System.out.println("orderList 시작");
        Optional<Member> byMemberId = memberService.findByMemberId(memberId);
        if (byMemberId.isEmpty()) return null;

        Member member = byMemberId.get();
        System.out.println("form = " + form);
        List<Orders> orderList = orderService.findByMatchAll(member, form);
        List<OrderListForm> matchResultForms = orderService.getMatchResultForm(orderList);

        return matchResultForms;
    }

    @PostMapping("/change/password")
    public Map<String, String> changePassword(@Validated @RequestBody ChangePasswordForm form, BindingResult bindingResult,
                                              @SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId) {
        System.out.println("form = " + form);
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError allError : allErrors) {
            System.out.println("allError = " + allError);
        }
        Optional<Member> findMember = memberService.findByMemberId(memberId);
        Map<String, String> result = mypageService.changePassword(findMember, form, bindingResult);

        return result;

    }
}
