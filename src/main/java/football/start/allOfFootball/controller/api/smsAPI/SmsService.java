package football.start.allOfFootball.controller.api.smsAPI;

import football.start.allOfFootball.controller.api.smsAPI.exception.*;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Sms;
import football.start.allOfFootball.dto.json.JsonDefault;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    @Value("${sms.key}")
    private String apiKey;
    @Value("${sms.secret}")
    private String secretKey;
    private final String url = "https://api.coolsms.co.kr";

    private final SmsRepository smsRepository;

    protected String sendSMS(SmsRequest smsRequest) throws MessageSendException {
        String certificationNumber = smsRepository.createCertificationNumber();

        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, url);
        Message message = new Message();
        message.setFrom("01066038635");
        message.setTo(smsRequest.getPhone());
        message.setText(smsRepository.createMessage(certificationNumber));

        try {
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException e) {
            log.error("[SmsService] [메세지 전송에러] {}", e.getMessage());
            throw new MessageSendException("메세지 전송실패", e);
        } catch (NurigoEmptyResponseException e) {
            log.error("[SmsService] [메세지 전송에러] {}", e.getMessage());
            throw new MessageSendException("메세지 무응답", e);
        } catch (NurigoUnknownException e) {
            log.error("[SmsService] [메세지 전송에러] {}", e.getMessage());
            throw new MessageSendException("메세지 알수없는 에러", e);
        }

        return certificationNumber;
    }

    public void saveSms(SmsRequest data, String certificationNumber) {

        Sms sms = Sms.builder()
            .phone(data.getPhone())
            .certificationNumber(certificationNumber)
            .expireDate(getExpireDate())
            .build();

        smsRepository.save(sms);
    }

    private LocalDateTime getExpireDate() {
        final int expireTime = 5; // 5분 후 만료
        LocalDateTime now = LocalDateTime.now();

        return now.plusMinutes(expireTime);
    }

    /**
     * 휴대폰 인증 버튼 클릭시 실행되는 메서드
     * @param smsRequest
     * @throws CertificationException
     */
    public void checkCertification(SmsRequest smsRequest) throws CertificationException {
        regexPhone(smsRequest);
        distinctPhone(smsRequest);

        Optional<Sms> findSms = smsRepository.findSms(smsRequest);
        if (findSms.isEmpty()) {
            throw new NotFoundCertificationNumberException(HttpStatus.BAD_REQUEST, new JsonDefault("error", "인증정보가 존재하지 않습니다."));
        }
        Sms sms = findSms.get();

        boolean isExpireDateBefore = smsRepository.isBefore(sms);
        if (!isExpireDateBefore) {
            smsRepository.delete(sms);
            throw new ExpireCertificationException(HttpStatus.BAD_REQUEST, new JsonDefault("error", "인증시간이 만료되었습니다."));
        }
    }
    public void checkCertificationFind(SmsRequest smsRequest) throws CertificationException {
        regexPhone(smsRequest);

        Optional<Sms> findSms = smsRepository.findSms(smsRequest);
        if (findSms.isEmpty()) {
            throw new NotFoundCertificationNumberException(HttpStatus.BAD_REQUEST, new JsonDefault("error", "인증정보가 존재하지 않습니다."));
        }
        Sms sms = findSms.get();

        boolean isExpireDateBefore = smsRepository.isBefore(sms);
        if (!isExpireDateBefore) {
            smsRepository.delete(sms);
            throw new ExpireCertificationException(HttpStatus.BAD_REQUEST, new JsonDefault("error", "인증시간이 만료되었습니다."));
        }
    }

    /**
     * 회원가입을 완료할때 실행되는 메서드
     * @param smsRequest
     * @throws CertificationException
     */
    public void isValid(SmsRequest smsRequest) throws CertificationException {
        regexPhone(smsRequest);
        distinctPhone(smsRequest);

        Optional<Sms> findSms = smsRepository.findSms(smsRequest);
        if (findSms.isEmpty()) {
            throw new NotFoundCertificationNumberException(HttpStatus.BAD_REQUEST, new JsonDefault("error", "인증정보가 존재하지 않습니다."));
        }

        Sms sms = findSms.get();
        smsRepository.delete(sms);

    }
    public void isValidFind(SmsRequest smsRequest) throws CertificationException {
        regexPhone(smsRequest);

        Optional<Sms> findSms = smsRepository.findSms(smsRequest);
        if (findSms.isEmpty()) {
            throw new NotFoundCertificationNumberException(HttpStatus.BAD_REQUEST, new JsonDefault("error", "인증정보가 존재하지 않습니다."));
        }

        Sms sms = findSms.get();
        smsRepository.delete(sms);

    }

    protected void regexPhone(SmsRequest data) throws IllegalPhoneException {

        String phone = data.getPhone();
        if (phone == null || phone.length() < 10) {
            throw new IllegalPhoneException(HttpStatus.BAD_REQUEST, new JsonDefault("error", "휴대폰 번호를 다시 확인해주세요."));
        }
        data.setPhone(phone.replaceAll("[^0-9]", ""));
    }

    protected void distinctPhone(SmsRequest data) throws DistinctPhoneException{
        String phone = data.getPhone();
        Optional<Long> findMember = smsRepository.findByPhone(phone);
        if (findMember.isPresent()) {
            throw new DistinctPhoneException(HttpStatus.BAD_REQUEST, new JsonDefault("error", "이미 가입한 이력이 있습니다."));
        }
    }
}
