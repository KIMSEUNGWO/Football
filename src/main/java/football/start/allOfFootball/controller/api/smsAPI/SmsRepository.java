package football.start.allOfFootball.controller.api.smsAPI;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.QMember;
import football.start.allOfFootball.domain.Sms;
import football.start.allOfFootball.jpaRepository.JpaMemberRepository;
import football.start.allOfFootball.jpaRepository.JpaSmsRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class SmsRepository {

    private final JpaSmsRepository jpaSmsRepository;
    private final JpaMemberRepository jpaMemberRepository;
    private final JPAQueryFactory query;
    private final int maxLength = 5;

    public SmsRepository(JpaSmsRepository jpaSmsRepository, JpaMemberRepository jpaMemberRepository, EntityManager em) {
        this.jpaSmsRepository = jpaSmsRepository;
        this.jpaMemberRepository = jpaMemberRepository;
        this.query = new JPAQueryFactory(em);
    }

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

    @Transactional
    public void delete(Sms sms) {
        jpaSmsRepository.deleteAllByPhone(sms.getPhone());
    }

    public Optional<Long> findByPhone(String phone) {
        Long memberId = query.select(QMember.member.memberId)
            .from(QMember.member)
            .where(QMember.member.memberPhone.eq(phone))
            .fetchFirst();
        if (memberId == null) return Optional.empty();
        return Optional.of(memberId);
//        return jpaMemberRepository.findByMemberPhone(phone);
    }
}
