package football.start.allOfFootball.service;

import football.common.domain.Coupon;
import football.common.domain.Member;
import football.start.allOfFootball.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;


    @Override
    public void createCoupon(Coupon coupon, Map<String, String> result) {
        couponRepository.createCoupon(coupon);
        result.put("result", "성공");
        result.put("message", "쿠폰을 생성했습니다");
    }

    @Override
    public void removeCoupon(Long couponId) {
        couponRepository.deleteCouponList(couponId);
    }

    @Override
    public Coupon getCoupon(Long couponId) {
        return couponRepository.getCoupon(couponId);
    }

    @Override
    public void saveCouponListTest(Member member, Coupon coupon) {
        couponRepository.saveCouponListTest(member, coupon);
    }
}
