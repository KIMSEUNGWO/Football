package football.start.allOfFootball.service;

import football.common.domain.Coupon;
import football.common.domain.Member;

import java.util.Map;

public interface CouponService {
    void createCoupon(Coupon coupon, Map<String, String> result);

    void removeCoupon(Long couponId);

    Coupon getCoupon(Long couponId);

    void saveCouponListTest(Member member, Coupon coupon);
}
