package football.start.allOfFootball.repository.domainRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.domain.CouponList;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.QCouponList;
import football.start.allOfFootball.jpaRepository.JpaCouponListRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static football.start.allOfFootball.domain.QCouponList.couponList;

@Repository
@Slf4j
public class CouponListRepository {

    private final JpaCouponListRepository couponListRepository;
    private final JPAQueryFactory query;

    public CouponListRepository(JpaCouponListRepository couponListRepository, EntityManager em) {
        this.couponListRepository = couponListRepository;
        this.query = new JPAQueryFactory(em);
    }


    public List<CouponList> getCouponList(Member member) {
        return query.selectFrom(couponList)
            .where(couponList.member.eq(member).and(couponList.couponListExpireDate.gt(LocalDateTime.now())).and(couponList.couponListStatus.eq('N')))
            .orderBy(couponList.couponListId.desc())
            .fetch();
    }

    public Optional<CouponList> findByCouponListId(Long couponNum) {
        return couponListRepository.findById(couponNum);
    }
}
