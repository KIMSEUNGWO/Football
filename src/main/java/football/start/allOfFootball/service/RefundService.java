package football.start.allOfFootball.service;

import football.start.allOfFootball.domain.*;
import football.start.allOfFootball.enums.paymentEnums.CashEnum;
import football.start.allOfFootball.enums.paymentEnums.RefundEnum;
import football.start.allOfFootball.service.domainService.PaymentService;
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

            Integer expectedPrice = orders.getPayment();
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
