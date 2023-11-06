package football.start.allOfFootball.repository;

import football.start.allOfFootball.domain.Coupon;

public interface CouponRepository {
    void createCoupon(Coupon coupon);

    void deleteCouponList(Long couponId);
}
