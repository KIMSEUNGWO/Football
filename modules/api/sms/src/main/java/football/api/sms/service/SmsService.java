package football.api.sms.service;

import football.api.sms.SmsConst;
import football.api.sms.repository.SmsRepository;
import football.internal.database.domain.Sms;
import football.api.sms.dto.SmsRequest;
import football.api.sms.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {


    private final SmsRepository smsRepository;

    public String sendSMS(SmsRequest smsRequest) throws MessageSendException {
        String certificationNumber = smsRepository.createCertificationNumber();

        Message message = smsRepository.createMessage(smsRequest.getPhone(), certificationNumber);

        smsRepository.send(message);

        return certificationNumber;
    }

    public void saveSms(SmsRequest data, String certificationNumber) {

        LocalDateTime expireDate = LocalDateTime.now().plusMinutes(SmsConst.CERTIFICATION_EXPIRE_TIME);

        Sms sms = Sms.builder()
            .phone(data.getPhone())
            .certificationNumber(certificationNumber)
            .expireDate(expireDate)
            .build();

        smsRepository.save(sms);
    }

    /**
     * 휴대폰 인증 버튼 클릭시 실행되는 메서드
     * @param smsRequest
     * @throws CertificationException
     */
    public void checkCertification(SmsRequest smsRequest) throws CertificationException {
        // 내 기억으론 여기로 바로 들어오는게 있어서 검증코드가 여기있는거였음 나중에 꼭 확인해야됨
        // 바로 service로 들어오는 코드가 존재하면 controller로 옮겨야한다.
        // TODO

        regexPhone(smsRequest);
        distinctPhone(smsRequest);

        Sms sms = smsRepository.findSms(smsRequest);

        boolean isExpireDateBefore = smsRepository.isBefore(sms);
        if (!isExpireDateBefore) {
            smsRepository.delete(sms);
            throw new ExpireCertificationException();
        }
    }
    public void checkCertificationFind(SmsRequest smsRequest) throws CertificationException {
        regexPhone(smsRequest);

        Sms sms = smsRepository.findSms(smsRequest);

        boolean isExpireDateBefore = smsRepository.isBefore(sms);
        if (!isExpireDateBefore) {
            smsRepository.delete(sms);
            throw new ExpireCertificationException();
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

        Sms sms = smsRepository.findSms(smsRequest);
        smsRepository.delete(sms);

    }
    public void isValidFind(SmsRequest smsRequest) throws CertificationException {
        regexPhone(smsRequest);

        Sms sms = smsRepository.findSms(smsRequest);

        smsRepository.delete(sms);

    }

    public void regexPhone(SmsRequest data) throws IllegalPhoneException {
        String phone = data.getPhone();
        if (phone == null || phone.length() < 10 || isNaN(phone)) throw new IllegalPhoneException();
        data.setPhone(phone.replaceAll("[^0-9]", ""));
    }

    public void distinctPhone(SmsRequest data) throws DistinctPhoneException{
        boolean byPhoneExists = smsRepository.existsByMemberPhone(data.getPhone());
        if (byPhoneExists) throw new DistinctPhoneException();
    }

    private boolean isNaN(String phone) {
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) return true;
        }
        return false;
    }


}
