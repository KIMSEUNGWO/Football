package football.start.allOfFootball.repository;

import football.internal.database.domain.Coupon;
import football.internal.database.domain.Member;

public interface CouponRepository {
    void createCoupon(Coupon coupon);

    void deleteCouponList(Long couponId);

    Coupon getCoupon(Long couponId);

    void saveCouponListTest(Member member, Coupon coupon);
}
