package football.start.allOfFootball.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.domain.Coupon;
import football.start.allOfFootball.jpaRepository.JpaCouponListRepository;
import football.start.allOfFootball.jpaRepository.JpaCouponRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final JpaCouponRepository jpaCouponRepository;
    private final JpaCouponListRepository jpaCouponListRepository;
    private final JPAQueryFactory query;



    @Override
    public void createCoupon(Coupon coupon) {
        jpaCouponRepository.save(coupon);
    }

    @Override
    public void deleteCouponList(Long couponId) {
        // 쿠폰리스트에서 해당 쿠폰리스트를 모두 삭제 ( CASCADE 효과와 같음)
        jpaCouponListRepository.deleteByCouponId(couponId);

        // 이후에 쿠폰 삭제
        jpaCouponRepository.deleteById(couponId);
    }
}
