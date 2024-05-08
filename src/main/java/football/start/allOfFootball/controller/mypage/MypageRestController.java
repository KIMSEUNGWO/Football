package football.start.allOfFootball.controller.mypage;

import football.start.allOfFootball.controller.api.smsAPI.SmsRequest;
import football.start.allOfFootball.controller.api.smsAPI.SmsService;
import football.start.allOfFootball.controller.api.smsAPI.exception.CertificationException;
import football.start.allOfFootball.customAnnotation.SessionLogin;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.dto.ChangePasswordForm;
import football.start.allOfFootball.dto.json.JsonDefault;
import football.start.allOfFootball.service.MypageService;
import football.start.allOfFootball.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    private final SmsService smsService;


    @PostMapping("/mypage/order/get")
    public List<OrderListForm> orderList(@SessionLogin Member member,
                                         @RequestBody OrderDateForm form) {
        System.out.println("orderList 시작");

        System.out.println("form = " + form);
        List<Orders> orderList = orderService.findByMatchAll(member, form);

        return orderService.getMatchResultForm(orderList);
    }

    @PostMapping("/change/password")
    public Map<String, String> changePassword(@Validated @RequestBody ChangePasswordForm form, BindingResult bindingResult,
                                              @SessionLogin Member member) {
        System.out.println("form = " + form);
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError allError : allErrors) {
            System.out.println("allError = " + allError);
        }
        Map<String, String> result = mypageService.changePassword(member, form, bindingResult);

        return result;

    }

    @PostMapping("/changePhone/check")
    public ResponseEntity<JsonDefault> confirmSMS(@RequestBody SmsRequest smsRequest, @SessionLogin Member member) {
        System.out.println("smsRequest = " + smsRequest);

        String memberPhone = member.getMemberPhone();
        String phone = smsRequest.getPhone();
        if (memberPhone.equals(phone)) {
            return ResponseEntity.badRequest().body(new JsonDefault("error", "동일한 번호로 변경할 수 없습니다."));
        }

        try {
            smsService.checkCertificationFind(smsRequest);
        } catch (CertificationException e) {
            return new ResponseEntity<>(e.getJsonDefault(), e.getCode());
        }
        return ResponseEntity.ok(new JsonDefault("ok", "인증이 완료되었습니다."));
    }

    @Transactional
    @PostMapping("/changePhone/confirm")
    public ResponseEntity<JsonDefault> changePhone(@RequestBody SmsRequest smsRequest, @SessionLogin Member member) {
        System.out.println("smsRequest = " + smsRequest);

        String memberPhone = member.getMemberPhone();
        String phone = smsRequest.getPhone();
        if (memberPhone.equals(phone)) {
            return ResponseEntity.badRequest().body(new JsonDefault("error", "동일한 번호로 변경할 수 없습니다."));
        }

        try {
            smsService.isValidFind(smsRequest);
        } catch (CertificationException e) {
            return new ResponseEntity<>(e.getJsonDefault(), e.getCode());
        }
        member.setMemberPhone(phone);
        return ResponseEntity.ok(new JsonDefault("ok", "휴대폰 번호를 변경했습니다."));
    }


}
