package football.internal.database.jpaRepository;

import football.internal.database.domain.Match;
import football.internal.database.domain.Member;
import football.internal.database.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByMatch(Match match);
    List<Orders> findByMember(Member member);
}
