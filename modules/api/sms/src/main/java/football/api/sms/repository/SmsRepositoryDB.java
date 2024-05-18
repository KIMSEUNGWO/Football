package football.api.sms.repository;

import football.api.sms.domain.Sms;
import football.api.sms.exception.ExpireCertificationException;
import football.api.sms.exception.NotFoundCertificationNumberException;
import football.common.jpaRepository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SmsRepositoryDB implements SmsRepository {

    private final JpaSmsRepository jpaSmsRepository;
    private final JpaMemberRepository jpaMemberRepository;

    @Override
    @Transactional
    public void save(Sms sms) {
        jpaSmsRepository.save(sms);
    }

    @Override
    @Transactional
    public Sms findSms(String phone, String certificationNumber) throws NotFoundCertificationNumberException {
        Optional<Sms> findSms = jpaSmsRepository.findByPhoneAndCertificationNumber(phone, certificationNumber);
        return findSms.orElseThrow(NotFoundCertificationNumberException::new);
    }

    @Override
    @Transactional
    public void delete(Sms sms) {
        jpaSmsRepository.deleteAllByPhone(sms.getPhone());
    }

    @Override
    public boolean existsByMemberPhone(String phone) {
        return jpaMemberRepository.existsByMemberPhone(phone);
    }

    @Override
    @Transactional(noRollbackFor = ExpireCertificationException.class)
    public void expireDateCheck(Sms sms) throws ExpireCertificationException {
        LocalDateTime expireDate = sms.getExpireDate();

        boolean isExpire = LocalDateTime.now().isAfter(expireDate);
        if (isExpire) {
            jpaSmsRepository.deleteAllByPhone(sms.getPhone());
            throw new ExpireCertificationException();
        }
    }
}
