package football.start.allOfFootball.controller.coupon;

import football.common.domain.Coupon;
import football.common.domain.Member;
import football.login.config.auth.PrincipalDetails;
import football.login.config.auth.UserRefreshProvider;
import football.start.allOfFootball.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CouponController {

    private final CouponService couponService;
    private final UserRefreshProvider provider;

    @GetMapping("/coupon/test")
    public String test() {
        Coupon coupon = new Coupon();
        coupon.setCouponDiscount(5000);
        coupon.setCouponName("테스트용 쿠폰");
        coupon.setCouponLimitDay(7);
        couponService.createCoupon(coupon, new HashMap<>());
        Coupon coupon2 = new Coupon();
        coupon2.setCouponDiscount(10000);
        coupon2.setCouponName("무료 쿠폰");
        coupon2.setCouponLimitDay(31);
        couponService.createCoupon(coupon2, new HashMap<>());
        return "redirect:/couponList/test";
    }

    @GetMapping("/couponList/test")
    public String listTest(@AuthenticationPrincipal PrincipalDetails user) {
        Member member = user.getMember();
        Coupon coupon = couponService.getCoupon(1L);
        couponService.saveCouponListTest(member, coupon);
        Coupon coupon2 = couponService.getCoupon(2L);
        couponService.saveCouponListTest(member, coupon2);

        provider.refresh(user);
        return "redirect:/";
    }

    @PostMapping("/coupon/create")
    public ResponseEntity<Map<String, String>> createCoupon(Coupon coupon) {
        Map<String, String> result = new HashMap<>();
//        couponService.createCoupon(coupon, result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/coupon/remove")
    public ResponseEntity<Map<String, String>> removeCoupon(Long couponId) {
        Map<String, String> result = new HashMap<>();
//        couponService.removeCoupon(couponId);
        return ResponseEntity.ok(result);
    }
}
