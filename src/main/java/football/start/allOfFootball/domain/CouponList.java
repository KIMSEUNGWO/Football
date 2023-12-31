package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "COUPON_LIST")
@SequenceGenerator(name = "SEQ_COUPON_LIST", sequenceName = "SEQ_COUPON_LIST_ID", allocationSize = 1)
public class CouponList extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUPON_LIST")
    private Long couponListId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;
    private LocalDateTime couponListExpireDate;

    private char couponListStatus;
}
