package football.api.sms.repository;

import football.api.sms.SmsConst;
import football.api.sms.dto.SmsRequest;
import football.api.sms.exception.MessageSendException;
import football.api.sms.exception.NotFoundCertificationNumberException;
import football.internal.database.domain.Sms;
import football.internal.database.repository.SmsInternalRepository;
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

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Collectors;


@Slf4j
@Repository
@RequiredArgsConstructor
public class SmsRepository {

    @Value("${sms.key}")
    private String API_KEY;
    @Value("${sms.secret}")
    private String SECRET_API_KEY;
    @Value("${sms.phone}")
    private String PHONE;

    private final String URI = "https://api.coolsms.co.kr";


    private final SmsInternalRepository smsInternalRepository;

    public String createCertificationNumber() {
        return new Random()
            .ints(0, 9)
            .limit(SmsConst.CERTIFICATION_LENGTH)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining());
    }

    public void save(Sms sms) {
        smsInternalRepository.save(sms);
    }

    public Sms findSms(SmsRequest data) {
        return smsInternalRepository.findSms(data.getPhone(), data.getCertificationNumber())
            .orElseThrow(NotFoundCertificationNumberException::new);
    }

    public boolean existsByMemberPhone(String phone) {
        return smsInternalRepository.existsByMemberPhone(phone);
    }

    public void delete(Sms sms) {
        smsInternalRepository.delete(sms);
    }

    public boolean isBefore(Sms sms) {
        LocalDateTime expireDate = sms.getExpireDate();
        LocalDateTime now = LocalDateTime.now();

        return now.isBefore(expireDate);
    }

    public Message createMessage(String phone, String certificationNumber) {
        Message message = new Message();

        message.setFrom(PHONE);
        message.setTo(phone);
        message.setText(String.format("[모두의풋볼] 본인확인 \n 인증번호는 [%s] 입니다.", certificationNumber));
        return message;
    }

    public void send(Message message) throws MessageSendException {
        try {
            DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(API_KEY, SECRET_API_KEY, URI);
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
