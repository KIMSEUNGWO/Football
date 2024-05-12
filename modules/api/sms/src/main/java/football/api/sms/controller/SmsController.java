package football.api.sms.controller;

import football.api.sms.dto.SmsRequest;
import football.api.sms.exception.MessageSendException;
import football.api.sms.service.SmsService;
import football.common.dto.JsonDefault;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static football.common.consts.Constant.OK;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/sms/send")
    public ResponseEntity<JsonDefault> sendSMS(@RequestBody SmsRequest data) throws MessageSendException {
        System.out.println("data = " + data);

        smsService.regexPhone(data);
        smsService.distinctPhone(data);

        String certificationNumber = smsService.sendSMS(data);
        System.out.println("certificationNumber = " + certificationNumber);
        smsService.saveSms(data, certificationNumber);


        return ResponseEntity.ok(new JsonDefault(OK, "인증번호가 발송되었습니다."));
    }

    @PostMapping("/sms/send/find")
    public ResponseEntity<JsonDefault> sendSMSFindMember(@RequestBody SmsRequest data) throws MessageSendException {
        System.out.println("data = " + data);

        smsService.regexPhone(data);

        String certificationNumber = smsService.sendSMS(data);

        smsService.saveSms(data, certificationNumber);

        return ResponseEntity.ok(new JsonDefault(OK, "인증번호가 발송되었습니다."));
    }

    @PostMapping("/sms/confirm")
    public ResponseEntity<JsonDefault> confirmSMS(@RequestBody SmsRequest data) {
        System.out.println("data = " + data);

//        smsService.regexPhone(data);
//        smsService.distinctPhone(data);

        smsService.checkCertification(data);

        return ResponseEntity.ok(new JsonDefault(OK, "인증되었습니다."));
    }
}
