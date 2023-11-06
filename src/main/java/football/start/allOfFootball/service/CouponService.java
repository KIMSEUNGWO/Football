package football.start.allOfFootball.service;

import football.start.allOfFootball.domain.Coupon;

import java.util.Map;

public interface CouponService {
    void createCoupon(Coupon coupon, Map<String, String> result);

    void removeCoupon(Long couponId);
}
