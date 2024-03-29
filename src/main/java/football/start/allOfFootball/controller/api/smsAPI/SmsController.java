package football.start.allOfFootball.controller.api.smsAPI;

import football.start.allOfFootball.controller.api.smsAPI.exception.CertificationException;
import football.start.allOfFootball.controller.api.smsAPI.exception.IllegalPhoneException;
import football.start.allOfFootball.controller.api.smsAPI.exception.MessageSendException;
import football.start.allOfFootball.dto.json.JsonDefault;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/sms/send")
    public ResponseEntity<JsonDefault> sendSMS(@RequestBody SmsRequest data) {
        System.out.println("data = " + data);

        smsService.regexPhone(data);
        smsService.distinctPhone(data);
        String certificationNumber = smsService.sendSMS(data);
        smsService.saveSms(data, certificationNumber);


        return new ResponseEntity<>(new JsonDefault("ok", "인증번호가 발송되었습니다."), HttpStatus.OK);
    }

    @PostMapping("/sms/send/find")
    public ResponseEntity<JsonDefault> sendSMSFindMember(@RequestBody SmsRequest data) {
        System.out.println("data = " + data);

        smsService.regexPhone(data);
        String certificationNumber = smsService.sendSMS(data);
        smsService.saveSms(data, certificationNumber);


        return new ResponseEntity<>(new JsonDefault("ok", "인증번호가 발송되었습니다."), HttpStatus.OK);
    }

    @PostMapping("/sms/confirm")
    public ResponseEntity<JsonDefault> confirmSMS(@RequestBody SmsRequest data) {
        System.out.println("data = " + data);

        smsService.checkCertification(data);

        return new ResponseEntity<>(new JsonDefault("ok", "인증되었습니다,"), HttpStatus.OK);
    }
}
