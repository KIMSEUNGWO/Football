package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Payment;
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


    public void save(Payment payment) {
        paymentRepository.save(payment);
        Member member = payment.getMember();
        memberRepository.refreshMemberCache(member, payment.getResultCash());
    }
}
