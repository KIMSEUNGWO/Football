package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponRepository extends JpaRepository<Coupon, Long> {
}
