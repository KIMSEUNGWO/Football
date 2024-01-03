package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.domain.CouponList;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.repository.domainRepository.CashRepository;
import football.start.exception.NotEnoughCashException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CashService {

    private final CashRepository cashRepository;
    public int calculate(Match match, Member member, Optional<CouponList> couponList) throws NotEnoughCashException {
        int price = match.getPrice();
        price -= cashRepository.couponApply(couponList, price);

        int cash = member.getMemberCash();
        if (cash < price) {
            throw new NotEnoughCashException();
        }
        return price;
    }
}
