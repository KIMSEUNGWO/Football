package football.api.sms.repository;

import football.api.sms.exception.MessageSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Random;
import java.util.stream.Collectors;

import static football.api.sms.consts.SmsConst.CERTIFICATION_MAX_LENGTH;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SmsRepositoryAPI {

    @Value("${sms.key}")
    private String API_KEY;

    @Value("${sms.secret}")
    private String SECRET_KEY;

    private final String URI = "https://api.coolsms.co.kr";

    @Value("${sms.phone}")
    private String SMS_PHONE;


    public Message createMessage(String phone, String certificationNumber) {
        Message message = new Message();
        message.setFrom(SMS_PHONE);
        message.setTo(phone);
        message.setText(String.format("[모두의풋볼] 본인확인 \n 인증번호는 [%s] 입니다.", certificationNumber));
        return message;
    }
    public String createCertificationNumber() {
        return new Random()
            .ints(0, 9)
            .limit(CERTIFICATION_MAX_LENGTH)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining());
    }


    public void send(Message message) throws MessageSendException {
        try {
            DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(API_KEY, SECRET_KEY, URI);
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException e) {
            log.error("[SmsService] [메세지 전송에러] {}", e.getMessage());
            throw new MessageSendException("메세지 전송실패", e);
        } catch (NurigoEmptyResponseException e) {
            log.error("[SmsService] [메세지 전송에러] {}", e.getMessage());
            throw new MessageSendException("메세지 무응답", e);
        } catch (NurigoUnknownException e) {
            log.error("[SmsService] [메세지 전송에러] {}", e.getMessage());
            throw new MessageSendException("메세지 알수없는 에러입니다. 관리자에게 문의해주세요.", e);
        }
    }
}
