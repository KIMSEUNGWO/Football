package football.start.allOfFootball.jpaRepository;

import football.start.allOfFootball.domain.CouponList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCouponListRepository extends JpaRepository<CouponList, Long> {

    void deleteByCouponId(Long couponId);
}
