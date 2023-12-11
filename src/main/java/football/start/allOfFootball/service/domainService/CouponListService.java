package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.domain.CouponList;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.dto.CouponListForm;
import football.start.allOfFootball.repository.domainRepository.CouponListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponListService {

    private final CouponListRepository couponListRepository;

    public List<CouponListForm> getCouponList(Member member) {
        List<CouponList> couponList = couponListRepository.getCouponList(member);
        return couponList.stream().map(x -> CouponListForm.build(x)).collect(Collectors.toList());
    }
}
