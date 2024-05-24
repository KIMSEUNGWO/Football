package football.payment.service;

import football.common.domain.*;
import football.common.enums.paymentEnums.CashEnum;
import football.common.enums.paymentEnums.RefundEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefundService {

    private final PaymentService paymentService;


    public void refund(Match match, RefundEnum refundEnum) {
        List<Orders> ordersList = match.getOrdersList();
        for (Orders orders : ordersList) {

            Integer expectedPrice = orders.getAmountPayment();
            CouponList couponList = orders.getCouponList();

            if (couponList != null) {
                couponList.refundCoupon();
                orders.setCouponList(null);
            }

            int refund = refundEnum.refund(expectedPrice);
            if (refund <= 0) continue;

            Member member = orders.getMember();
            Payment payment = paymentService.buildPayment(member, refund, CashEnum.환불, null);
            paymentService.save(payment);

        }

    }

}
