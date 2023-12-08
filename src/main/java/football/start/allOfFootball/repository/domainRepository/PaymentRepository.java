package football.start.allOfFootball.repository.domainRepository;

import football.start.allOfFootball.domain.Payment;
import football.start.allOfFootball.jpaRepository.JpaPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PaymentRepository {

    private final JpaPaymentRepository jpaPaymentRepository;

    public void save(Payment payment) {
        jpaPaymentRepository.save(payment);
    }
}
