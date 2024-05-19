package football.api.sms.repository;

import football.api.sms.domain.Sms;
import football.api.sms.exception.ExpireCertificationException;
import football.api.sms.exception.NotFoundCertificationNumberException;

public interface SmsRepository {

    void save(Sms sms);
    Sms findSms(String phone, String certificationNumber) throws NotFoundCertificationNumberException;
    void delete(Sms sms);
    boolean existsByMemberPhone(String phone);
    void expireDateCheck(Sms sms) throws ExpireCertificationException;
}
