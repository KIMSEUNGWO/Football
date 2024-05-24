package football.start.allOfFootball.controller.mypage;

import football.api.sms.dto.SmsRequest;
import football.api.sms.exception.CertificationException;
import football.api.sms.service.SmsService;
import football.common.domain.Member;
import football.common.dto.json.JsonDefault;
import football.login.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static football.common.consts.Constant.ERROR;
import static football.common.consts.Constant.OK;

@RestController
@RequiredArgsConstructor
public class MypageSmsController {

    private final SmsService smsService;


    @PostMapping("/changePhone/check")
    public ResponseEntity<JsonDefault> confirmSMS(@RequestBody SmsRequest smsRequest, @AuthenticationPrincipal PrincipalDetails user) throws CertificationException {
        System.out.println("smsRequest = " + smsRequest);
        Member member = user.getMember();

        String memberPhone = member.getMemberPhone();
        String phone = smsRequest.getPhone();
        if (memberPhone.equals(phone))
            return ResponseEntity.badRequest().body(new JsonDefault(ERROR, "동일한 번호로 변경할 수 없습니다."));

        smsService.checkCertificationFind(smsRequest);

        return ResponseEntity.ok(new JsonDefault(OK, "인증이 완료되었습니다."));
    }

    @PostMapping("/changePhone/confirm")
    public ResponseEntity<JsonDefault> changePhone(@RequestBody SmsRequest smsRequest, @AuthenticationPrincipal PrincipalDetails user) throws CertificationException {
        System.out.println("smsRequest = " + smsRequest);

        Member member = user.getMember();
        String memberPhone = member.getMemberPhone();
        String phone = smsRequest.getPhone();
        if (memberPhone.equals(phone)) return ResponseEntity.badRequest().body(new JsonDefault(ERROR, "동일한 번호로 변경할 수 없습니다."));

        smsService.isValidFind(smsRequest.getPhone(),smsRequest.getCertificationNumber());

        member.setMemberPhone(phone);
        return ResponseEntity.ok(new JsonDefault(OK, "휴대폰 번호를 변경했습니다."));
    }
}
