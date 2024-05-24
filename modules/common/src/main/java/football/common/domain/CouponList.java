package football.common.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@Table(name = "COUPON_LIST")
@NoArgsConstructor
@AllArgsConstructor
public class CouponList extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponListId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "COUPON_ID")
    private Coupon coupon;
    private LocalDateTime couponListExpireDate;

    private char couponListStatus;

    public boolean isUsedCoupon() { return couponListStatus == 'Y'; }

    public void useCoupon() { couponListStatus = 'Y'; }

    public void refundCoupon() { couponListStatus = 'N'; }

    public boolean isExpire() {
        return LocalDateTime.now().isAfter(couponListExpireDate);
    }

    public CouponList(Member member, Coupon coupon) {
        this.member = member;
        this.coupon = coupon;
        this.couponListExpireDate = LocalDateTime.now().plusDays(coupon.getCouponLimitDay());
        this.couponListStatus = 'N';
    }

    public String howToRemaining() {
        Duration duration = Duration.between(LocalDateTime.now(), couponListExpireDate);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;

        if (days >= 1) return days + "일 남음";
        if (hours >= 1) return hours + "시간 남음";
        return "만료임박";
    }

}
