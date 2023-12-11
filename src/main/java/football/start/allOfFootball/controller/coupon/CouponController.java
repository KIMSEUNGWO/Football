package football.start.allOfFootball.controller.coupon;

import football.start.allOfFootball.domain.Coupon;
import football.start.allOfFootball.service.CouponService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CouponController {

    private final CouponService couponService;

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
