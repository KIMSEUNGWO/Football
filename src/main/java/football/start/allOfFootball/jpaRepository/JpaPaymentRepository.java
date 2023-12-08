package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {
}
