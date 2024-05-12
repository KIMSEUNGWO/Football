package football.internal.database.repository;

import football.internal.database.domain.Sms;
import football.internal.database.jpaRepository.JpaMemberRepository;
import football.internal.database.jpaRepository.JpaSmsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SmsInternalRepository {

    private final JpaSmsRepository jpaSmsRepository;
    private final JpaMemberRepository jpaMemberRepository;


    public void save(Sms sms) {
        jpaSmsRepository.save(sms);
    }

    public Optional<Sms> findSms(String phone, String certificationNumber) {
        return jpaSmsRepository.findByPhoneAndCertificationNumber(phone, certificationNumber);
    }

    @Transactional
    public void delete(Sms sms) {
        jpaSmsRepository.deleteAllByPhone(sms.getPhone());
    }

    public boolean existsByMemberPhone(String phone) {
        return jpaMemberRepository.existsByMemberPhone(phone);
    }
}
