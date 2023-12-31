package football.start.allOfFootball.service;

import football.start.allOfFootball.controller.mypage.OrderDateForm;
import football.start.allOfFootball.controller.mypage.OrderListForm;
import football.start.allOfFootball.domain.CouponList;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Orders;
import football.start.allOfFootball.enums.TeamEnum;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {

    void save(Orders orders, Member member, Optional<CouponList> couponList, int price);

    List<Orders> findByMatchBefore(Member member);
    List<Orders> findByMatchAll(Member member, OrderDateForm form);

    List<OrderListForm> getMatchResultForm(List<Orders> orderList);

    void setTeam(Map<TeamEnum, List<Orders>> result);
}
