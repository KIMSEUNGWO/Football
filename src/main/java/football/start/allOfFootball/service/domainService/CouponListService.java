package football.start.allOfFootball.service.domainService;

import football.common.domain.CouponList;
import football.common.domain.Member;
import football.start.allOfFootball.dto.CouponListForm;
import football.start.allOfFootball.mapper.Mapper;
import football.start.allOfFootball.repository.domainRepository.CouponListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponListService {

    private final CouponListRepository couponListRepository;

    public List<CouponListForm> getCouponList(Member member) {
        List<CouponList> couponList = couponListRepository.getCouponList(member);

        // 만료일이 지난 쿠폰 삭제
        List<CouponList> renewCouponList = couponListRepository.deleteByExpireDate(couponList);

        return renewCouponList.stream().map(Mapper::getCouponListForm).toList();
    }

    public Optional<CouponList> findByCouponListId(Long couponNum) {
        if (couponNum == null) return Optional.empty();

        Optional<CouponList> byCouponListId = couponListRepository.findByCouponListId(couponNum);

        return byCouponListId.filter(couponList -> !couponList.isUsedCoupon());
    }
}
