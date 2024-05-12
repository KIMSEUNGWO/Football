package football.internal.database.jpaRepository;

import football.internal.database.domain.CouponList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponListRepository extends JpaRepository<CouponList, Long> {


}
