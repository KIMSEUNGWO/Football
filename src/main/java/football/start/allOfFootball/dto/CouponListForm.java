package football.start.allOfFootball.dto;

import football.start.allOfFootball.domain.CouponList;
import football.start.allOfFootball.formatter.NumberFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class CouponListForm {

    private Long couponListId;
    private String couponName;
    private Integer couponDiscount;
    private String remainingTime;
    private String couponEndDate;


    public static CouponListForm build(CouponList couponList) {
        return CouponListForm.builder()
            .couponListId(couponList.getCouponListId())
            .couponName(couponList.getCoupon().getCouponName())
            .couponDiscount(couponList.getCoupon().getCouponDiscount())
            .remainingTime(getRemain(couponList.getCouponListExpireDate()))
            .couponEndDate(getEndDate(couponList.getCouponListExpireDate()))
            .build();
    }

    private static String getRemain(LocalDateTime couponListExpireDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, couponListExpireDate);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;

        if (days >= 1) {
            return days + "일 남음";
        }
        if (hours >= 1) {
            return hours + "시간 남음";
        }

        return "만료임박";
    }

    private static String getEndDate(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int hour = date.getHour();
        int minute = date.getMinute();

        return String.format("%d.%02d.%02d %02d:%02d까지", year, month, day, hour, minute);
    }

}
