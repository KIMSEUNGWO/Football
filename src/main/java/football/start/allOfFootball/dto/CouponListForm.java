package football.start.allOfFootball.dto;

import football.common.domain.CouponList;
import football.common.formatter.DateFormatter;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponListForm {

    private Long couponListId;
    private String couponName;
    private Integer couponDiscount;
    private String remainingTime;
    private String couponEndDate;


    public CouponListForm(CouponList couponList) {
        couponListId = couponList.getCouponListId();
        couponName = couponList.getCoupon().getCouponName();
        couponDiscount = couponList.getCoupon().getCouponDiscount();
        remainingTime = couponList.howToRemaining();
        couponEndDate = DateFormatter.format("yyyy.MM.dd HH:mm 까지", couponList.getCouponListExpireDate());
    }


}
