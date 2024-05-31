package football.start.allOfFootball.dto;

import java.util.List;

public record OrderForm(Long matchId,
                        String fieldTitle,
                        String fieldAddress,
                        String matchDate,
                        String price,
                        List<CouponListForm> couponList,
                        int cash) {
}
