package football.start.allOfFootball.repository.domainRepository;

import football.common.domain.CouponList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class CashRepository {

    public int couponApply(Optional<CouponList> form) {
        return form
            .map(couponList -> couponList.getCoupon().getCouponDiscount())
            .orElse(0);
    }
}
