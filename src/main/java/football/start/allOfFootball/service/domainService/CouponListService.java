package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.domain.CouponList;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.dto.CouponListForm;
import football.start.allOfFootball.repository.domainRepository.CouponListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponListService {

    private final CouponListRepository couponListRepository;

    public List<CouponListForm> getCouponList(Member member) {
        List<CouponList> couponList = couponListRepository.getCouponList(member);
        if (couponList.isEmpty()) return Collections.emptyList();

        // 만료일이 지난 쿠폰 삭제
        List<CouponList> renewCouponList = couponListRepository.deleteByExpireDate(couponList);
        return renewCouponList.stream().map(CouponListForm::build).collect(Collectors.toList());
    }

    public Optional<CouponList> findByCouponListId(Long couponNum) {
        if (couponNum == null) {
            return Optional.empty();
        }
        Optional<CouponList> byCouponListId = couponListRepository.findByCouponListId(couponNum);
        if (byCouponListId.isEmpty()) {
            return byCouponListId;
        }
        CouponList couponList = byCouponListId.get();
        if (couponList.getCouponListStatus() != 'N') {
            return Optional.empty();
        }
        return byCouponListId;
    }
}
