package football.start.allOfFootball.service.domainService;

import football.common.domain.CouponList;
import football.common.domain.Match;
import football.common.domain.Member;
import football.start.allOfFootball.repository.domainRepository.CashRepository;
import football.start.allOfFootball.exception.NotEnoughCashException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CashService {

    private final CashRepository cashRepository;

    public int calculate(Long matchId, Match match, Member member, Optional<CouponList> couponList) throws NotEnoughCashException {
        // 매치경기금액 - 쿠폰 (없으면 0)
        int price = Math.max(match.getPrice() - cashRepository.couponApply(couponList), 0);

        // 내 캐시보다 금애깅 크면 예외발생
        int myCash = member.getMemberCash();
        if (myCash < price) throw new NotEnoughCashException("/order/" + matchId);

        return price;
    }
}
