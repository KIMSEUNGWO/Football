package football.start.allOfFootball.service;

import football.start.allOfFootball.domain.Coupon;
import football.start.allOfFootball.domain.Member;

import java.util.Map;

public interface CouponService {
    void createCoupon(Coupon coupon, Map<String, String> result);

    void removeCoupon(Long couponId);

    Coupon getCoupon(Long couponId);

    void saveCouponListTest(Member member, Coupon coupon);
}
