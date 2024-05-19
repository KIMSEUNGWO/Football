package football.common.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@Table(name = "COUPON_LIST")
@SequenceGenerator(name = "SEQ_COUPON_LIST", sequenceName = "SEQ_COUPON_LIST_ID", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class CouponList extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUPON_LIST")
    private Long couponListId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couponId")
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
