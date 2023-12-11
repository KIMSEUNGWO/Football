package football.start.allOfFootball.repository.domainRepository;

import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.jpaRepository.JpaOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;


    public void save(Orders orders) {
        jpaOrderRepository.save(orders);
    }
}
