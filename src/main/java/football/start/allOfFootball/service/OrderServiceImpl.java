package football.start.allOfFootball.service;

import football.common.domain.CouponList;
import football.common.domain.Member;
import football.common.domain.Orders;
import football.common.domain.Payment;
import football.payment.service.PaymentService;
import football.start.allOfFootball.controller.mypage.OrderDateForm;
import football.start.allOfFootball.controller.mypage.OrderListForm;
import football.common.enums.domainenum.TeamEnum;
import football.common.enums.paymentEnums.CashEnum;
import football.start.allOfFootball.repository.domainRepository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;


    @Override
    public void save(Orders orders, Optional<CouponList> couponList) {
        Payment payment = paymentService.buildPayment(orders.getMember(), -1 * orders.getAmountPayment(), CashEnum.사용, null);
        paymentService.save(payment);

        couponList.ifPresent(couponList1 -> {
            couponList1.useCoupon();
            orders.setCouponList(couponList1);
        });

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
        List<OrderListForm> list = new ArrayList<>(orderList.size());
        orderList.forEach(order -> list.add(new OrderListForm(order)));
        return list;
    }

    @Override
    public void setTeam(Map<TeamEnum, List<Orders>> result) {
        result.forEach((teamEnum, orders) -> orders.forEach(order -> order.setTeam(teamEnum)));
    }

}
