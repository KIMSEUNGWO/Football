package football.internal.database.jpaRepository;

import football.internal.database.domain.Member;
import football.internal.database.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByMemberOrderByPaymentIdDesc(Member member);
}
