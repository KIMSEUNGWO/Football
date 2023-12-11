package football.start.allOfFootball.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.domain.Coupon;
import football.start.allOfFootball.domain.CouponList;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.jpaRepository.JpaCouponListRepository;
import football.start.allOfFootball.jpaRepository.JpaCouponRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Slf4j
@Transactional
public class CouponRepositoryImpl implements CouponRepository {

    private final JpaCouponRepository jpaCouponRepository;
    private final JpaCouponListRepository jpaCouponListRepository;
    private final JPAQueryFactory query;

    public CouponRepositoryImpl(JpaCouponRepository jpaCouponRepository, JpaCouponListRepository jpaCouponListRepository, EntityManager em) {
        this.jpaCouponRepository = jpaCouponRepository;
        this.jpaCouponListRepository = jpaCouponListRepository;
        this.query = new JPAQueryFactory(em);
    }

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
        CouponList couponList = new CouponList();
        couponList.setMember(member);
        couponList.setCoupon(coupon);
        couponList.setCouponListExpireDate(LocalDateTime.now().plusDays(coupon.getCouponLimitDay()));
        couponList.setCouponListStatus('N');
        jpaCouponListRepository.save(couponList);
    }
}
