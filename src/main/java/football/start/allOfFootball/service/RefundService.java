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

            CouponList couponList = orders.getCouponList();
            int expectedPrice = 10000;

            if (couponList != null) {
                couponList.setCouponListStatus('N');
                orders.setCouponList(null);
                expectedPrice -= couponList.getCoupon().getCouponDiscount();
            }

            int refund = refundEnum.refund(expectedPrice);
            if (refund <= 0) continue;

            Member member = orders.getMember();
            Payment payment = paymentService.buildPayment(member, refund, CashEnum.환불, null);
            paymentService.save(payment);

        }

    }

}
