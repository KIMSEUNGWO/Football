package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.mypage.OrderDateForm;
import football.start.allOfFootball.controller.mypage.OrderListForm;
import football.common.domain.CouponList;
import football.common.domain.Member;
import football.common.domain.Orders;
import football.common.enums.domainenum.TeamEnum;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {

    void save(Orders orders, Optional<CouponList> couponList);
    List<Orders> findByMatchBefore(Member member);
    List<Orders> findByMatchAll(Member member, OrderDateForm form);
    List<OrderListForm> getMatchResultForm(List<Orders> orderList);
    void setTeam(Map<TeamEnum, List<Orders>> result);
}
