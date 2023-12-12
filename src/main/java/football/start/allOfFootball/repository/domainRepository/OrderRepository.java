package football.start.allOfFootball.repository.domainRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.domain.QOrders;
import football.start.allOfFootball.jpaRepository.JpaOrderRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static football.start.allOfFootball.domain.QOrders.orders;

@Repository
@Slf4j
public class OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final JPAQueryFactory query;

    public OrderRepository(JpaOrderRepository jpaOrderRepository, EntityManager em) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.query = new JPAQueryFactory(em);
    }

    public void save(Orders orders) {
        jpaOrderRepository.save(orders);
    }

    public List<Orders> findByMember(Member member) {
        return query.selectFrom(orders)
            .where(orders.member.eq(member).and(orders.match.matchEndStatus.eq('N')))
            .fetch();
    }
}
