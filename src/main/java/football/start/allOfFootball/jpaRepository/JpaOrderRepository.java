package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByMatch(Match match);
    List<Orders> findByMember(Member member);
}
