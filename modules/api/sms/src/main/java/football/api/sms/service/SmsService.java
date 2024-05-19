package football.api.sms.service;

import football.api.sms.domain.Sms;
import football.api.sms.dto.SmsRequest;
import football.api.sms.exception.*;
import football.api.sms.repository.SmsRepository;
import football.api.sms.repository.SmsRepositoryAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static football.api.sms.consts.SmsConst.CERTIFICATION_EXPIRE_MINUTES;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final SmsRepository smsRepositoryDB;
    private final SmsRepositoryAPI smsRepositoryAPI;

    public String sendSMS(SmsRequest smsRequest) throws MessageSendException {

        String certificationNumber = smsRepositoryAPI.createCertificationNumber();

//        Message message = smsRepositoryAPI.createMessage(smsRequest.getPhone(), certificationNumber);
//        smsRepositoryAPI.send(message);

        return certificationNumber;
    }

    public void saveSms(SmsRequest data, String certificationNumber) {

        Sms sms = Sms.builder()
            .phone(data.getPhone())
            .certificationNumber(certificationNumber)
            .expireDate(LocalDateTime.now().plusMinutes(CERTIFICATION_EXPIRE_MINUTES))
            .build();

        smsRepositoryDB.save(sms);
    }

    /**
     * 휴대폰 인증 버튼 클릭시 실행되는 메서드
     * @param smsRequest
     * @throws CertificationException
     */
    public void checkCertification(SmsRequest smsRequest) throws CertificationException {
        regexPhone(smsRequest.getPhone());
        distinctPhone(smsRequest.getPhone());

        Sms sms = smsRepositoryDB.findSms(smsRequest.getPhone(), smsRequest.getCertificationNumber());

        smsRepositoryDB.expireDateCheck(sms);
    }
    public void checkCertificationFind(SmsRequest smsRequest) throws CertificationException {
        regexPhone(smsRequest.getPhone());

        Sms sms = smsRepositoryDB.findSms(smsRequest.getPhone(), smsRequest.getCertificationNumber());

        smsRepositoryDB.expireDateCheck(sms);
    }

    /**
     * 회원가입을 완료할때 실행되는 메서드
     * @throws CertificationException
     */
    public void isValid(String phone, String certificationNumber) throws CertificationException {
        regexPhone(phone);
        distinctPhone(phone);

        Sms sms = smsRepositoryDB.findSms(phone, certificationNumber);

        smsRepositoryDB.delete(sms);

    }
    public void isValidFind(String phone, String certificationNumber) throws CertificationException {
        regexPhone(phone);

        Sms sms = smsRepositoryDB.findSms(phone, certificationNumber);

        smsRepositoryDB.delete(sms);

    }

    public void regexPhone(String phone) throws IllegalPhoneException {
        if (phone == null || isNaN(phone) || phone.length() < 10 || !phone.startsWith("01")) {
            throw new IllegalPhoneException();
        }
    }

    private boolean isNaN(String phone) {
        for (int i = 0; i < phone.length(); i++) {
             if (!Character.isDigit(phone.charAt(i))) return true;
        }
        return false;
    }

    public void distinctPhone(String phone) throws DistinctPhoneException{
        boolean existsByPhone = smsRepositoryDB.existsByMemberPhone(phone);
        if (existsByPhone) throw new DistinctPhoneException();
    }
}
