package football.start.allOfFootball.repository;

import football.common.domain.Coupon;
import football.common.domain.Member;

public interface CouponRepository {
    void createCoupon(Coupon coupon);

    void deleteCouponList(Long couponId);

    Coupon getCoupon(Long couponId);

    void saveCouponListTest(Member member, Coupon coupon);
}
