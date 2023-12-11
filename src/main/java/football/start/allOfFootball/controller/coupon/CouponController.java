package football.start.allOfFootball.controller.coupon;

import football.start.allOfFootball.SessionConst;
import football.start.allOfFootball.domain.Coupon;
import football.start.allOfFootball.domain.CouponList;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.service.CouponService;
import football.start.allOfFootball.service.domainService.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/coupon/remove")
    public ResponseEntity<Map<String, String>> removeCoupon(Long couponId) {
        Map<String, String> result = new HashMap<>();
//        couponService.removeCoupon(couponId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
