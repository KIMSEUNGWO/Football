package football.start.allOfFootball.service.domainService;

import football.start.allOfFootball.controller.mypage.CashListForm;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Payment;
import football.start.allOfFootball.enums.paymentEnums.CashEnum;
import football.start.allOfFootball.enums.paymentEnums.PaymentType;
import football.start.allOfFootball.formatter.DateFormatter;
import football.start.allOfFootball.formatter.NumberFormatter;
import football.start.allOfFootball.repository.domainRepository.MemberRepository;
import football.start.allOfFootball.repository.domainRepository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;

    public Payment buildPayment(Member member, int insertCash, CashEnum cashEnum, PaymentType paymentType) {
        int cash = member.getMemberCash() + insertCash;
        member.setMemberCash(cash);

        return Payment.builder()
            .member(member)
            .charge(insertCash)
            .resultCash(cash)
            .cashType(cashEnum)
            .paymentType(paymentType)
            .build();
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
        Member member = payment.getMember();
        memberRepository.refreshMemberCash(member, payment.getResultCash());
    }

    public List<CashListForm> findByAllMemberCacheList(Member findMember) {
        List<Payment> list = paymentRepository.findByAllMemberCashList(findMember);

        List<CashListForm> result = new ArrayList<>();
        for (Payment payment : list) {
            CashListForm build = CashListForm.builder()
                .cashDate(DateFormatter.format("yyyy년 M월 d일 (E)", payment.getCreateDate()))
                .cashTime(getTime(payment.getCreateDate()))
                .cashEnum(payment.getCashType())
                .cash(payment.getCharge())
                .cashStr(NumberFormatter.format(payment.getCharge()))
                .nowCash(NumberFormatter.format(payment.getResultCash()))
                .build();
            result.add(build);
        }
        return result;
    }

    private String getTime(LocalDateTime createDate) {
        return String.format("%02d:%02d", createDate.getHour(), createDate.getMinute());
    }
}
