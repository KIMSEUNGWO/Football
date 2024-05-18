package football.payment.service;

import football.payment.dto.CashListForm;
import football.payment.repository.PaymentRepository;
import football.common.domain.Member;
import football.common.domain.Payment;
import football.common.enums.paymentEnums.CashEnum;
import football.common.enums.paymentEnums.PaymentType;
import football.common.formatter.DateFormatter;
import football.common.formatter.NumberFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

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
        member.setMemberCash(payment.getResultCash());
    }

    public List<CashListForm> findByAllMemberCacheList(Member findMember) {
        List<Payment> list = paymentRepository.findByAllMemberCashList(findMember);

        return list.stream().map(payment -> CashListForm.builder()
                .cashDate(DateFormatter.format("yyyy년 M월 d일 (E)", payment.getCreateDate()))
                .cashTime(DateFormatter.format("HH:mm", payment.getCreateDate()))
                .cashEnum(payment.getCashType())
                .cash(payment.getCharge())
                .cashStr(NumberFormatter.format(payment.getCharge()))
                .nowCash(NumberFormatter.format(payment.getResultCash()))
                .build())
            .toList();
    }

}
