package football.start.allOfFootball.controller.coupon;

import football.common.consts.SessionConst;
import football.common.domain.Coupon;
import football.common.domain.Member;
import football.start.allOfFootball.service.CouponService;
import football.common.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CouponController {

    private final CouponService couponService;
    private final MemberService memberService;

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
    public String listTest(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId) {
        Optional<Member> findMember = memberService.findByMemberId(memberId);
        Member member = findMember.get();
        Coupon coupon = couponService.getCoupon(1L);
        couponService.saveCouponListTest(member, coupon);
        Coupon coupon2 = couponService.getCoupon(2L);
        couponService.saveCouponListTest(member, coupon2);

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
