package football.start.allOfFootball.repository;

import football.common.domain.Coupon;
import football.common.domain.CouponList;
import football.common.domain.Member;
import football.common.jpaRepository.JpaCouponListRepository;
import football.common.jpaRepository.JpaCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CouponRepositoryImpl implements CouponRepository {

    private final JpaCouponRepository jpaCouponRepository;
    private final JpaCouponListRepository jpaCouponListRepository;

    @Override
    public void createCoupon(Coupon coupon) {
        jpaCouponRepository.save(coupon);
    }

    @Override
    public void deleteCouponList(Long couponId) {
        // 쿠폰리스트에서 해당 쿠폰리스트를 모두 삭제 ( CASCADE 효과와 같음)
//        jpaCouponListRepository.deleteByCouponId(couponId);

        // 이후에 쿠폰 삭제
//        jpaCouponRepository.deleteById(couponId);
    }

    @Override
    public Coupon getCoupon(Long couponId) {
        return jpaCouponRepository.findById(couponId).get();
    }

    @Override
    public void saveCouponListTest(Member member, Coupon coupon) {
        jpaCouponListRepository.save(new CouponList(member, coupon));
    }
}
