package football.start.allOfFootball.controller.mypage;

import football.common.config.auth.PrincipalDetails;
import football.common.config.auth.UserRefreshProvider;
import football.common.customAnnotation.SessionLogin;
import football.common.domain.Member;
import football.common.domain.Orders;
import football.start.allOfFootball.dto.ChangePasswordForm;
import football.start.allOfFootball.service.MypageService;
import football.start.allOfFootball.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MypageRestController {

    private final MypageService mypageService;
    private final OrderService orderService;
    private final UserRefreshProvider provider;



    @PostMapping("/mypage/order/get")
    public List<OrderListForm> orderList(@AuthenticationPrincipal PrincipalDetails user,
                                         @RequestBody OrderDateForm form) {
        Member member = user.getMember();
        System.out.println("orderList 시작");

        System.out.println("form = " + form);
        List<Orders> orderList = orderService.findByMatchAll(member, form);

        return orderService.getMatchResultForm(orderList);
    }

    @PostMapping("/change/password")
    public Map<String, String> changePassword(@Validated @RequestBody ChangePasswordForm form, BindingResult bindingResult,
                                              @AuthenticationPrincipal PrincipalDetails user) {
        Member member = user.getMember();
        System.out.println("form = " + form);
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError allError : allErrors) {
            System.out.println("allError = " + allError);
        }
        Map<String, String> result = mypageService.changePassword(member, form, bindingResult);

        provider.refresh(user);
        return result;

    }




}
