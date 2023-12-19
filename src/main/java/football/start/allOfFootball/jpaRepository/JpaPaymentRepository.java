package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByMemberOrderByPaymentIdDesc(Member member);
}
