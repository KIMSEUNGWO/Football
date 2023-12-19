package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.mypage.OrderDateForm;
import football.start.allOfFootball.controller.mypage.OrderListForm;
import football.start.allOfFootball.domain.*;
import football.start.allOfFootball.enums.matchEnums.MatchStatus;
import football.start.allOfFootball.enums.paymentEnums.CashEnum;
import football.start.allOfFootball.repository.domainRepository.OrderRepository;
import football.start.allOfFootball.service.domainService.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    @Override
    public int calculate(Optional<CouponList> form) {
        if (form.isEmpty()) {
            return 10000;
        }
        CouponList couponList = form.get();
        Coupon coupon = couponList.getCoupon();
        int price = coupon.getCouponDiscount();

        return Math.max(0, 10000 - price);
    }

    @Override
    public void save(Orders orders, Member member, Optional<CouponList> couponList, int price) {
        Payment payment = paymentService.buildPayment(member, -1 * price, CashEnum.사용, null);
        paymentService.save(payment);

        if (couponList.isPresent()) {
            CouponList couponList1 = couponList.get();
            couponList1.setCouponListStatus('Y');
            orders.setCouponList(couponList1);
        }

        orderRepository.save(orders);
    }

    @Override
    public List<Orders> findByMatchBefore(Member member) {
        return orderRepository.findByBefore(member);
    }

    @Override
    public List<Orders> findByMatchAll(Member member, OrderDateForm form) {
        return orderRepository.findByMatchAll(member, form);
    }

    @Override
    public List<OrderListForm> getMatchResultForm(List<Orders> orderList) {
        if (orderList.isEmpty()) return Collections.emptyList();
        return orderList.stream().map(x -> OrderListForm.build(x.getMatch())).collect(Collectors.toList());
    }
}
