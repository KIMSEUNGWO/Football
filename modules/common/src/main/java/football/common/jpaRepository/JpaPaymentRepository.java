package football.common.jpaRepository;

import football.common.domain.Member;
import football.common.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByMemberOrderByPaymentIdDesc(Member member);
}
