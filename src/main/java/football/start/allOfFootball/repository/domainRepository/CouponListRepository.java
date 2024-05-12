package football.start.allOfFootball.repository.domainRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.internal.database.domain.CouponList;
import football.internal.database.domain.Member;
import football.internal.database.jpaRepository.JpaCouponListRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static football.start.allOfFootball.domain.QCouponList.couponList;

@Repository
@Slf4j
public class CouponListRepository {

    private final JpaCouponListRepository jpaCouponListRepository;
    private final JPAQueryFactory query;

    public CouponListRepository(JpaCouponListRepository jpaCouponListRepository, EntityManager em) {
        this.jpaCouponListRepository = jpaCouponListRepository;
        this.query = new JPAQueryFactory(em);
    }


    public List<CouponList> getCouponList(Member member) {
        return query.selectFrom(couponList)
//            .where(couponList.member.eq(member).and(couponList.couponListExpireDate.gt(LocalDateTime.now())).and(couponList.couponListStatus.eq('N')))
            .where(couponList.member.eq(member).and(couponList.couponListStatus.eq('N')))
            .orderBy(couponList.couponListExpireDate.asc())
            .fetch();
    }

    public Optional<CouponList> findByCouponListId(Long couponNum) {
        return jpaCouponListRepository.findById(couponNum);
    }

    @Transactional
    public List<CouponList> deleteByExpireDate(List<CouponList> couponList) {
        List<CouponList> list = new ArrayList<>();

        for (CouponList coupon : couponList) {
            if (coupon.isExpire()) {
                jpaCouponListRepository.delete(coupon);
            } else {
                list.add(coupon);
            }
        }
        return list;
    }
}
