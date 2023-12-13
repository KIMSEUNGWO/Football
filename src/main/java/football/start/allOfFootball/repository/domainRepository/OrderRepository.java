package football.start.allOfFootball.repository.domainRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.controller.mypage.OrderDateForm;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.jpaRepository.JpaOrderRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static football.start.allOfFootball.domain.QOrders.orders;
import static football.start.allOfFootball.enums.matchEnums.MatchStatus.*;

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

    public List<Orders> findByBefore(Member member) {
        return query.selectFrom(orders)
            .where(orders.member.eq(member).and(orders.match.matchStatus.in(모집중, 마감임박, 마감) ))
            .fetch();
    }

    public List<Orders> findByMatchAll(Member member, OrderDateForm form) {
        if (form == null) return Collections.emptyList();

        return query.selectFrom(orders)
            .where(orders.member.eq(member).and(orders.match.matchDate.between(form.getStartDate(), form.getEndDate())))
            .orderBy(orders.match.matchDate.asc())
            .fetch();
    }
}
