package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Payment;
import football.start.allOfFootball.enums.paymentEnums.CashEnum;
import football.start.allOfFootball.enums.paymentEnums.PaymentType;
import football.start.allOfFootball.repository.domainRepository.MemberRepository;
import football.start.allOfFootball.repository.domainRepository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;

    public Payment buildPayment(Member member, int insertCash, CashEnum cashEnum, PaymentType paymentType) {
        int cash = member.getMemberCash() + insertCash;
        member.setMemberCash(cash);

        return Payment.builder()
            .member(member)
            .charge(insertCash)
            .resultCash(cash)
            .cashType(cashEnum)
            .paymentType(paymentType)
            .build();
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
        Member member = payment.getMember();
        memberRepository.refreshMemberCache(member, payment.getResultCash());
    }
}
