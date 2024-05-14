package football.common.jpaRepository;

import football.common.domain.Match;
import football.common.domain.Member;
import football.common.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByMatch(Match match);
    List<Orders> findByMember(Member member);
}
