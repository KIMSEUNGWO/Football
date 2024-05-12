package football.start.allOfFootball.repository.domainRepository;

import football.internal.database.domain.Member;
import football.internal.database.domain.Payment;
import football.internal.database.jpaRepository.JpaPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PaymentRepository {

    private final JpaPaymentRepository jpaPaymentRepository;

    public void save(Payment payment) {
        jpaPaymentRepository.save(payment);
    }

    public List<Payment> findByAllMemberCashList(Member findMember) {
        return jpaPaymentRepository.findAllByMemberOrderByPaymentIdDesc(findMember);
    }
}
