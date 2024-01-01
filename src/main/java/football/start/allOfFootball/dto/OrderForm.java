package football.start.allOfFootball.dto;


import football.start.allOfFootball.domain.CouponList;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.formatter.DateFormatter;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderForm {

    private Long matchId;
    private String fieldTitle;
    private String fieldAddress;
    private String matchDate;

    private List<CouponListForm> couponList;
    private Integer cash;


    public OrderForm(Member member, Match match, List<CouponListForm> couponLists) {
        matchId = match.getMatchId();
        fieldTitle = match.getField().getFieldTitle();
        fieldAddress = match.getField().getFieldAddress();
        matchDate = DateFormatter.dateFormatAll(match.getMatchDate());
        couponList = couponLists;
        cash = member.getMemberCash();
    }
}
