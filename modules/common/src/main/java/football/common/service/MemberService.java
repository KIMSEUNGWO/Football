package football.common.service;

import football.common.domain.Manager;
import football.common.domain.Match;
import football.common.domain.Member;
import football.common.domain.Orders;
import football.common.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findByMemberId(Long memberId) {
        if (memberId == null) return Optional.empty();
        return memberRepository.findByMemberId(memberId);
    }

    public boolean isAlreadyApply(List<Orders> ordersList, Match match) {
        LocalDateTime minDate = match.getMatchDate().minusHours(1);
        LocalDateTime maxDate = minDate.plusHours(3);
        for (Orders orders : ordersList) {
            Match m = orders.getMatch();
            LocalDateTime myMatchDate = m.getMatchDate();

            if (myMatchDate.isAfter(minDate) && myMatchDate.isBefore(maxDate)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAlreadyApply(List<Orders> memberOrdersList, Match match, Manager manager) {
        LocalDateTime minDate = match.getMatchDate().minusHours(1);
        LocalDateTime maxDate = minDate.plusHours(3);

        if (manager != null) {
            List<Orders> ordersList = memberRepository.findAllOrders(manager.getMember());
            for (Orders orders : ordersList) {
                Match m = orders.getMatch();
                LocalDateTime myMatchDate = m.getMatchDate();

                if (myMatchDate.isAfter(minDate) && myMatchDate.isBefore(maxDate)) {
                    return true;
                }
            }
        }
        for (Orders orders : memberOrdersList) {
            Match m = orders.getMatch();
            LocalDateTime myMatchDate = m.getMatchDate();

            if (myMatchDate.isAfter(minDate) && myMatchDate.isBefore(maxDate)) {
                return true;
            }
        }
        return false;
    }

    public Optional<Member> findByMemberPhone(String phone) {
        return memberRepository.findByMemberPhone(phone);
    }

    public Optional<Member> findByMemberEmailAndMemberPhone(String email, String phone) {
        return memberRepository.findByMemberEmailAndMemberPhone(email, phone);
    }

    public void changePassword(Member member, String password) {
        memberRepository.changePassword(member, password);
    }

    public boolean existsByEmailAndPhone(String email, String phone) {
        return memberRepository.existsByEmailAndPhone(email, phone);
    }
}
