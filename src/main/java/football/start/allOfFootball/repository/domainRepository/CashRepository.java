package football.start.allOfFootball.repository.domainRepository;

import football.internal.database.domain.Coupon;
import football.internal.database.domain.CouponList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class CashRepository {

    public int couponApply(Optional<CouponList> form, Integer price) {
        if (form.isEmpty()) return 0;

        CouponList couponList = form.get();
        Coupon coupon = couponList.getCoupon();
        int discount = coupon.getCouponDiscount();

        return Math.max(0, discount);
    }
}
