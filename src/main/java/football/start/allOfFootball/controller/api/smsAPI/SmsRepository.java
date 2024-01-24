package football.start.allOfFootball.controller.api.smsAPI;

import football.start.allOfFootball.domain.Sms;
import football.start.allOfFootball.jpaRepository.JpaSmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SmsRepository {

    private final JpaSmsRepository jpaSmsRepository;
    private final int maxLength = 5;

    protected String createCertificationNumber() {
        return new Random()
            .ints(0, 9)
            .limit(maxLength)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining());
    }

    public String createMessage(String certificationNumber) {
        return String.format("[모두의풋볼] 본인확인 \n 인증번호는 [%s] 입니다.", certificationNumber);
    }

    public void save(Sms sms) {
        jpaSmsRepository.save(sms);
    }

    public Optional<Sms> findSms(SmsRequest smsRequest) {
        return jpaSmsRepository.findByPhoneAndCertificationNumber(smsRequest.getPhone(), smsRequest.getCertificationNumber());
    }

    public boolean isBefore(Sms sms) {
        LocalDateTime expireDate = sms.getExpireDate();
        LocalDateTime now = LocalDateTime.now();

        return now.isBefore(expireDate);
    }

    public void delete(Sms sms) {
        jpaSmsRepository.deleteByPhone(sms.getPhone());
    }
}
